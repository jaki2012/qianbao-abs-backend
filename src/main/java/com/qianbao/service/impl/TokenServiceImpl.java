package com.qianbao.service.impl;

import com.qianbao.common.shiro.StatelessAuthenticationToken;
import com.qianbao.common.sys.Constants;
import com.qianbao.service.myinterface.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 通过Redis缓存机制管理token生命周期的Impl类
 */
@Service
public class TokenServiceImpl implements TokenService {

    private RedisTemplate<Long, String> redis;

    @Autowired
    @Qualifier(value="redisTemplate")
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public StatelessAuthenticationToken createToken(long userID) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        StatelessAuthenticationToken model = new StatelessAuthenticationToken(userID, token);
        // 存储到redis并设置过期时间
        redis.boundValueOps(userID).set(token, Constants.TOKEN_EXPIRES_HOURS, TimeUnit.HOURS);
        return model;
    }

    @Override
    public boolean checkToken(StatelessAuthenticationToken model) {
        if(null == model) {
            return false;
        }
        // 服务器后台上存储的正确token
        String token = redis.boundValueOps(model.getUserId()).get();

        if(null == token || !token.equals(model.getToken())){
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOURS, TimeUnit.HOURS);
        return true;
    }

    @Override
    public StatelessAuthenticationToken getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new StatelessAuthenticationToken(userId, token);
    }

    @Override
    public void deleteToken(long userID) {
        redis.delete(userID);
    }
}
