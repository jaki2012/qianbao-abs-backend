package com.qianbao.mapper;

import com.qianbao.domain.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */
@Repository
@Mapper
public interface AssetMapper {
    int insert(Asset asset);

    List<Asset> findByRoleID(@Param("roleID") int roleID);
}
