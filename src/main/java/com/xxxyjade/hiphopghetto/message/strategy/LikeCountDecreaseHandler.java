package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.entity.Like;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentService;
import com.xxxyjade.hiphopghetto.server.topic.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LikeCountDecreaseHandler implements MessageStrategy<Like> {

    @Autowired
    private CommentService commentService;
    @Autowired
    private TopicService topicService;

    /**
     * 处理消息
     * 收藏数+1
     */
    public void handle(Message<Like> message) {
        try {
            Like like = message.getMessageBody();
            Long targetId = like.getTargetId();
            switch (like.getTargetType()) {
                case ResourceType.COMMENT -> commentService.decreaseLikeCount(targetId);
                case ResourceType.TOPIC -> topicService.decreaseLikeCount(targetId);
                default -> log.warn("未知对象类型: {}", like.getTargetType());
            }
        } catch (Exception e) {
            throw new HipHopGhettoFrameException("消息处理失败");
        }
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageType.LIKE_COUNT_DECREASE;
    }
}