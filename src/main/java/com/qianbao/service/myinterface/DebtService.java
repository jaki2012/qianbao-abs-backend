package com.qianbao.service.business.myinterface;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.domain.Debt;

import java.util.Date;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
public interface DebtService {

    /**
     * 从债权池中获取-待审核-的债权
     * @param page
     * @param length
     * @return
     */
    JSONObject getUnreviewdDebts(int page, int length);

    /**
     * 从债券池中获取-待审核-的债权（带日期）
     * @param page
     * @param length
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject getUnreviewdDebts(int page, int length, Date startDate, Date endDate);

    /**
     * 从债权池中获取所有债权
     * @param page
     * @param length
     * @return
     */
    JSONObject getAllDebts(int page, int length, Date startDate, Date endDate);


    /**
     * 从外部获取债权
     * @param number 要获取多少个
     * @return
     */
    List<Debt> acquireDebts(int number);

    /**
     * 打包债权并将其状态变更为已打包
     * @param debtNumber
     * @return
     */
    int packageDebt(String debtNumber);

    /**
     * 退回债权并将其状态变更为已退回
     * @param debtNumber
     * @return
     */
    int returnDebt(String debtNumber);
}
