package com.qianbao.redis;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */

public interface RedisDao {

    String get(String key);
    /**
     * 根据key值实现自增
     * @param key
     * @return
     */
    long incr(String key);
}
