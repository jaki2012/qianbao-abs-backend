package com.qianbao.service.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.domain.Asset;
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
    public int generateAsset(String[] debtsNumbers, Asset asset) {
//        if(asset.getBasicAsset() < 4000000000l) {
//            return 1;
//        }
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
        return 0;
    }

    @Override
    public List<Asset> findAssets(int userID) {
        int roleID = userMapper.getRoleIDByUserID(userID);
        // 如果是管理员的话则可以看到所有资产
        if(roleID == 1) {
            return assetMapper.findAll();
        }
        List<Asset> assets = assetMapper.findByUserID(userID);
        return assets;
    }

    @Override
    public int generateSaleAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(2);
        return 0;
    }

    @Override
    public int generateGuaranteeAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(3);
        return 0;
    }

    @Override
    public int generateProductDesignAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(4);
        return 0;
    }

    @Override
    public int generateAssetRatingInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(5);
        return 0;
    }

    @Override
    public int generateAccountOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(6);
        return 0;
    }

    @Override
    public int generateLegalOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(7);
        return 0;
    }

    @Override
    public int generateProductPlanInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(8);
        return 0;
    }

    @Override
    public int generatePosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(9);
        return 0;
    }

    @Override
    public int generateSubPosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(10);
        return 0;
    }

    @Override
    @Transactional
    public int generatePriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(11);
        // 当状态值为11，智能合约向银行发出分账指令
        this.bankCredit(assetID);
        return 0;
    }

    @Override
    public int bankCredit(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        // 状态已完成
        asset.setState(12);
        return 0;
    }

    @Override
    public JSONObject getInitialOptions() {
        JSONObject initialOptions = new JSONObject();
        initialOptions.put("rateSetting", rateSettingMapper.findOne());
        initialOptions.put("investors", companyMapper.findByType("投资方"));
        initialOptions.put("spvs", companyMapper.findByType("信托机构"));
        initialOptions.put("promisers", companyMapper.findByType("差额支付承诺人"));
        initialOptions.put("ratingOrganisations", companyMapper.findByType("资产评级机构"));
        initialOptions.put("accountantFirms", companyMapper.findByType("会计师事务所"));
        initialOptions.put("lawFirms", companyMapper.findByType("律师事务所"));
        return initialOptions;
    }
}
