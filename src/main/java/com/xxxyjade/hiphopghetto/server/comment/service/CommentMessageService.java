package com.xxxyjade.hiphopghetto.server.comment.service;

import com.xxxyjade.hiphopghetto.model.entity.Comment;

public interface CommentMessageService {

    /**
     * 发送评论数递增消息
     */
    void sentCommentCountIncreaseMessage(Comment comment);

    /**
     * 发送评论数递减消息
     */
    void sentCommentCountDecreaseMessage(Comment comment);

}
