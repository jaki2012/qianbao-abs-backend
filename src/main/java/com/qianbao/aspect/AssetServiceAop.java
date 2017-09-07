package com.qianbao.aspect;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.UserinfoUtil;
import com.qianbao.domain.ActionLog;
import com.qianbao.mapper.ActionLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    // 定义切点在AssetController下的所有以generate开头的方法
    @Pointcut("execution(* com.qianbao.controller.AssetController.generate*(..))")
    public void generateActionLogPointCut(){

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
        actionLog.setUserID(UserinfoUtil.getUser().getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }

    @AfterReturning(pointcut = "returnDebtPointCut()")
    public void generateReturnDebtLog(){
        ActionLog actionLog = new ActionLog();
        actionLog.setAction("退回债权");
        actionLog.setUserID(UserinfoUtil.getUser().getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }

    @AfterReturning(pointcut = "packageDebtsPointCut()")
    public void generatePackageDebtsLog(){
        ActionLog actionLog = new ActionLog();
        actionLog.setAction("打包债权");
        actionLog.setUserID(UserinfoUtil.getUser().getUserID());
        actionLog.setCreateTime(new Date());
        actionLogMapper.insert(actionLog);
    }
}
