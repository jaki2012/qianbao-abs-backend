package com.qianbao.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.Result;
import com.qianbao.common.ResultUtil;
import com.qianbao.domain.Debt;
import com.qianbao.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
@RestController
@RequestMapping("/abs-api/v1.0")
public class MainController {

    @Autowired
    private DebtService debtService;

    @RequestMapping(value = "/debts", method = RequestMethod.GET)
    public Result acquireDebts(@RequestParam("page")int page, @RequestParam("length")int length){
        List<Debt> debts = debtService.acquireDebts(page, length);
        return ResultUtil.success(debts);
    }

    @Autowired
    AuthenticationManager authenticationManager;
    @RequestMapping(value="/doLogin",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("userName")String userName, @RequestParam("password") String password){
        String str = new String();
        try {
            // 内部登录请求
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            // 验证
            Authentication auth = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(auth);
           str = "1";
        } catch (AuthenticationException e) {
            str = "2";
        }
        return str;
    }

}
