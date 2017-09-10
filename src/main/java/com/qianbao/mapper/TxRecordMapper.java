package com.qianbao.mapper;

import com.qianbao.domain.TxRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description
 */
@Repository
@Mapper
public interface TxRecordMapper {

    @Select("Select * from tbTxRecord")
    List<TxRecord> findAll();
}
