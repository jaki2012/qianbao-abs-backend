package com.qianbao.common.util;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 字符串操作辅助类
 */
public class StringUtil {

    public static String fillStringWillZeroes(String sequence){
        StringBuilder formattedSequence = new StringBuilder();

        if(sequence.length() >= 4) {
            return sequence.substring(0, 4);
        } else {
            for(int i =0; i< 4-sequence.length(); i++){
                formattedSequence.append("0");
            }
            formattedSequence.append(sequence);
        }
        return formattedSequence.toString();
    }
}
