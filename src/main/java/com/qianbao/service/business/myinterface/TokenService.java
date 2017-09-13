package com.qianbao.service.business.myinterface;

import com.qianbao.domain.Token;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description Token服务的接口类
 */
public interface TokenService {
    /**
     * 创建一个token关联上指定用户
     * @param userID 指定用户的id
     * @return 生成的token
     */
    Token createToken(long userID);

    /**
     * 检查token是否有效
     * @param model
     * @return 是否有效
     */
    boolean checkToken(Token model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    Token getToken(String authentication);

    /**
     * 清除token
     * @param userID 登录用户的id
     */
    void deleteToken(long userID);
}
