package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageTypeConstant;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.SongService;
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
     * 对象累计评分数加一
     */
    public void handle(Message<Rating> message) {
        Rating rating = message.getMessageBody();
        switch (rating.getTargetType()) {
            case 1 -> albumService.increaseRatingCount(rating.getId());
            case 2 -> songService.increaseRatingCount(rating.getId());
        }
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageTypeConstant.RATING_COUNT_INCREASE;
    }
}
