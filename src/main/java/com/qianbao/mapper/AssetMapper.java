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

    @Deprecated
    List<Asset> findByRoleID(@Param("roleID") int roleID);

    Asset findOneByAssetID(@Param("assetID") int assetID);

    /**
     * 查找所有的资产列表
     * @return
     */
    List<Asset> findAll();

    /**
     * 根据用户id查找其所参与的资产交易
     * @param userID 用户id
     * @return
     */
    List<Asset> findByUserID(int userID);
}
