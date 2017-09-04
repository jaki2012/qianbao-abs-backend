package com.qianbao.mapper;

import com.qianbao.domain.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */
@Repository
@Mapper
public interface AssetMapper {
    int insert(Asset asset);
}
