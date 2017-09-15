package com.qianbao.mapper;

import com.qianbao.domain.Agreement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author lijiechu
 * @create on 17/9/15
 * @description
 */
@Mapper
@Repository
public interface AgreementMapper {

    @Insert("Insert into tbAgreement(agreementID, agreementType, publisher, bcPosition, createTime, modifyTime) values " +
            "(#{agreementID},#{agreementType}, #{publisher}, #{bcPosition}, #{createTime}, #{modifyTime})")
    int insert(Agreement agreement);

    @Select("Select * from tbAgreement where agreementID = #{agreementID}")
    Agreement findByID(@Param("agreementID") String agreementID);
}
