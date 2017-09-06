package com.qianbao.service.business.myinterface;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.domain.Asset;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 资产管理Service类
 */
public interface AssetService {

    /**
     * 打包资产
     * @param debtsNumbers 资产所包含的债权列表
     * @param asset 生成的资产
     * @return
     */
    int generateAsset(String[] debtsNumbers, Asset asset);

    List<Asset> findAssets(int userID);

    /**
     * 生成债权买卖协议
     * @param assetID
     * @return
     */
    int generateSaleAgreement(int assetID);

    /**
     * 生成差额支付承诺书
     * @param assetID
     * @return
     */
    int generateGuaranteeAgreement(int assetID);

    /**
     * spv生成产品设计书
     * @param assetID
     * @return
     */
    int generateProductDesignAgreement(int assetID);

    /**
     * 评级机构生成资产评级说明
     * @param assetID
     * @return
     */
    int generateAssetRatingInstruction(int assetID);

    /**
     * 会计事务所生成审计信息
     * @param assetID
     * @return
     */
    int generateAccountOpinion(int assetID);

    /**
     * 律师事务所上传法律意见书
     * @param assetID
     * @return
     */
    int generateLegalOpinion(int assetID);

    /**
     * spv生成产品计划说明书
     * @param assetID
     * @return
     */
    int generateProductPlanInstruction(int assetID);

    /**
     * 劣后级认购
     * @param assetID
     * @return
     */
    int generatePosteriorSubscription(int assetID);

    /**
     * 次优级认购
     * @param assetID
     * @return
     */
    int generateSubPosteriorSubscription(int assetID);

    /**
     * 优先级资产认购
     * @param assetID
     * @return
     */
    int generatePriorSubscription(int assetID);

    /**
     * 银行放款
     * @param assetID
     * @return
     */
    int bankCredit(int assetID);

    /**
     * 获取企业机构
     * @return 各个特邀用户
     */
    JSONObject getInitialOptions();
}
