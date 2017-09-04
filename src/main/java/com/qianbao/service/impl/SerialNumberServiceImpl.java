package com.qianbao.service.impl;

import com.qianbao.common.util.StringUtil;
import com.qianbao.common.util.TimeUtil;
import com.qianbao.common.redis.RedisDao;
import com.qianbao.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */
@Service
public class SerialNumberServiceImpl implements SerialNumberService {

    @Autowired
    private RedisDao redisDao;

    @Override
    public String generateSerialNumber(String bizCode) {
        // 获取今天的日期
        String dateStr = TimeUtil.getToday();
        // 构造redis的key
        String redisKey = SERIAL_NUMBER + dateStr;
        long sequence  = redisDao.incr(redisKey);
        StringBuilder serialNumberBuilder = new StringBuilder();
        serialNumberBuilder.append(bizCode).append(dateStr).append(StringUtil.fillStringWillZeroes("" + sequence));
        return serialNumberBuilder.toString();
    }
}
