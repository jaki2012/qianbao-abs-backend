package com.qianbao.mapper;

import com.qianbao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
}
