package com.qianbao.service.business.myinterface;

import com.qianbao.domain.UserWrapper;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 用户管理服务
 */
public interface UserService {

    /**
     * 创建用户
     * @param userWrapper
     * @return 创建成功与否
     */
    int createUser(UserWrapper userWrapper);

    /**
     * 获取所有用户信息
     * @return
     */
    List<UserWrapper> getAllUsers();
}
