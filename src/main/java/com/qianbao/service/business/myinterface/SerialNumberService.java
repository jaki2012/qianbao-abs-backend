package com.qianbao.service.business.myinterface;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 生成流水号的服务
 */
public interface SerialNumberService {

    /**
     * 序列号自增
     */
    String SERIAL_NUMBER = "serial.number";

    /**
     * @param bizCode 业务码
     * @return 固定格式的序列号
     */
    String generateSerialNumber(String bizCode);

    //对bizCode做白名单验证,以免恶意伪造
    default boolean isLegal(String bizCode) {
        if (bizCode == null || bizCode.length() != 2) {
            throw new RuntimeException("bizCode: " + bizCode + "异常");
        }
        if (Character.isDigit(bizCode.charAt(0))
                && Character.isDigit(bizCode.charAt(1)))
            return true;
        return false;
    }
}
