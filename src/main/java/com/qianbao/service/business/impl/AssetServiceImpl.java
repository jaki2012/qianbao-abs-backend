package com.qianbao.service.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.util.JsonUtil;
import com.qianbao.domain.Asset;
import com.qianbao.domain.AssetCreationWrapper;
import com.qianbao.domain.AssetWrapper;
import com.qianbao.mapper.AssetMapper;
import com.qianbao.mapper.CompanyMapper;
import com.qianbao.mapper.RateSettingMapper;
import com.qianbao.mapper.UserMapper;
import com.qianbao.service.business.myinterface.AssetService;
import com.qianbao.service.business.myinterface.DebtService;
import com.qianbao.service.business.myinterface.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private RateSettingMapper rateSettingMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DebtService debtService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    @Transactional
    public int generateAsset(AssetCreationWrapper assetCreationWrapper) {
//        if(asset.getBasicAsset() < 4000000000l) {
//            return 1;
//        }
        String[] debtsNumbers = assetCreationWrapper.getDebtNumbers();
        // 强制转换
        Asset asset =  assetCreationWrapper;
        for(String debtNumber : debtsNumbers){
            debtService.packageDebt(debtNumber);
        }
        // 初始化状态
        asset.setState(1);
        asset.setAssetNumber(serialNumberService.generateSerialNumber("ZC"));

        // 更新时间
        asset.setCreateTime(new Date());
        asset.setModifyTime(new Date());
        assetMapper.insert(asset);
        // 刚刚插入的asset
        int assetID = asset.getAssetID();
        assetMapper.recordDebts(debtsNumbers, assetID);
        return 0;
    }

    @Override
    public List<AssetWrapper> findAssets(int userID) {
        List<Asset> assets = null;
        List<AssetWrapper> assetWrappers = new ArrayList<>();
        int roleID = userMapper.getRoleIDByUserID(userID);
        // 如果是管理员的话则可以看到所有资产
        if(roleID == 1) {
            assets = assetMapper.findAll();

        } else {
            assets = assetMapper.findByUserID(userID);
        }

        for(Asset asset: assets) {
            AssetWrapper assetWrapper = assetMapper.findWrapperInfo(roleID, asset.getState());
            assetWrapper.fillAssetInfo(asset);
            //是否可以执行
            String url = assetWrapper.getUrl();
            boolean executable = (null != url);
            if(executable) {
                url += "/";
                url += assetWrapper.getAssetID();
                assetWrapper.setUrl(url);
            }
            assetWrapper.setExecutable(executable);
            assetWrappers.add(assetWrapper);
        }
        return assetWrappers;
    }

    @Override
    public int generateSaleAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(2);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateGuaranteeAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(3);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateProductDesignAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(4);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateAssetRatingInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(5);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateAccountOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(6);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateLegalOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(7);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateProductPlanInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(8);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generatePosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(9);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public int generateSubPosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(10);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    @Transactional
    public int generatePriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(11);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        // 当状态值为11，智能合约向银行发出分账指令
        // this.bankCredit(assetID);
        return 0;
    }

    @Override
    public int bankCredit(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        // 状态已完成
        asset.setState(12);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        return 0;
    }

    @Override
    public JSONObject getInitialOptions() {
        JSONObject initialOptions = new JSONObject();
        initialOptions.put("rateSetting", rateSettingMapper.findOne());
        initialOptions.put("investors", JsonUtil.addKeyForList(companyMapper.findByType("投资方"),"name"));
        initialOptions.put("spvs", JsonUtil.addKeyForList(companyMapper.findByType("信托机构"),"name"));
        initialOptions.put("promisers", JsonUtil.addKeyForList(companyMapper.findByType("差额支付承诺人"),"name"));
        initialOptions.put("ratingOrganisations", JsonUtil.addKeyForList(companyMapper.findByType("资产评级机构"),"name"));
        initialOptions.put("accountantFirms", JsonUtil.addKeyForList(companyMapper.findByType("会计师事务所"),"name"));
        initialOptions.put("lawFirms", JsonUtil.addKeyForList(companyMapper.findByType("律师事务所"),"name"));
        return initialOptions;
    }
}
