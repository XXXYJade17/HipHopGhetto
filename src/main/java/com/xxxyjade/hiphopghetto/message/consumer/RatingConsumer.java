package com.xxxyjade.hiphopghetto.message.consumer;

import com.xxxyjade.hiphopghetto.common.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.message.context.MessageStrategyDispatcher;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingConsumer {

    @Autowired
    private MessageStrategyDispatcher messageStrategyDispatcher;

    @RabbitListener(queues = RabbitConfig.RATING_QUEUE)
    public void handleUserInfoMessage(Rating rating) {

    }

}
