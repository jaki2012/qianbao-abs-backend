package com.qianbao.service.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.sys.SysProperties;
import com.qianbao.common.util.JsonUtil;
import com.qianbao.common.util.UserinfoUtil;
import com.qianbao.domain.*;
import com.qianbao.mapper.*;
import com.qianbao.service.business.myinterface.AssetService;
import com.qianbao.service.business.myinterface.DebtService;
import com.qianbao.service.business.myinterface.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    private RestTemplate restTemplate;

    @Autowired
    private UserinfoUtil userinfoUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RateSettingMapper rateSettingMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DebtMapper debtMapper;

    @Autowired
    private TxRecordMapper txRecordMapper;

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
        String[] debtsNumbers = assetCreationWrapper.getDebtsNumbers();
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
        // 区块链交互
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers",peers);
        params.put("fcn","proInfoUpload");
        JSONArray args  = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        JSONObject bcAssetInfo = new JSONObject();
        bcAssetInfo.put("ProductID", ""+asset.getAssetID());
        bcAssetInfo.put("ProductName", asset.getProductName());
        bcAssetInfo.put("ProductType", asset.getProductType());
        bcAssetInfo.put("BasicAssets", "asset" + asset.getBasicAsset());
        bcAssetInfo.put("ProjectScale", asset.getProductSize());
        bcAssetInfo.put("Originators", asset.getCreditor());
        JSONArray investors = new JSONArray();
        investors.add(asset.getPosteriorSubscriber());
        investors.add(asset.getSubPosteriorSubscriber());
        investors.add(asset.getPriorSubscriber());
        bcAssetInfo.put("Investor", investors);
        bcAssetInfo.put("ExpectedReturn", ""+asset.getExpectedProfitRate());
        bcAssetInfo.put("PaymentMethod", asset.getPaymentWay());
        bcAssetInfo.put("TrustInstitution", asset.getSpv());
        bcAssetInfo.put("DifferenceComplement", asset.getDifferencePaymentPromiser());
        bcAssetInfo.put("AssetRatingAgency", asset.getRatingOrganisation());
        bcAssetInfo.put("AccountFirm", asset.getAccountingFirm());
        bcAssetInfo.put("LawOffice", asset.getLawFirm());
        // 费率信息存放在wrapper上面
        bcAssetInfo.put("TrustManagementFee", assetCreationWrapper.getSpvRate());
        bcAssetInfo.put("AssetRatingFee", assetCreationWrapper.getRatingRate());
        bcAssetInfo.put("AccountancyFee", assetCreationWrapper.getAccountantMoney());
        bcAssetInfo.put("CounselFee", assetCreationWrapper.getLawyerMoney());
        JSONObject basicCreditorInfo = new JSONObject();
        basicCreditorInfo.put("Url", "www.qianbao.com/cc/12");
        basicCreditorInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        bcAssetInfo.put("BasicCreditorInfo", basicCreditorInfo);
        bcAssetInfo.put("Remark", asset.getComment());
        args.add(JSONObject.toJSONString(bcAssetInfo));
        params.put("args", args);
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                SysProperties.BLOCKCHAIN_SDK_BASEURL+ "channels/mychannel/chaincodes/ClaimsPackageInfo", HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
        assetMapper.recordDebts(debtsNumbers, assetID);

        assetMapper.recordExtraInfo(assetCreationWrapper);
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
            StringBuilder adjustedUrl = new StringBuilder();
            String url = assetWrapper.getUrl();
            boolean executable = (null != url);
            if(executable) {
                adjustedUrl.append("/asset/").append(assetWrapper.getAssetID()).append(url);
                assetWrapper.setUrl(adjustedUrl.toString());
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

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "assetSaleAgreementUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateGuaranteeAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(3);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "guaranteeAgreementUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateProductDesignAgreement(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(4);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "trustManageementUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateAssetRatingInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(5);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "assetRatingInstructionUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateAccountOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(6);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "accountOpinionUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateLegalOpinion(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(7);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "counselOpinionUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generateProductPlanInstruction(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(8);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "productPlanInstructionUpload");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        return 0;
    }

    @Override
    public int generatePosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(9);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "inferiorAssetObtain");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        recordPosteriorSubscription(assetID);
        return 0;
    }

    public int recordPosteriorSubscription(int assetID){
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "inferiorAssetObtainRecording");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        args.add("RECORDID01");
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("ProductID", "" + assetID);
        bcSaleAgreementInfo.put("WaterFlowNumber", "11111111");
        bcSaleAgreementInfo.put("WaterFlowNumberTime", "2017-08");
        bcSaleAgreementInfo.put("FromAccount", "aaa");
        bcSaleAgreementInfo.put("ToAccount","aaaaa");
        bcSaleAgreementInfo.put("BbMount", 1000.0);
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);

        return 0;
    }

    @Override
    public int generateSubPosteriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(10);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "subprimeAssetObtain");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        recordSubPosteriorSubscription(assetID);
        return 0;
    }

    public int recordSubPosteriorSubscription(int assetID){
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "subprimeAssetsObtainRecording");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        args.add("RECORDID01");
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("ProductID", "" + assetID);
        bcSaleAgreementInfo.put("WaterFlowNumber", "11111111");
        bcSaleAgreementInfo.put("WaterFlowNumberTime", "2017-08");
        bcSaleAgreementInfo.put("FromAccount", "aaa");
        bcSaleAgreementInfo.put("ToAccount","aaaaa");
        bcSaleAgreementInfo.put("BbMount", 1000.0);
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);

        return 0;
    }

    @Override
    @Transactional
    public int generatePriorSubscription(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        asset.setState(11);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);

        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "priorityAssetObtain");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("Url", "www.qianbao.com/cc/11");
        bcSaleAgreementInfo.put("Hashcode", "40b3fa8de4e01e5b37928ff03c7c6f0b");
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        recordPriorSubscription(assetID);
        // 当状态值为11，智能合约向银行发出分账指令
        this.bankCredit(assetID);
        return 0;
    }

    public int recordPriorSubscription(int assetID){
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "priorityAssetObtainRecording");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        args.add("RECORDID01");
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("ProductID", "" + assetID);
        bcSaleAgreementInfo.put("WaterFlowNumber", "11111111");
        bcSaleAgreementInfo.put("WaterFlowNumberTime", "2017-08");
        bcSaleAgreementInfo.put("FromAccount", "aaa");
        bcSaleAgreementInfo.put("ToAccount","aaaaa");
        bcSaleAgreementInfo.put("BbMount", 1000.0);
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);

        return 0;
    }

    @Override
    public int bankCredit(int assetID) {
        Asset asset = assetMapper.findOneByAssetID(assetID);
        // 状态已完成
        asset.setState(12);
        asset.setModifyTime(new Date());
        assetMapper.update(asset);
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "breakAccountRecording");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        args.add("RECORDID01");
        JSONObject bcSaleAgreementInfo = new JSONObject();
        bcSaleAgreementInfo.put("ProductID", "" + assetID);
        bcSaleAgreementInfo.put("WaterFlowNumber", "11111111");
        bcSaleAgreementInfo.put("WaterFlowNumberTime", "2017-08");
        bcSaleAgreementInfo.put("FromAccount", "aaa");
        bcSaleAgreementInfo.put("ToAccount","aaaaa");
        bcSaleAgreementInfo.put("BbMount", 1000.0);
        args.add(JSONObject.toJSONString(bcSaleAgreementInfo));
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
        recordBankCredit(assetID);
        return 0;
    }

    public int recordBankCredit(int assetID){
        String url = SysProperties.BLOCKCHAIN_SDK_BASEURL + "channels/mychannel/chaincodes/ClaimsPackageInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + SysProperties.ORG2_TOKEN);
        headers.set("content-type", "application/json");

        JSONObject params = new JSONObject();
        JSONArray peers = new JSONArray();
        peers.add("localhost:8051");
        params.put("peers", peers);
        params.put("fcn", "finishBreakAccountRecording");
        JSONArray args = new JSONArray();
        args.add("user" + userinfoUtil.getUserID());
        args.add("" + assetID);
        params.put("args", args);

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string
                url, HttpMethod.POST, entity, String.class);
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

    @Override
    public List<TxRecord> getTxRecords() {
        return txRecordMapper.findAll();
    }

    @Override
    public AssetQueryWrapper getAssetDetail(int assetID) {
        return assetMapper.getByAssetID(assetID);
    }

    @Override
    public void downloadDebtsInfo(int assetID, HttpServletResponse response) {
       List<Debt> debts = debtMapper.findByAssetID(assetID);
       String debtsStr = JSON.toJSONString(debts);
       OutputStream out = null;
       byte[] bytes = debtsStr.getBytes();

        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            // 解决中文乱码问题
            response.setHeader("Content-Disposition", "debtsInfo; filename=" + new String(("asset"+assetID+"_debtsInfo.txt").getBytes("gbk"),"iso-8859-1"));
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
