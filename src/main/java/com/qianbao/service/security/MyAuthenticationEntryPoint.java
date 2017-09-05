package com.qianbao.service.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lijiechu
 * @create on 17/9/5
 * @description 未登录用户403处理类
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        // 设置请求码
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        Result result = ResultUtil.error(403, "未授权的访问：请先登录");
        // 输出null字段
        writer.println(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
    }
}
