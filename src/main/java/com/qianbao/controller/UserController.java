package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.UserWrapper;
import com.qianbao.service.business.myinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description 用户操作相关
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public Result createUser(@RequestBody UserWrapper userWrapper){
        Result result = ResultUtil.success();
        userService.createUser(userWrapper);
        result.setData("用户创建成功");
        return result;
    }

    @GetMapping("/all")
    public Result getAllUsers(){
        return ResultUtil.success(userService.getAllUsers());
    }

}
