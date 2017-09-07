package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
public class AssetCreationWrapper extends Asset {

    private String[] debtNumbers;

    public String[] getDebtNumbers() {
        return debtNumbers;
    }

    public void setDebtNumbers(String[] debtNumbers) {
        this.debtNumbers = debtNumbers;
    }
}
