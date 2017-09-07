package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
public class AssetWrapper extends Asset {

    // 状态名字
    private String stateName;

    // 是否可以进行操作
    private boolean executable;

    // 操作URL
    private String url;

    // 操作URL文字提示
    private String urlAlt;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlAlt() {
        return urlAlt;
    }

    public void setUrlAlt(String urlAlt) {
        this.urlAlt = urlAlt;
    }

    // 逐个属性填充
    public void fillAssetInfo(Asset asset){
        this.setAssetID(asset.getAssetID());
        this.setAssetNumber(asset.getAssetNumber());
        this.setProductName(asset.getProductName());
        this.setProductType(asset.getProductType());
        this.setBasicAsset(asset.getBasicAsset());
        this.setProductSize(asset.getProductSize());
        this.setCreditor(asset.getCreditor());
        this.setPosteriorSubscriber(asset.getPosteriorSubscriber());
        this.setSubPosteriorSubscriber(asset.getSubPosteriorSubscriber());
        this.setPriorSubscriber(asset.getPriorSubscriber());
        this.setExpectedProfitRate(asset.getExpectedProfitRate());
        this.setPaymentWay(asset.getPaymentWay());
        this.setSpv(asset.getSpv());
        this.setDifferencePaymentPromiser(asset.getDifferencePaymentPromiser());
        this.setRatingOrganisation(asset.getRatingOrganisation());
        this.setAccountingFirm(asset.getAccountingFirm());
        this.setLawFirm(asset.getLawFirm());
        this.setCreateTime(asset.getCreateTime());
        this.setModifyTime(asset.getModifyTime());
        this.setComment(asset.getComment());
    }
}
