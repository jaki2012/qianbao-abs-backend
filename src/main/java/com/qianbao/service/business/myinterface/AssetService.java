package com.qianbao.service.business.myinterface;

import com.qianbao.domain.Asset;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 资产管理Service类
 */
public interface AssetService {

    int generateAsset(String[] debtsNumbers, Asset asset);

    List<Asset> findAssets(int userID);
}
