package com.qianbao.mapper;

import com.qianbao.domain.RateSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@Mapper
@Repository
public interface RateSettingMapper {

    @Select("Select * from tbRateSetting LIMIT 1")
    RateSetting findOne();

    @Update("Update tbRateSetting a set a.lawyerFee = #{lawyerFee}, a.accountantFee = #{accountantFee}, " +
            "a.spvRate = #{spvRate}, a.ratingRate = #{ratingRate} WHERE a.id = #{id}")
    int update(RateSetting rateSetting);
}
