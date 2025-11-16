package com.xxxyjade.hiphopghetto.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 收藏计数队列
    public static final String COLLECT_QUEUE = "collect_queue";
    public static final String RATING_QUEUE = "rating_queue";
    public static final String USER_QUEUE = "user_queue";

    @Bean
    public Queue userInfoQueue() {
        return new Queue(USER_QUEUE);
    }

    @Bean
    public Queue collectCountQueue() {
        return new Queue(COLLECT_QUEUE, true);
    }

    @Bean
    public Queue ratingCountQueue() {
        return new Queue(RATING_QUEUE, true);
    }

    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

}
