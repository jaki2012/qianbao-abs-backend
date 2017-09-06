package com.qianbao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@Mapper
@Repository
public interface RoleMapper {

    @Select("Select roleID from tbRole where description=#{param0}")
    int getRoleIDbyRoleName(String roleName);
}
