package com.xxxyjade.hiphopghetto.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.property.AliyunOssProperties;
import com.xxxyjade.hiphopghetto.util.NetEaseAlbumCrawlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Tag(name = "上传服务")
@Slf4j
public class UploadController {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;
    @Autowired
    private NetEaseAlbumCrawlUtil netEaseAlbumCrawlUtil;

    @Operation(summary = "文件上传")
    @PostMapping(value = "/file")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传: {}",file);
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID() + extension;
            String filePath = upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败",e);
        }

        // TODO 改自定义状态
        return Result.error();
    }

    @Operation(summary = "专辑上传")
    @GetMapping("/album/{id}")
    public Result<Void> album(@PathVariable("id") Long id) {
        log.info("albumId:{}", id);
        netEaseAlbumCrawlUtil.startCrawl(id);
        return Result.success();
    }

    private String upload(byte[] bytes, String objectName) {

        OSS ossClient = new OSSClientBuilder().build(
                aliyunOssProperties.getEndpoint(),
                aliyunOssProperties.getAccessKeyId(),
                aliyunOssProperties.getAccessKeySecret());

        try {
            // 创建PutObject请求。
            ossClient.putObject(aliyunOssProperties.getBucketName(), objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(aliyunOssProperties.getBucketName())
                .append(".")
                .append(aliyunOssProperties.getEndpoint())
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder);

        return stringBuilder.toString();
    }

}
