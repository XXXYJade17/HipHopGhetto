package com.xxxyjade.hiphopghetto.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类，封装常用操作
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    // 构造器注入（推荐，避免字段注入的潜在问题）
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ============================ 基础操作 ============================

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存（带过期时间）
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个缓存
     * @param key 键
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     * @param keys 键集合
     * @return 删除的数量
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 判断缓存是否存在
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置缓存过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否设置成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // ============================ 批量操作（针对模糊匹配） ============================

    /**
     * 根据前缀模糊查询所有键
     * @param prefix 键前缀（如 "commentPage::sectionId=123"）
     * @return 匹配的键集合
     */
    public Set<String> keys(String prefix) {
        return redisTemplate.keys(prefix + "*");
    }

    /**
     * 根据前缀批量删除缓存
     * @param prefix 键前缀
     * @return 删除的数量
     */
    public Long deleteByPrefix(String prefix) {
        Set<String> keys = keys(prefix);
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }
        return delete(keys);
    }

}