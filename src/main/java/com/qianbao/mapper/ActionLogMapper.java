package com.qianbao.mapper;

import com.qianbao.domain.ActionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
@Mapper
@Repository
public interface ActionLogMapper {

    @Insert("Insert into tbActionLog (action, userID, createTime) " +
            "values (#{action}, #{userID}, #{createTime})")
    int insert(ActionLog actionLog);

    @Select("Select * from tbActionLog where userID = #{userID}")
    List<ActionLog> findByUserID(@Param("userID") int userID);
}
