package com.qianbao.service.impl;

import com.qianbao.domain.Debt;
import com.qianbao.mapper.DebtMapper;
import com.qianbao.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Debt> debts = debtMapper.findByPage(start, length);
        return debts;
    }
}
