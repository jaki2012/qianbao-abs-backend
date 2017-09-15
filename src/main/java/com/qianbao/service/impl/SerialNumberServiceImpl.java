package com.qianbao.service.impl;

import com.qianbao.common.util.StringUtil;
import com.qianbao.common.util.TimeUtil;
import com.qianbao.common.redis.RedisDao;
import com.qianbao.service.myinterface.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 流水号生成服务实现类
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
        // 添加bizCode以避免不同业务之间的流水单号共用
        String redisKey = bizCode + SERIAL_NUMBER + dateStr;
        long sequence  = redisDao.incr(redisKey);
        StringBuilder serialNumberBuilder = new StringBuilder();
        serialNumberBuilder.append(bizCode).append(dateStr).append(StringUtil.fillStringWillZeroes("" + sequence));
        return serialNumberBuilder.toString();
    }
}
