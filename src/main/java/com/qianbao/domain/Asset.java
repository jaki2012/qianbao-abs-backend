package com.qianbao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 资产表的业务代码
 */
public class Asset {
    // 资产id
    private int  assetID;
    // 资产编号
    private String assetNumber;
    // 产品名称
    private String productName;
    private String productType;
    // 基础资产
    private long basicAsset;
    // 项目规模
    private long productSize;
    // 原始债权人
    private String creditor;
    private String posteriorSubscriber;
    private String subPosteriorSubscriber;
    private String priorSubscriber;
    // 预期收益率
    private int expectedProfitRate;
    // 支付方式
    private String paymentWay;
    private String spv;
    private String differencePaymentPromiser;
    private String ratingOrganisation;
    private String accountingFirm;
    private String lawFirm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
    private String comment;
    private int state;

    public int getAssetID() {
        return assetID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public long getBasicAsset() {
        return basicAsset;
    }

    public void setBasicAsset(long basicAsset) {
        this.basicAsset = basicAsset;
    }

    public long getProductSize() {
        return productSize;
    }

    public void setProductSize(long productSize) {
        this.productSize = productSize;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getPosteriorSubscriber() {
        return posteriorSubscriber;
    }

    public void setPosteriorSubscriber(String posteriorSubscriber) {
        this.posteriorSubscriber = posteriorSubscriber;
    }

    public String getSubPosteriorSubscriber() {
        return subPosteriorSubscriber;
    }

    public void setSubPosteriorSubscriber(String subPosteriorSubscriber) {
        this.subPosteriorSubscriber = subPosteriorSubscriber;
    }

    public String getPriorSubscriber() {
        return priorSubscriber;
    }

    public void setPriorSubscriber(String priorSubscriber) {
        this.priorSubscriber = priorSubscriber;
    }

    public int getExpectedProfitRate() {
        return expectedProfitRate;
    }

    public void setExpectedProfitRate(int expectedProfitRate) {
        this.expectedProfitRate = expectedProfitRate;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getSpv() {
        return spv;
    }

    public void setSpv(String spv) {
        this.spv = spv;
    }

    public String getDifferencePaymentPromiser() {
        return differencePaymentPromiser;
    }

    public void setDifferencePaymentPromiser(String differencePaymentPromiser) {
        this.differencePaymentPromiser = differencePaymentPromiser;
    }

    public String getRatingOrganisation() {
        return ratingOrganisation;
    }

    public void setRatingOrganisation(String ratingOrganisation) {
        this.ratingOrganisation = ratingOrganisation;
    }

    public String getAccountingFirm() {
        return accountingFirm;
    }

    public void setAccountingFirm(String accountingFirm) {
        this.accountingFirm = accountingFirm;
    }

    public String getLawFirm() {
        return lawFirm;
    }

    public void setLawFirm(String lawFirm) {
        this.lawFirm = lawFirm;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
