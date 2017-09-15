package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description 资产详情页包装类
 */
public class AssetQueryWrapper extends Asset{

    private int ratingMoney;

    private int lawyerMoney;

    private int spvMoney;

    private int accountantMoney;

    // 债权买卖协议
    private String saleAgreementUrl;
    // 担保协议
    private String guaranteeAgreementUrl;
    // 资产评级说明
    private String ratingInstructionUrl;
    // 法律意见书
    private String legalOpinionUrl;
    // 会计意见书
    private String accountantOpinionUrl;
    // 产品计划说明书
    private String productPlanInstructionUrl;
    // 认购协议
    private String subscriptionAgreementUrl;

    public int getRatingMoney() {
        return ratingMoney;
    }

    public void setRatingMoney(int ratingMoney) {
        this.ratingMoney = ratingMoney;
    }

    public int getLawyerMoney() {
        return lawyerMoney;
    }

    public void setLawyerMoney(int lawyerMoney) {
        this.lawyerMoney = lawyerMoney;
    }

    public int getSpvMoney() {
        return spvMoney;
    }

    public void setSpvMoney(int spvMoney) {
        this.spvMoney = spvMoney;
    }

    public int getAccountantMoney() {
        return accountantMoney;
    }

    public void setAccountantMoney(int accountantMoney) {
        this.accountantMoney = accountantMoney;
    }

    public String getSaleAgreementUrl() {
        return saleAgreementUrl;
    }

    public void setSaleAgreementUrl(String saleAgreementUrl) {
        this.saleAgreementUrl = saleAgreementUrl;
    }

    public String getGuaranteeAgreementUrl() {
        return guaranteeAgreementUrl;
    }

    public void setGuaranteeAgreementUrl(String guaranteeAgreementUrl) {
        this.guaranteeAgreementUrl = guaranteeAgreementUrl;
    }

    public String getRatingInstructionUrl() {
        return ratingInstructionUrl;
    }

    public void setRatingInstructionUrl(String ratingInstructionUrl) {
        this.ratingInstructionUrl = ratingInstructionUrl;
    }

    public String getLegalOpinionUrl() {
        return legalOpinionUrl;
    }

    public void setLegalOpinionUrl(String legalOpinionUrl) {
        this.legalOpinionUrl = legalOpinionUrl;
    }

    public String getAccountantOpinionUrl() {
        return accountantOpinionUrl;
    }

    public void setAccountantOpinionUrl(String accountantOpinionUrl) {
        this.accountantOpinionUrl = accountantOpinionUrl;
    }

    public String getProductPlanInstructionUrl() {
        return productPlanInstructionUrl;
    }

    public void setProductPlanInstructionUrl(String productPlanInstructionUrl) {
        this.productPlanInstructionUrl = productPlanInstructionUrl;
    }

    public String getSubscriptionAgreementUrl() {
        return subscriptionAgreementUrl;
    }

    public void setSubscriptionAgreementUrl(String subscriptionAgreementUrl) {
        this.subscriptionAgreementUrl = subscriptionAgreementUrl;
    }
}
