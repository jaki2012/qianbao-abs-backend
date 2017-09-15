package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.Agreement;
import com.qianbao.mapper.AgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijiechu
 * @create on 17/9/15
 * @description
 */

@RestController
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    private AgreementMapper agreementMapper;

    @GetMapping("/{agreementID}")
    public Result getAgreementByID(@PathVariable("agreementID")String agreementID){
        return ResultUtil.success(agreementMapper.findByID(agreementID));
    }
}
