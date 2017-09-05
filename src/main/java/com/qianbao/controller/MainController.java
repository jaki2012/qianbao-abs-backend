package com.qianbao.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.sys.Result;
import com.qianbao.common.sys.SysProperties;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.Asset;
import com.qianbao.domain.Debt;
import com.qianbao.domain.SecurityUser;
import com.qianbao.service.business.myinterface.AssetService;
import com.qianbao.service.business.myinterface.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description abs主要业务controller分发器
 */
@RestController
public class MainController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/allunrevieweddebts/", method = RequestMethod.GET)
    public Result initialDebtsPool(@RequestParam("page")int page, @RequestParam("length")int length) {
        JSONObject result = debtService.getUnreviewdDebts(page, length);
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/alldebts/", method = RequestMethod.GET)
    public Result queryDebts(@RequestParam("page")int page, @RequestParam("length")int length) {
        JSONObject result = debtService.getAllDebts(page, length);
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/newdebts", method = RequestMethod.GET)
    public Result acquireDebts(@RequestParam("number")int number){
        List<Debt> debts = debtService.acquireDebts(number);
        if(null == debts) {
            return ResultUtil.error(400,"每次获取债权数量不得大于1000");
        } else {
            return ResultUtil.success(debts);
        }
    }

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    public Result packageDebts(@RequestParam("debtsNumbers") String [] debtsNumbers, @RequestBody Asset asset){
        if(0 == assetService.generateAsset(debtsNumbers, asset))
            return ResultUtil.success("打包成功！");
        else
            return ResultUtil.error(400, "债权打包金额不符合要求");
    }

    @PatchMapping(value = "/debt")
    public Result returnDebt(@RequestBody Map<String,String> requestParams) {
        String state = requestParams.get("state");
        String debtNumber = requestParams.get("debtNumber");
        if(null != state && state.equals("已退回") && null != debtNumber){
            int result = debtService.returnDebt(requestParams.get("debtNumber"));
            if(1 == result) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(400, "更新失败");
            }
        } else {
            return ResultUtil.error(400, "请求参数有误" );
        }
    }

    @GetMapping(value = "/test")
    public Result test(){
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultUtil.success(user);
    }

    @GetMapping(value = "/allassets")
    public Result manageAssets(){
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userID = user.getUserID();
        List<Asset> assets = assetService.findAssets(userID);
        return ResultUtil.success(assets);
    }

}
