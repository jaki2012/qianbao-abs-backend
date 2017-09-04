package com.qianbao.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */
@Component
public class RedisDaoImpl implements RedisDao {

    private RedisTemplate<String, Long> redis;

    @Autowired
    @Qualifier(value="redisTemplate")
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public long incr(String key) {
        RedisAtomicLong sequence = new RedisAtomicLong(key, redis.getConnectionFactory());
        return sequence.incrementAndGet();
    }
}
