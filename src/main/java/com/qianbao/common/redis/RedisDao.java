package com.qianbao.common.redis;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description redis缓存dao，主要用于业务自增流水号的生成
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
