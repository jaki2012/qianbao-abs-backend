package com.qianbao.common.util;

import com.qianbao.domain.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 获取当前授权用户信息的工具类
 */
public class UserinfoUtil {

    public static SecurityUser getUser() {
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
