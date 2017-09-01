package com.qianbao.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.Result;
import com.qianbao.common.ResultUtil;
import com.qianbao.domain.Debt;
import com.qianbao.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/debts", method = RequestMethod.GET)
    public Result acquireDebts(@RequestParam("page")int page, @RequestParam("length")int length){
        List<Debt> debts = debtService.acquireDebts(page, length);
        return ResultUtil.success(debts);
    }
}
