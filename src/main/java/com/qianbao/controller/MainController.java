package com.qianbao.controller;

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
@RequestMapping(SysProperties.API_VERSION_URLPATH)
public class MainController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/debts", method = RequestMethod.GET)
    public Result acquireDebts(@RequestParam("page")int page, @RequestParam("length")int length){
        List<Debt> debts = debtService.acquireDebts(page, length);
        return ResultUtil.success(debts);
    }

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    public Result packageDebts(@RequestParam("debtsIDs") String [] debtsIDs, @RequestBody Asset asset){
        if(0 == assetService.generateAsset(debtsIDs, asset))
            return ResultUtil.success(debtsIDs[0]);
        else
            return ResultUtil.error(400, "债权打包金额不符合要求");
    }

    @PatchMapping(value = "/debt")
    public Result returnDebt(@RequestBody Map<String,String> requestParams) {
        String state = requestParams.get("state");
        String debtID = requestParams.get("debtID");
        if(null != state && state.equals("已退回") && null != debtID){
            int result = debtService.returnDebt(requestParams.get("debtID"));
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

    @GetMapping(value = "/assets")
    public Result manageAssets(){
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userID = user.getUserID();
        List<Asset> assets = assetService.findAssets(userID);
        return ResultUtil.success(assets);
    }

}
