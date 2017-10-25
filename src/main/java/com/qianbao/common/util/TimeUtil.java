package com.qianbao.common.util;

import java.util.Calendar;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description 日期、时间编码等相关工具类
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

        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        // 对1-9点添加0前缀
        hour = (hour.length() == 1 ? 0 + hour : hour);

        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        // 对1-9分钟添加0前缀
        minute = (minute.length() == 1 ? 0 + minute : minute);

        String second = String.valueOf(calendar.get(Calendar.SECOND));
        // 对1-9秒添加0前缀
        second = (second.length() == 1 ? 0 + second : second);

        StringBuilder todayStrBuilder = new StringBuilder();
        todayStrBuilder.append(year)
                .append(month)
                .append(day)
                .append(hour)
                .append(minute)
                .append(second);
        return todayStrBuilder.toString();
    }
}
