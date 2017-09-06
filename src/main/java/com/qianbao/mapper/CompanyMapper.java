package com.qianbao.mapper;

import com.qianbao.domain.Company;
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

    int insert(Company company);

    @Select("Select * from tbCompany where companyID = #{companyID}")
    Company findByCompanyID(@Param("companyID")int CompanyID);

    @Select("Select * from tbCompany where companyName = #{companyName}")
    Company findByCompanyName(@Param("companyName") String companyName);
}
