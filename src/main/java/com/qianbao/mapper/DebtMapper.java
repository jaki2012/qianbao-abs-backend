package com.qianbao.mapper;

import com.qianbao.domain.Debt;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description
 */
@Repository
@Mapper
public interface DebtMapper {
    /**
     *
     * @param start
     * @param length
     * @return 根据页码及页码尺寸返回若干个债权
     */
    @Select("SELECT * FROM tbDebt WHERE state='待审核' LIMIT #{start}, #{length}")
    List<Debt> findUnreviewdByPage(@Param("start") int start, @Param("length") int length);

    List<Debt> findUnreviewdByPageAndDate(@Param("start") int start, @Param("length") int length,
                                          @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Select("SELECT * FROM tbDebt WHERE state='待审核'")
    List<Debt> findAllUnreviewd();

    List<Debt> findAllUnreviewdByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<Debt> findAllByPage(@Param("start") int start, @Param("length") int length, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<Debt> findAll(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    /**
     * 模拟获取债权操作
     * @param number 每次取的债权个数
     * @return 新增的外部债权列表
     */
    @Select("SELECT * FROM tbDebtOut ORDER BY RAND() LIMIT #{number}")
    List<Debt> acquireDebts(int number);

    @Select("SELECT * FROM tbDebt LIMIT #{start}, #{length}")
    List<Debt> findByPage(@Param("start")int start, @Param("length")int length);

    @Update("UPDATE tbDebt SET state='已打包' WHERE debtNumber=#{debtNumber}")
    int packageDebt(@Param("debtNumber") String debtNumber);

    @Update("UPDATE tbDebt SET state='已退回' WHERE debtNumber=#{debtNumber}")
    int returnDebt(@Param("debtNumber") String debtNumber);

    @Insert("INSERT INTO tbDebt (debtNumber, debtID, platform, loanNumber, loanMoney, loanRate, loanTerm, repaymentWay, loanUse, " +
            "state, comment, createTime, modifyTime)" +
            " values (#{debt.debtNumber}, #{debt.debtID}, #{debt.platform}, #{debt.loanNumber}, #{debt.loanMoney}, #{debt.loanRate}," +
            " #{debt.loanTerm}, #{debt.repaymentWay}, #{debt.loanUse}, #{debt.state}, #{debt.comment}, #{debt.createTime}, #{debt.modifyTime})")
    int insert(@Param("debt") Debt debt);

    @Select("Select a.* From tbDebt a LEFT JOIN tbAsset_Debt b ON a.debtNumber = b.debtNumber Where b.assetID = #{assetID}")
    List<Debt> findByAssetID(@Param("assetID") int assetID);

}
