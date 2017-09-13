package com.qianbao.common.exception;

import com.qianbao.common.sys.Result;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijiechu
 * @create on 17/9/5
 * @description SpringMVC全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        Result result = new Result();
        result.setMessage(e.getMessage());

        if(e instanceof NoHandlerFoundException) {
            result.setCode(HttpServletResponse.SC_NOT_FOUND);
        } else if(e instanceof UnauthorizedException){
            result.setCode(HttpServletResponse.SC_FORBIDDEN);
        } else {
            result.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return result;
    }
}
