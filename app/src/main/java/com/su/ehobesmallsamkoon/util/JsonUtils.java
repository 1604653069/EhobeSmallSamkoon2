package com.su.ehobesmallsamkoon.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class JsonUtils {

    private static Gson mGson = new Gson();

    /**
     * 将json字符串转化成实体对象
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static Object stringToObject(String json, Class classOfT) {
        return mGson.fromJson(json, classOfT);
    }
    public static Object objectToClass(Object object, Class classOfT) {
        return mGson.fromJson(mGson.toJson(object),classOfT);
    }
    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }

    /**
     * 把json 字符串转化成list
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }
    public static String getValueByKey(Object json ,String key){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(mGson.toJson(json));
        JsonObject root = element.getAsJsonObject();
        String resultCode = root.get(key).toString();
        return  resultCode;
    }
    public static <T> List<T>  getListByKey(Object json ,String key,Class<T> cls){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(mGson.toJson(json));
        JsonObject root = element.getAsJsonObject();
        String resultCode = root.get(key).toString();

        return  stringToList(resultCode,cls);
    }



}
