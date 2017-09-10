package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.service.business.myinterface.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/{assetID}")
    public Result getAssetByID(@PathVariable("assetID") int assetID) {
        return ResultUtil.success(assetService.getAssetDetail(assetID));
    }

    @PostMapping("/{assetID}/saleAgreement")
    public Result generateSaleAgreement(@PathVariable("assetID")int assetID){
        assetService.generateSaleAgreement(assetID);
        return ResultUtil.success("生成买卖协议成功");
    }

    @PostMapping("/{assetID}/guaranteeAgreement")
    public Result generateGuaranteeAgreement(@PathVariable("assetID")int assetID){
        assetService.generateGuaranteeAgreement(assetID);
        return ResultUtil.success("生成差额补足承诺书成功");
    }

    @PostMapping("/{assetID}/productDesignAgreement")
    public Result generateProductDesignAgreement(@PathVariable("assetID")int assetID){
        assetService.generateProductDesignAgreement(assetID);
        return ResultUtil.success("生成产品设计书成功");
    }

    @PostMapping("/{assetID}/assetRatingInstruction")
    public Result generateAssetRatingInstruction(@PathVariable("assetID")int assetID){
        assetService.generateAssetRatingInstruction(assetID);
        return ResultUtil.success("配置资产评级成功");
    }

    @PostMapping("/{assetID}/accountOpinion")
    public Result generateAccountOpinion(@PathVariable("assetID")int assetID){
        assetService.generateAccountOpinion(assetID);
        return ResultUtil.success("获取审计报告成功");
    }

        @PostMapping("/{assetID}/legalOpinion")
    public Result generateLegalOpinion(@PathVariable("assetID")int assetID){
        assetService.generateAccountOpinion(assetID);
        return ResultUtil.success("生成法律意见书成功");
    }

    @PostMapping("/{assetID}/productPlanInstruction")
    public Result generateProductPlanInstruction(@PathVariable("assetID")int assetID){
        assetService.generateProductDesignAgreement(assetID);
        return ResultUtil.success("生成资产计划说明书成功");
    }

    @PostMapping("/{assetID}/posteriorSubscription")
    public Result generatePosteriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generatePosteriorSubscription(assetID);
        return ResultUtil.success("认购劣后级资产成功");
    }

    @PostMapping("/{assetID}/subPosteriorSubscription")
    public Result generateSubPosteriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generateSubPosteriorSubscription(assetID);
        return ResultUtil.success("认购次优级资产成功");
    }

    @PostMapping("/{assetID}/priorSubscription")
    public Result generatePriorSubscription(@PathVariable("assetID")int assetID){
        assetService.generatePriorSubscription(assetID);
        return ResultUtil.success("认购优先级资产成功");
    }

    @GetMapping(value = "/txs")
    // TODO: 此接口目前处于待做状态，因为返回字段。内容由银行决定 并非由后台生成
    // 暂时返回MOCK数据
    public Result getTxsRecords(){
        return ResultUtil.success(assetService.getTxRecords());
    }

    @GetMapping(value = "/{assetID}/debtsInfo")
    public Result getDebtsInfo(@PathVariable int assetID, HttpServletResponse response){
        assetService.downloadDebtsInfo(assetID,response);
        return ResultUtil.success("获取债权资料成功");
    }

}
