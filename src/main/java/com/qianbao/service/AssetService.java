package com.qianbao.service;

import com.qianbao.domain.Asset;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 资产管理服务
 */
public interface AssetService {

    int generateAsset(String[] debtsIDs, Asset asset);
}
