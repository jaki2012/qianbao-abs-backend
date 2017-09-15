package com.qianbao.service.impl;

import com.qianbao.common.sys.SysProperties;
import com.qianbao.domain.ActionLog;
import com.qianbao.domain.Company;
import com.qianbao.domain.User;
import com.qianbao.domain.UserWrapper;
import com.qianbao.mapper.ActionLogMapper;
import com.qianbao.mapper.CompanyMapper;
import com.qianbao.mapper.RoleMapper;
import com.qianbao.mapper.UserMapper;
import com.qianbao.service.myinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Override
    @Transactional
    public int createUser(UserWrapper userWrapper) {

        String companyName = userWrapper.getCompanyName();
        Company company = companyMapper.findByCompanyName(companyName);
        if(null == company){
            company.setCompanyName(userWrapper.getCompanyName());
            company.setCompanyType(userWrapper.getCompanyType());
            // 设置创建时间、更新时间
            company.setCreateTime(new Date());
            company.setModifyTime(new Date());
            // 插入后会获得company的id，也就是主键
            companyMapper.insert(company);
        } else {
            // Currently We do nothing here;
        }

        User user = new User();
        // 设置登录用户名（在ABS一期里面也代指简称）
        user.setUsername(userWrapper.getUsername());
        // 设置用户代币账户
        user.setAccount(userWrapper.getAccount());
        // 设置用户的公司
        user.setCompanyID(company.getCompanyID());
        // 设置用户的角色
        user.setRoleID(roleMapper.getRoleIDbyRoleName(userWrapper.getCompanyType()));
        // 以下默认设置可以通过数据库来做 也可以通过后台来做
        user.setSex(SysProperties.USER_DEFAULT_SEX);
        user.setPassword(SysProperties.USER_DEFAULT_PASSWORD);
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userMapper.insert(user);
        return 1;
    }

    @Override
    public List<UserWrapper> getAllUsers() {
        List<User> users = userMapper.findAll();
        // 返回结果的包装类
        List<UserWrapper> userWrappers = new ArrayList<>();
        for(User user : users) {
            Company company = companyMapper.findByCompanyID(user.getCompanyID());

            UserWrapper userWrapper = new UserWrapper();
            userWrapper.setUserID(user.getUserID());
            userWrapper.setAccount(user.getAccount());
            userWrapper.setUsername(user.getUsername());
            userWrapper.setCreateTime(user.getCreateTime());
            userWrapper.setCompanyName(company.getCompanyName());
            userWrapper.setCompanyType(company.getCompanyType());

            userWrappers.add(userWrapper);
        }

        return userWrappers;
    }

    @Override
    public List<ActionLog> getActionLogs(int userID) {
        return actionLogMapper.findByUserID(userID);
    }
}
