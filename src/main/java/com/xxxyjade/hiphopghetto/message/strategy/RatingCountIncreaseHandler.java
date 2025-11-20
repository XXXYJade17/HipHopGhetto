package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.entity.Rating;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.server.album.service.AlbumService;
import com.xxxyjade.hiphopghetto.server.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingCountIncreaseHandler implements MessageStrategy<Rating> {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;

    /**
     * 处理消息
     * 评分数加一
     */
    public void handle(Message<Rating> message) {
        Rating rating = message.getMessageBody();
        switch (rating.getTargetType()) {
            case ResourceType.ALBUM -> albumService.increaseRatingCount(rating.getId());
            case ResourceType.SONG -> songService.increaseRatingCount(rating.getId());
        }
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageType.RATING_COUNT_INCREASE;
    }
}
