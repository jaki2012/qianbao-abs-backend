package com.qianbao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description 债权的实体类
 */
public class Debt {
    // 债权ID
    private String debtID;
    // 债权编号
    private String debtNumber;
    // 所属平台
    private String platform;
    // 借款编号
    private String loanNumber;
    // 借款金额
    @Min(value = 0, message = "借款金额必须大于等于0")
    private int loanMoney;
    // 借款利率
    private float loanRate;
    // 借款期限
    private int loanTerm;
    // 还款方式
    private String repaymentWay;
    // 借款用途
    private String loanUse;
    // 审核状态
    private String state;
    // 备注
    private String comment;
    // 创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    // 更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    public String getDebtID() {
        return debtID;
    }

    public void setDebtID(String debtID) {
        this.debtID = debtID;
    }

    public String getDebtNumber() {
        return debtNumber;
    }

    public void setDebtNumber(String debtNumber) {
        this.debtNumber = debtNumber;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public int getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(int loanMoney) {
        this.loanMoney = loanMoney;
    }

    public float getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(float loanRate) {
        this.loanRate = loanRate;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getRepaymentWay() {
        return repaymentWay;
    }

    public void setRepaymentWay(String repaymentWay) {
        this.repaymentWay = repaymentWay;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
