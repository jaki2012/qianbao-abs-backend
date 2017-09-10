package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
public class AssetCreationWrapper extends Asset {

    private String[] debtsNumbers;

    private int ratingRate;

    private int lawyerMoney;

    private int spvRate;

    private int accountantMoney;

    public String[] getDebtsNumbers() {
        return debtsNumbers;
    }

    public void setDebtsNumbers(String[] debtsNumbers) {
        this.debtsNumbers = debtsNumbers;
    }

    public int getLawyerMoney() {
        return lawyerMoney;
    }

    public void setLawyerMoney(int lawyerMoney) {
        this.lawyerMoney = lawyerMoney;
    }

    public int getAccountantMoney() {
        return accountantMoney;
    }

    public void setAccountantMoney(int accountantMoney) {
        this.accountantMoney = accountantMoney;
    }

    public int getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(int ratingRate) {
        this.ratingRate = ratingRate;
    }

    public int getSpvRate() {
        return spvRate;
    }

    public void setSpvRate(int spvRate) {
        this.spvRate = spvRate;
    }
}
