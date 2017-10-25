package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
public class AssetCreationWrapper extends Asset {

    private String[] debtsNumbers;

    private float ratingRate;

    private float lawyerMoney;

    private float spvRate;

    private float accountantMoney;

    public String[] getDebtsNumbers() {
        return debtsNumbers;
    }

    public void setDebtsNumbers(String[] debtsNumbers) {
        this.debtsNumbers = debtsNumbers;
    }

    public float getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(float ratingRate) {
        this.ratingRate = ratingRate;
    }

    public float getLawyerMoney() {
        return lawyerMoney;
    }

    public void setLawyerMoney(float lawyerMoney) {
        this.lawyerMoney = lawyerMoney;
    }

    public float getSpvRate() {
        return spvRate;
    }

    public void setSpvRate(float spvRate) {
        this.spvRate = spvRate;
    }

    public float getAccountantMoney() {
        return accountantMoney;
    }

    public void setAccountantMoney(float accountantMoney) {
        this.accountantMoney = accountantMoney;
    }
}
