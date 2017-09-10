package com.qianbao.domain;

import java.util.Date;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description 交易记录
 */
public class TxRecord {

    private int id;
    // 交易流水号
    private String txNumber;
    // 用户民
    private String username;

    private String userType;

    private String txType;

    private int txMoney;

    private String assetNumber;

    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxNumber() {
        return txNumber;
    }

    public void setTxNumber(String txNumber) {
        this.txNumber = txNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public int getTxMoney() {
        return txMoney;
    }

    public void setTxMoney(int txMoney) {
        this.txMoney = txMoney;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
