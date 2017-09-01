package com.qianbao.mapper;

import com.qianbao.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */

@Repository
@Mapper
public interface PermissionMapper {
    List<Permission> findAll();
    List<Permission> findByAdminUserID(int userID);
}
