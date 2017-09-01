package com.qianbao.service;

import com.qianbao.domain.Debt;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
public interface DebtService {
    /**
     * 获取债权
     * @param page 第几页
     * @param length 页面长度
     * @return
     */
    List<Debt> acquireDebts(int page, int length);
}
