package com.qianbao.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author lijiechu
 * @create on 17/9/7
 * @description 为数组配置键值对的快捷方式
 */
public class JsonUtil {

    public static <T> JSONArray addKeyForList(List<T> list, String keyName){
        JSONArray result = new JSONArray();
        for(T item : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(keyName, item);
            result.add(jsonObject);
        }
        return result;
    }
}
