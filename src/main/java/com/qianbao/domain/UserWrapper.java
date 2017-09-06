package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description User包装类，用于适配前端请求创建的用户对象
 */
public class UserWrapper {

    private String companyName;

    private String companyType;

    private String account;

    private String username;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
