package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.entity.Comment;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommentCountDecreaseHandler implements MessageStrategy<Comment> {

    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理消息
     * 收藏数-1
     */
    public void handle(Message<Comment> message) {
        try {
            Comment comment = message.getMessageBody();
            switch (comment.getParentType()) {
                case ResourceType.COMMENT -> {
                    commentService.decreaseCommentCount(comment.getParentId());
                }
                default -> log.warn("未知对象类型: {}", comment.getParentType());
            }
        } catch (Exception e) {
            throw new HipHopGhettoFrameException("消息处理失败");
        }
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageType.COMMENT_COUNT_DECREASE;
    }
}