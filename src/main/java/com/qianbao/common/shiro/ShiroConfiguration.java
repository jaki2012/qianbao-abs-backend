package com.qianbao.common.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description shiro配置类
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.getFilters().put("statelessAuthc", statelessAccessControlFilter());
        // 拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对/auth/login登录路径不作拦截
        filterChainDefinitionMap.put("/auth/login","anon");
        // 其余路径作token验证
        filterChainDefinitionMap.put("/**", "statelessAuthc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro安全管理器:主要是身份认证的管理，缓存管理，cookie管理
     * 所以在实际开发中我们主要是和SecurityManager进行打交道的
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectFactory(statelessDefaultSubjectFactory());
        securityManager.setRealm(statelessRealm());
        // 禁用sessions作为存储策略的实现
        ((DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        return securityManager;
    }

    @Bean
    public StatelessDefaultSubjectFactory statelessDefaultSubjectFactory(){
        return new StatelessDefaultSubjectFactory();
    }

    /**
     * 自己定义的AuthorizingRealm实现
     * @return
     */
    @Bean
    public StatelessAuthorizingRealm statelessRealm(){
        StatelessAuthorizingRealm statelessAuthorizingRealm= new StatelessAuthorizingRealm();
        return statelessAuthorizingRealm;
    }

    @Bean
    public StatelessAccessControlFilter statelessAccessControlFilter(){
        return new StatelessAccessControlFilter();
    }

    /**
     * session管理器：
     * 因为关闭了session的创建，因此没必要再定期清理session
     * @return
     */
    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    @Bean
    public FilterRegistrationBean registrationBean(StatelessAccessControlFilter statelessAccessControlFilter){
        FilterRegistrationBean registration = new FilterRegistrationBean(statelessAccessControlFilter);
        // 取消Filter自动注册,不会添加到FilterChain中.
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     *   自动代理所有的advisor:
     *   由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
