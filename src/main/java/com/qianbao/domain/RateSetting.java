package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 系统费率设置实体类
 */
public class RateSetting {

    private int id;
    // 律师费用
    private float lawyerFee;
    // 审计费用
    private float accountantFee;
    // 信托管理费率(‰)
    private float spvRate;
    // 资产评级费率(‰)
    private float ratingRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLawyerFee() {
        return lawyerFee;
    }

    public void setLawyerFee(int lawyerFee) {
        this.lawyerFee = lawyerFee;
    }

    public float getAccountantFee() {
        return accountantFee;
    }

    public void setAccountantFee(int accountantFee) {
        this.accountantFee = accountantFee;
    }

    public float getSpvRate() {
        return spvRate;
    }

    public void setSpvRate(int spvRate) {
        this.spvRate = spvRate;
    }

    public float getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(int ratingRate) {
        this.ratingRate = ratingRate;
    }
}
