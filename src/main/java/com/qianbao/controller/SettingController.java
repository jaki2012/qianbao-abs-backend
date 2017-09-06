package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.RateSetting;
import com.qianbao.service.business.myinterface.RateSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private RateSettingService rateSettingService;

    @GetMapping("/rateSetting")
    public Result getRateSetting(){
        return ResultUtil.success(rateSettingService.getDefaultRateSetting());
    }

    @PutMapping("/rateSetting")
    public Result updateRateSetting(@RequestBody RateSetting rateSetting){
        if(0 == rateSettingService.updateDefaultRateSetting(rateSetting)){
            return ResultUtil.error(400,"更新费率失败");
        }
        Result result = ResultUtil.success();
        result.setMessage("费率设置更新成功");
        result.setData(rateSettingService.getDefaultRateSetting());
        return result;
    }
}
