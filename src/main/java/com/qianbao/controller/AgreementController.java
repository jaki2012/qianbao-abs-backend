package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.Agreement;
import com.qianbao.mapper.AgreementMapper;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lijiechu
 * @create on 17/9/15
 * @description
 */

@RestController
@RequestMapping("/agreement")
@CrossOrigin(value = "*")
public class AgreementController {

    @Autowired
    private AgreementMapper agreementMapper;

    @GetMapping("/{agreementID}")
    public Result getAgreementByID(@PathVariable("agreementID")String agreementID, HttpServletResponse response){
        Agreement agreement = agreementMapper.findByID(agreementID);
        String agreementType = "" + agreement.getAgreementType();
        String agreementFile;
        switch (agreementType) {
            case "1" : {
                agreementFile = "基础资产认购协议.pdf";
                break;
            }
            case "2" : {
                agreementFile = "差额支付承诺函.pdf";
                break;
            }
            case "3" : {
                agreementFile = "产品设计书.pdf";
                break;
            }
            case "4": {
                agreementFile = "资产评级报告.pdf";
                break;
            }
            case "5": {
                agreementFile = "审计报告.pdf";
                break;
            }
            case "6": {
                agreementFile = "律师事务所法律意见书.pdf";
                break;
            }
            case "7": {
                agreementFile = "计划说明书.pdf";
                break;
            }
            case "8": {
                agreementFile = "认购协议——钱包金服.pdf";
                break;
            }
            case "9": {
                agreementFile = "认购协议——上海银行.pdf";
                break;
            }
            case "10": {
                agreementFile = "认购协议——中融金（北京）科技有限公司.pdf";
                break;
            }
            default: {
                agreementFile = "基础资产认购协议.pdf";
            }
        }
        File file = new File("/Users/lijiechu/Desktop/ABS协议准备/" + agreementFile);
        OutputStream out = null;
        try {
            // response.setContentType("application/octet-stream; charset=utf-8");
            response.setContentType("application/pdf");
            // 解决中文乱码问题
            response.setHeader("Content-Disposition", "filename=" + new String((agreementFile).getBytes("gbk"),"iso-8859-1"));
            out = response.getOutputStream();
            out.write(FileUtil.readAsByteArray(file));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultUtil.success();
    }

    @GetMapping("/agreementPDF/{agreementType}")
    public Result getAgreementPDF(HttpServletResponse response, @PathVariable("agreementType")String agreementType) {
        String agreementFile;
        switch (agreementType) {
            case "1" : {
                agreementFile = "基础资产认购协议.pdf";
                break;
            }
            case "2" : {
                agreementFile = "差额支付承诺函.pdf";
                break;
            }
            case "3" : {
                agreementFile = "产品设计书.pdf";
                break;
            }
            case "4": {
                agreementFile = "资产评级报告.pdf";
                break;
            }
            case "5": {
                agreementFile = "审计报告.pdf";
                break;
            }
            case "6": {
                agreementFile = "律师事务所法律意见书.pdf";
                break;
            }
            case "7": {
                agreementFile = "计划说明书.pdf";
                break;
            }
            case "8": {
                agreementFile = "认购协议——钱包金服.pdf";
                break;
            }
            case "9": {
                agreementFile = "认购协议——上海银行.pdf";
                break;
            }
            case "10": {
                agreementFile = "认购协议——中融金（北京）科技有限公司.pdf";
                break;
            }
            default: {
                agreementFile = "基础资产认购协议.pdf";
            }
        }
        File file = new File("/Users/lijiechu/Desktop/ABS协议准备/" + agreementFile);
        OutputStream out = null;
            try {
                response.setContentType("application/octet-stream; charset=utf-8");
                // 解决中文乱码问题
                response.setHeader("Content-Disposition", "agreementFile; filename=" + new String((agreementFile).getBytes("gbk"),"iso-8859-1"));
                out = response.getOutputStream();
                out.write(FileUtil.readAsByteArray(file));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return ResultUtil.success();
    }
}
