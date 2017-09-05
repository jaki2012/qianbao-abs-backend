package com.qianbao.service.business.impl;

import com.qianbao.domain.Debt;
import com.qianbao.mapper.DebtMapper;
import com.qianbao.service.business.myinterface.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Debt> acquireDebts(int page, int length) {
        int start = (page-1) * length;
        List<Debt> debts = debtMapper.findUnreviewdByPage(start, length);
        for(Debt debt: debts) {
            // 设置最新时间 模拟获取债权业务
            debt.setCreateTime(new Date());
            debt.setModifyTime(new Date());
        }
        return debts;
    }

    @Override
    public int packageDebt(String debtID) {
        return debtMapper.packageDebt(debtID);
    }

    @Override
    public int returnDebt(String debtID) {
        return debtMapper.returnDebt(debtID);
    }
}
