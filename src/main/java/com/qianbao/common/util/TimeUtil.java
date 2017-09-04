package com.qianbao.common.util;

import java.util.Calendar;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description
 */
public class TimeUtil {

    public static String getToday(){
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        // Calendar.MONTH返回0 - 11的值 此处需要注意
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        // 对1-9月份添加0前缀
        month = (month.length() == 1 ? 0 + month : month) ;
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        // 对1-9号添加0前缀
        day = (day.length() == 1 ? 0 + day : day);

        StringBuilder todayStrBuilder = new StringBuilder();
        todayStrBuilder.append(year)
                .append(month)
                .append(day);
        return todayStrBuilder.toString();
    }
}
