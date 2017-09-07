package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.service.business.myinterface.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description 资产管理业务分发器Controller
 */

@RestController
@RequestMapping(value = "/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping("/saleAgreement/{assetID}")
    public Result generateSaleAgreement(@PathVariable("assetID")int assetID){
        assetService.generateSaleAgreement(assetID);
        return ResultUtil.success("生成买卖协议成功");
    }

    @PostMapping("/guaranteeAgreement/{assetID}")
    public Result generateGuaranteeAgreement(@PathVariable("assetID")int assetID){
        assetService.generateGuaranteeAgreement(assetID);
        return ResultUtil.success("生成差额补足承诺书成功");
    }

    @PostMapping("/productDesignAgreement/{assetID}")
    public Result generateProductDesignAgreement(@PathVariable("assetID")int assetID){
        assetService.generateProductDesignAgreement(assetID);
        return ResultUtil.success("生成产品设计书成功");
    }

    @PostMapping("/assetRatingInstruction/{assetID}")
    public Result generateAssetRatingInstruction(@PathVariable("assetID")int assetID){
        assetService.generateAssetRatingInstruction(assetID);
        return ResultUtil.success("配置资产评级成功");
    }

    @PostMapping("/accountOpinion/{assetID}")
    public Result generateAccountOpinion(@PathVariable("assetID")int assetID){
        assetService.generateAccountOpinion(assetID);
        return ResultUtil.success("获取审计报告成功");
    }

    @PostMapping("/legalOpinion/{assetID}")
    public Result generateLegalOpinion(@PathVariable("assetID")int assetID){
        assetService.generateAccountOpinion(assetID);
        return ResultUtil.success("生成法律意见书成功");
    }

    @PostMapping("/productPlanInstruction/{assetID}")
    public Result generateProductPlanInstruction(@PathVariable("assetID")int assetID){
        assetService.generateProductDesignAgreement(assetID);
        return ResultUtil.success("生成资产计划说明书成功");
    }

    @PostMapping("/posteriorSubscription/{assetID}")
    public Result generatePosteriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generatePosteriorSubscription(assetID);
        return ResultUtil.success("认购劣后级资产成功");
    }

    @PostMapping("/subPosteriorSubscription/{assetID}")
    public Result generateSubPosteriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generateSubPosteriorSubscription(assetID);
        return ResultUtil.success("认购次优级资产成功");
    }

    @PostMapping("/priorSubscription/{assetID}")
    public Result generatePriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generatePriorSubscription(assetID);
        return ResultUtil.success("认购优先级资产成功");
    }

}
