package com.qianbao.service.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lijiechu
 * @create on 17/9/5
 * @description 自定义SpringSecurity 403权限验证失败返回信息处理器
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        Result result = ResultUtil.error(HttpServletResponse.SC_FORBIDDEN,"您没有权限进行操作");
        writer.println(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
    }
}
