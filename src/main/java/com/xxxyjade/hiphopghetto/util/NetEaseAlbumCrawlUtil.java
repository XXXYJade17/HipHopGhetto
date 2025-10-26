package com.xxxyjade.hiphopghetto.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class NetEaseAlbumCrawlUtil implements PageProcessor {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private NetEaseSongCrawlUtil netEaseSongCrawlUtil;

    private final Site site = Site.me()
            .setDomain("music.163.com")        // 目标域名
            .setSleepTime(100)               // 爬取间隔（避免反爬）
            .setRetryTimes(3)                 // 重试次数
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"); // 模拟浏览器UA

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(Page page) {
        log.info("开始爬取专辑数据");
        // 判断 URL
        if (page.getUrl().regex("https?://music\\.163\\.com/album\\?id=\\d+").match()) {
            // 专辑 Id
            Long albumId = Long.parseLong(page.getUrl().regex("id=(\\d+)").get());
            // 专辑名
            String albumName = page.getHtml().xpath("//h2[@class='f-ff2']/text()").get();
            // 歌手名
            String singer = String.join(" / ", page.getHtml().xpath("//p[@class='intr'][1]/span/a/text()").all());
            // 发行时间
            LocalDate releaseTime = LocalDate.parse(page.getHtml().xpath("//p[@class='intr'][2]/text()").get(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 专辑封面
            String url = page.getHtml().xpath("//div[@class=\"cover u-cover u-cover-alb\"]/img/@data-src").get();
            // 专辑介绍
            String introduction = page.getHtml().xpath("//div[@id='album-desc-dot']/p/text()").get();
            // 歌曲id列表
            List<Long> songs = page.getHtml().xpath("//ul[@class='f-hide']/li").all().stream().map(s -> {
                Html html = Html.create(s);
                return Long.parseLong(html.xpath("//a/@href").get().replace("/song?id=", ""));
            }).toList();
            // 插入歌曲数据
            songs.forEach(songId -> {
                netEaseSongCrawlUtil.startCrawl(songId);
            });
            // 插入专辑数据
            Album album = new Album(albumId, albumName, singer, releaseTime, url, introduction);
            albumService.insert(album);
        }
    }

    public void startCrawl(String albumUrl) {
        Spider.create(this)
                .addUrl(albumUrl)
                .thread(1) // 单线程避免请求过于频繁
                .run();
    }

    public void startCrawl(Long id) {
        startCrawl("https://music.163.com/album?id=" + id);
    }



}
