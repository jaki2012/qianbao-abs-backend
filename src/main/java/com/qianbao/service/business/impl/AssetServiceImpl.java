package com.qianbao.service.business.impl;

import com.qianbao.domain.Asset;
import com.qianbao.mapper.AssetMapper;
import com.qianbao.mapper.UserMapper;
import com.qianbao.service.business.myinterface.AssetService;
import com.qianbao.service.business.myinterface.DebtService;
import com.qianbao.service.business.myinterface.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 资产服务的实现类
 * @see AssetService
 */
@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DebtService debtService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    @Transactional
    public int generateAsset(String[] debtsIDs, Asset asset) {
//        if(asset.getBasicAsset() < 4000000000l) {
//            return 1;
//        }
        for(String debtID : debtsIDs){
            debtService.packageDebt(debtID);
        }
        // 初始化状态
        asset.setState(1);
        asset.setAssetNumber(serialNumberService.generateSerialNumber("ZC"));

        // 更新时间
        asset.setCreateTime(new Date());
        asset.setModifyTime(new Date());
        assetMapper.insert(asset);
        return 0;
    }

    @Override
    public List<Asset> findAssets(int userID) {
        int roleID = userMapper.getRoleIDByUserID(userID);
        List<Asset> assets = assetMapper.findByRoleID(roleID);
        return assets;
    }


}
