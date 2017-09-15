package com.qianbao.common.util;

import org.springframework.stereotype.Component;

/**
 * @author lijiechu
 * @create on 17/9/14
 * @description 获取用户资料的工具类，由spring来管理
 */
@Component
public class UserinfoUtil {

    private long userID;

    private String bcToken;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getBcToken() {
        return bcToken;
    }

    public void setBcToken(String bcToken) {
        this.bcToken = bcToken;
    }
}
