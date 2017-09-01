package com.qianbao.mapper;

import com.qianbao.domain.Debt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT * FROM tbDebt LIMIT #{start}, #{length}")
    List<Debt> findByPage(@Param("start") int start, @Param("length") int length);
}
