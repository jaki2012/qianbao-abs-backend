package com.qianbao.mapper;

import com.qianbao.domain.Debt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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

    @Select("SELECT * FROM tbDebt LIMIT #{start}, #{length}")
    List<Debt> findByPage(@Param("start")int start, @Param("length")int length);

    @Update("UPDATE tbDebt SET state='已打包' WHERE debtID=#{debtID}")
    int packageDebt(@Param("debtID") String debtID);

    @Update("UPDATE tbDebt SET state='已退回' WHERE debtID=#{debtID}")
    int returnDebt(@Param("debtID") String debtID);

}
