package com.qianbao.service.business.impl;

import com.qianbao.domain.RateSetting;
import com.qianbao.mapper.RateSettingMapper;
import com.qianbao.service.business.myinterface.RateSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@Service
public class RateSettingServiceImpl implements RateSettingService {

    @Autowired
    private RateSettingMapper rateSettingMapper;

    @Override
    // TODO: 捕获相关异常
    public RateSetting getDefaultRateSetting() {
        return rateSettingMapper.findOne();
    }

    @Override
    public int updateDefaultRateSetting(RateSetting rateSetting) {
        return rateSettingMapper.update(rateSetting);
    }
}
