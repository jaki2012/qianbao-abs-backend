package com.qianbao.common.util;

import com.qianbao.common.sys.Constants;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 字符串操作辅助类
 */
public class StringUtil {

    /**
     * 根据业务情况对当天序列号进行自增
     * @param sequence
     * @return
     */
    public static String fillStringWillZeroes(String sequence){
        StringBuilder formattedSequence = new StringBuilder();

        if(sequence.length() >= Constants.SEQUENCE_LENGTH) {
            return sequence.substring(0, Constants.SEQUENCE_LENGTH);
        } else {
            for(int i = 0; i< Constants.SEQUENCE_LENGTH - sequence.length(); i++){
                formattedSequence.append("0");
            }
            formattedSequence.append(sequence);
        }
        return formattedSequence.toString();
    }
}
