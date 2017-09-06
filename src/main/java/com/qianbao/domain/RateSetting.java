package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 系统费率设置实体类
 */
public class RateSetting {

    private int id;
    // 律师费用
    private int lawyerFee;
    // 审计费用
    private int accountantFee;
    // 信托管理费率(‰)
    private int spvRate;
    // 资产评级费率(‰)
    private int ratingRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawyerFee() {
        return lawyerFee;
    }

    public void setLawyerFee(int lawyerFee) {
        this.lawyerFee = lawyerFee;
    }

    public int getAccountantFee() {
        return accountantFee;
    }

    public void setAccountantFee(int accountantFee) {
        this.accountantFee = accountantFee;
    }

    public int getSpvRate() {
        return spvRate;
    }

    public void setSpvRate(int spvRate) {
        this.spvRate = spvRate;
    }

    public int getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(int ratingRate) {
        this.ratingRate = ratingRate;
    }
}
