package cn.sibat.boot.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

public class FastJsonUtil {

    public static JSONObject getResponse(int status, String msg, String seq) {
        JSONObject obj = new JSONObject();
        obj.put("status", status);
        obj.put("seq", seq);
        obj.put("msg", msg);
        obj.put("time", DateUtil.parseDateToString(new Date(), DateUtil.PATTERN_yyyy_MM_dd_HHmmss));
        return obj;
    }

    public static String error(String msg) {
        return getResponse(500, msg, "").toString();
    }

    public static String notData(String msg) {
        return getResponse(404, msg, "").toString();
    }

    public static String ok() {
        return getResponse(200, "ok", "").toString();
    }

    /**
     * form the params to json ,{"data" : data,"status" ： status,"msg" : msg,"seq" : seq,"time": time}
     *
     * @param data
     * @param status
     * @param msg
     * @param seq
     * @return
     */
    public static String getDataResponse(Object data, int status, String msg, String seq) {
        JSONObject obj = getResponse(status, msg, seq);
        if (data instanceof List) {
            obj.put("data", JSONArray.toJSON(data));
        } else if (data instanceof JSONObject || data instanceof JSONArray) {
            obj.put("data", data);
        } else {
            if (data == null) {
                obj.put("data", "");
            } else {
                obj.put("data", JSONObject.toJSON(data));
            }
        }
        return obj.toString();
    }

    public static String getOkDataResponse(Object data) {
        return getDataResponse(data,200,"ok","");
    }

    /**
     * 将对象转换为json数据
     *
     * @param data 对象数据
     * @return
     */
    public static String toJson(Object data) {
        if (data instanceof List) {
            return JSONArray.toJSON(data).toString();
        } else if (data instanceof JSONObject || data instanceof JSONArray) {
            return data.toString();
        } else {
            if (data == null) {
                return "";
            } else {
                return JSONObject.toJSON(data).toString();
            }
        }
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            // JSON串转用户对象列表
            T t = JSON.parseObject(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            // JSON串转用户对象列表
            List<T> t = JSON.parseArray(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
