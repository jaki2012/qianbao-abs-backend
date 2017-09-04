package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.Asset;
import com.qianbao.domain.Debt;
import com.qianbao.service.AssetService;
import com.qianbao.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
@RestController
@RequestMapping("/abs-api/v1.0")
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

}
