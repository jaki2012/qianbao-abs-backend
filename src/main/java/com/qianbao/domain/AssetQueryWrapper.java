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
}
