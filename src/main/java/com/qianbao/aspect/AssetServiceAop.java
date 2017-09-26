package com.qianbao.aspect;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.shiro.StatelessAuthenticationToken;
import com.qianbao.common.sys.Result;
import com.qianbao.common.sys.SysProperties;
import com.qianbao.common.util.UserinfoUtil;
import com.qianbao.domain.ActionLog;
import com.qianbao.domain.User;
import com.qianbao.mapper.ActionLogMapper;
import com.qianbao.service.myinterface.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
@Component
@Aspect
public class AssetServiceAop {

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Autowired
    private UserinfoUtil userinfoUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    // 定义切点在AssetController下的所有以generate开头的方法
    @Pointcut("execution(* com.qianbao.controller.AssetController.generate*(..))")
    public void generateActionLogPointCut(){

    }

    // 定义切点在非登录controller下 获取其userID
    @Pointcut("execution(* com.qianbao.controller.*.*(..)) && " +
            "!execution(* com.qianbao.controller.AuthController.login(..))")
    public void userIDPointCut(){

    }

    @Before("userIDPointCut()")
    public void setCurrentUserID(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        StatelessAuthenticationToken token = tokenService.getToken(request.getParameter("access_token"));
        userinfoUtil.setUserID((Long)token.getPrincipal());

        StringBuilder urlBuilder = new StringBuilder(SysProperties.BLOCKCHAIN_SDK_BASEURL);
        urlBuilder.append("users");
        JSONObject params = new JSONObject();
        params.put("username", "user"+userinfoUtil.getUserID());
        params.put("orgName", "org2");
        JSONObject json= restTemplate.postForEntity(urlBuilder.toString(), params, JSONObject.class).getBody();
        userinfoUtil.setBcToken(json.getString("token"));

    }


    // 退回债权日志切点
    @Pointcut("execution(* com.qianbao.controller.MainController.returnDebt(..))")
    public void returnDebtPointCut(){

    }

    // 打包债权日志切点
    @Pointcut("execution(* com.qianbao.controller.MainController.packageDebts(..))")
    public void packageDebtsPointCut(){

    }

    @AfterReturning(pointcut = "generateActionLogPointCut()", returning = "result")
    public void generateActionLog(JoinPoint joinPoint, Result result){
        System.out.println(joinPoint.getSignature().getName());
        ActionLog actionLog = new ActionLog();
        actionLog.setAction((String) result.getData());
        actionLog.setUserID(((User)joinPoint.getArgs()[0]).getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }

    @AfterReturning(pointcut = "returnDebtPointCut()")
    public void generateReturnDebtLog(JoinPoint joinPoint){
        ActionLog actionLog = new ActionLog();
        actionLog.setAction("退回债权");
        actionLog.setUserID(((User)joinPoint.getArgs()[0]).getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }

    @AfterReturning(pointcut = "packageDebtsPointCut()")
    public void generatePackageDebtsLog(JoinPoint joinPoint){
        ActionLog actionLog = new ActionLog();
        actionLog.setAction("打包债权");
        actionLog.setUserID(((User)joinPoint.getArgs()[0]).getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }
}
