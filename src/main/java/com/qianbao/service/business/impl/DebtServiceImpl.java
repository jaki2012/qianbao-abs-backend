package com.qianbao.service.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.domain.Debt;
import com.qianbao.mapper.DebtMapper;
import com.qianbao.service.business.myinterface.DebtService;
import com.qianbao.service.business.myinterface.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
@Service
public class DebtServiceImpl implements DebtService  {

    @Autowired
    private DebtMapper debtMapper;

    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    public JSONObject getUnreviewdDebts(int page, int length) {
        JSONObject result = new JSONObject();
        int start = (page-1) * length;
        List<Debt> debts = debtMapper.findUnreviewdByPage(start, length);
        result.put("total", debtMapper.findAllUnreviewd().size());
        result.put("debts", debts);
        return result;
    }

    @Override
    public JSONObject getUnreviewdDebts(int page, int length, Date startDate, Date endDate) {
        JSONObject result = new JSONObject();
        int start = (page-1) * length;
        List<Debt> debts = debtMapper.findUnreviewdByPageAndDate(start, length,startDate, endDate);
        result.put("total", debtMapper.findAllUnreviewdByDate(startDate, endDate).size());
        result.put("debts", debts);
        return result;
    }

    @Override
    public JSONObject getAllDebts(int page, int length, Date startDate, Date endDate) {
        JSONObject result = new JSONObject();
        int start = (page-1) * length;
        List<Debt> debts = debtMapper.findAllByPage(start, length,startDate, endDate);
        result.put("total", debtMapper.findAll(startDate,endDate).size());
        result.put("debts", debts);
        return result;
    }

    @Override
    @Transactional
    // TODO: 捕获编号相同的异常
    public List<Debt> acquireDebts(int number) {
        if(1000 < number) {
            return null;
        } else {
            List<Debt> debts =  debtMapper.acquireDebts(number);
            for(Debt debt: debts) {
                // 设置最新时间 模拟获取债权业务
                debt.setCreateTime(new Date());
                debt.setModifyTime(new Date());
                // 生成流水编号
                debt.setDebtNumber(serialNumberService.generateSerialNumber("zq"));
                debtMapper.insert(debt);
            }
            return debts;
        }
    }

    @Override
    public int packageDebt(String debtNumber) {
        return debtMapper.packageDebt(debtNumber);
    }

    @Override
    public int returnDebt(String debtID) {
        return debtMapper.returnDebt(debtID);
    }
}
