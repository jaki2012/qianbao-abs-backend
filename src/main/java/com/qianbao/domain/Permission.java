package com.qianbao.domain;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description 权限表的业务代码
 */
public class Permission {
    // 权限ID
    private int permissionID;
    // 权限名称
    private String permissionName;
    // 权限描述
    private String description;
    // 授权链接
    private String url;

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

