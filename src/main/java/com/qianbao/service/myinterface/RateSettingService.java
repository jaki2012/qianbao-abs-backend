package com.qianbao.service.myinterface;

import com.qianbao.domain.RateSetting;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 系统费率设置服务类
 */
public interface RateSettingService {

    // 获取默认的系统费率设置
    RateSetting getDefaultRateSetting();

    // 更新系统的默认费率设置
    int updateDefaultRateSetting(RateSetting rateSetting);
}
