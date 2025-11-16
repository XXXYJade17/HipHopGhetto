package com.xxxyjade.hiphopghetto.message.consumer;

import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CollectCountConsumer {



    // 监听收藏计数队列
//    @RabbitListener(queues = RabbitConfig.COLLECT_COUNT_QUEUE)
    public void handleCollectCount(Long resourceId) {
//        // 尝试更新歌曲收藏数
//        Song song = songMapper.selectById(resourceId);
//        if (song != null) {
//            song.addCollectCount(1);
//            songMapper.updateById(song);
//            log.info("更新歌曲收藏数: resourceId={}, 新收藏数={}", resourceId, song.getCollectCount());
//            return;
//        }
//
//        // 尝试更新专辑收藏数
//        Album album = albumMapper.selectById(resourceId);
//        if (album != null) {
//            album.addCollectCount(1);
//            albumMapper.updateById(album);
//            log.info("更新专辑收藏数: resourceId={}, 新收藏数={}", resourceId, album.getCollectCount());
//            return;
//        }
//
//        log.warn("未找到资源: resourceId={}", resourceId);
    }

}
