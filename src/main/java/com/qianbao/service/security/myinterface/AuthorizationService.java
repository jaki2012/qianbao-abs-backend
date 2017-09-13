package com.qianbao.service.security.myinterface;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/13
 * @description
 */
public interface AuthorizationService {

    List<String> getRoles(String username);
}
