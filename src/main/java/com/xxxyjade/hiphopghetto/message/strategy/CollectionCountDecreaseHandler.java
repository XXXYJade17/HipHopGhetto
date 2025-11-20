package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.entity.Collection;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.server.album.service.AlbumService;
import com.xxxyjade.hiphopghetto.server.song.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CollectionCountDecreaseHandler implements MessageStrategy<Collection> {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;

    /**
     * 处理消息
     * 收藏数-1
     */
    public void handle(Message<Collection> message) {
        try {
            Collection collection = message.getMessageBody();
            Long targetId = collection.getTargetId();
            switch (collection.getTargetType()) {
                case ResourceType.ALBUM -> albumService.decreaseCollectionCount(targetId);

                case ResourceType.SONG -> songService.decreaseCollectionCount(targetId);
                default -> log.warn("未知对象类型: {}", collection.getTargetType());
            }
        } catch (Exception e) {
            throw new HipHopGhettoFrameException("消息处理失败");
        }
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageType.COLLECTION_COUNT_DECREASE;
    }
}