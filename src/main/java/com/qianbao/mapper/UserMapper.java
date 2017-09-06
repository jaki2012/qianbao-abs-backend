package com.qianbao.mapper;

import com.qianbao.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
@Repository
@Mapper
public interface UserMapper {

    @Select("select * from tbUser where tbUser.username = #{username}")
    User findByUsername(@Param("username")String username);

    @Select("select tbUser.roleID from tbUser where tbUser.userID = #{userID}")
    int getRoleIDByUserID(@Param("userID")int userID);

    @Insert("insert into tbUser (companyID, roleID, username, password, sex, account, createTime, modifyTime) " +
            "values(#{companyID}, #{roleID}, #{username}, #{password}, #{sex}, #{account}, #{createTime}, #{modifyTime})")
    int insert(User user);

    @Select("select * from tbUser")
    List<User> findAll();

}
