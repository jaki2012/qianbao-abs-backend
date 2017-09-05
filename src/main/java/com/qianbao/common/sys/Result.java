package com.qianbao.common.sys;

import java.io.Serializable;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description SpringMVCController返回结果封装类
 */
public class Result<T> implements Serializable{

    // 返回结果码
    private Integer code;
    // 返回结果提示消息
    private String message;
    // 具体的内容
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
