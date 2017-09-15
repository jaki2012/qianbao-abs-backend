package com.qianbao.common.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qianbao.common.sys.Constants;
import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.service.myinterface.TokenService;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 访问控制过滤器 先执行isAccessAllowed 再执行onAccessDenied
 */
public class StatelessAccessControlFilter extends AccessControlFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object handler) throws Exception {
        // 如果不携带token，则必须先走登录流程
        if(null == request.getParameter("access_token")){
            onLoginFail(response, Constants.ACCESS_TOKEN_LACK);
            return false;
        }
        // 如果携带了token，则验证token是否存在并获取token对应的用户
        String authorization = request.getParameter("access_token");
        StatelessAuthenticationToken model = tokenService.getToken(authorization);

        if (tokenService.checkToken(model)) {
            // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute("userID", model.getUserId());
            try {
                // 委托给Realm进行登录
                getSubject(request, response).login(model);
            } catch (Exception e){
                onLoginFail(response, e.getMessage());
                // 停止继续传递
                return false;
            }
        } else {
            onLoginFail(response,Constants.ACCESS_TOKEN_WRONG);
            return false;
        }
        // 通过isPermitted 才能调用doGetAuthorizationInfo方法获取权限信息
        getSubject(request, response).isPermitted(((HttpServletRequest)request).getRequestURI());
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 设置请求码
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        Result result = ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "auth_error: " + errorMsg);
        // 输出null字段
        writer.println(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
    }
}
