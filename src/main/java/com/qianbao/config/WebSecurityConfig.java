package com.qianbao.config;

import com.alibaba.fastjson.JSON;
import com.qianbao.service.security.*;
import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录后无权限用户
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler());

        // 未登录用户
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint());

        http.authorizeRequests()
                .antMatchers("/**/login")
                .permitAll()
                .antMatchers("/**/debtsInfo")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                // 防止跳转到登录页面导致404
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        Result result = ResultUtil.success("退出登录成功");
                        out.write(JSON.toJSONString(result));
                    }
                })
                .permitAll();
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user Details Service验证
        auth// .eraseCredentials(false)
                .userDetailsService(customUserDetailsService());
    }

    /**
     * 设置用户密码的加密方式为MD5加密
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }


    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean AuthenticationEntryPoint myAuthenticationEntryPoint(){
        return new MyAuthenticationEntryPoint();
    }
}
