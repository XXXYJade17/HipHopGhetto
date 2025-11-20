package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.List;

@Configuration
public class RabbitConfig {

    /**
     * 创建队列
     */
    @Bean
    public List<Queue> queues() {
        return List.of(
                QueueBuilder.durable(MessageQueue.COLLECTION_QUEUE)
                        .withArgument("x-dead-letter-exchange", "")     // 设置死信交换机
                        .withArgument("x-dead-letter-routing-key", MessageQueue.COLLECTION_DEAD_QUEUE)   // 设置死信路由健
                        .build(),
                QueueBuilder.durable(MessageQueue.COMMENT_QUEUE)
                        .withArgument("x-dead-letter-exchange", "")
                        .withArgument("x-dead-letter-routing-key", MessageQueue.COMMENT_DEAD_QUEUE)
                        .build(),
                QueueBuilder.durable(MessageQueue.LIKE_QUEUE)
                        .withArgument("x-dead-letter-exchange", "")
                        .withArgument("x-dead-letter-routing-key", MessageQueue.LIKE_DEAD_QUEUE)
                        .build(),
                QueueBuilder.durable(MessageQueue.RATING_QUEUE)
                        .withArgument("x-dead-letter-exchange", "")
                        .withArgument("x-dead-letter-routing-key", MessageQueue.RATING_DEAD_QUEUE)
                        .build(),
                QueueBuilder.durable(MessageQueue.USER_QUEUE)
                        .withArgument("x-dead-letter-exchange", "")
                        .withArgument("x-dead-letter-routing-key", MessageQueue.USER_DEAD_QUEUE)
                        .build(),
                new Queue(MessageQueue.DEAD_LETTER_QUEUE),
                new Queue(MessageQueue.COLLECTION_DEAD_QUEUE),
                new Queue(MessageQueue.COMMENT_DEAD_QUEUE),
                new Queue(MessageQueue.LIKE_DEAD_QUEUE),
                new Queue(MessageQueue.RATING_DEAD_QUEUE),
                new Queue(MessageQueue.USER_DEAD_QUEUE)
        );
    }

    /**
     * 创建消息转换器
     */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

}