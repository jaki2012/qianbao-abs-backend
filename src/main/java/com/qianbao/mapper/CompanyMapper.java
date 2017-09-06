package com.qianbao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/6
 * @description
 */
@Mapper
@Repository
public interface CompanyMapper {

    @Select("Select companyName from tbCompany where companyType = #{companyType}")
    List<String> findByType(@Param("companyType") String companyType);
}
