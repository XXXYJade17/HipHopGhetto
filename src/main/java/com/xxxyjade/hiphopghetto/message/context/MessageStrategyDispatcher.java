package com.xxxyjade.hiphopghetto.message.context;

import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.message.strategy.MessageStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageStrategyDispatcher {

    private final Map<String, MessageStrategy> strategyMap;

    public MessageStrategyDispatcher(List<MessageStrategy> strategies) {
        this.strategyMap = new HashMap<>();
        for (MessageStrategy strategy : strategies) {
            String type = strategy.getMessageType();
            if (strategyMap.containsKey(type)) {
                throw new HipHopGhettoFrameException("消息类型[" + type + "]存在重复的处理器");
            }
            strategyMap.put(type, strategy);
        }
    }

    @SuppressWarnings("unchecked")
    public void dispatch(Message message) {
        // 提取消息类型标识
        String messageType = message.getMessageType();
        if (StringUtils.isEmpty(messageType)) {
            throw new HipHopGhettoFrameException("消息缺少类型标识（message-type）");
        }

        // 查找对应的策略
        MessageStrategy strategy = strategyMap.get(messageType);
        if (strategy == null) {
            throw new HipHopGhettoFrameException("不支持的消息类型：" + messageType);
        }

        strategy.handle(message);
    }
}