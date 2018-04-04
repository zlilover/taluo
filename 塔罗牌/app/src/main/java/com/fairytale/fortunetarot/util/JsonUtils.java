package com.fairytale.fortunetarot.util;

import com.google.gson.Gson;

/**
 * Created by zli on 15/12/14.
 *
 *
 */
public class JsonUtils {

    public static <T> String entityToJsonString(T obj) {
        try {
            if (obj==null){
                return null;
            }
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception e) {
        }
        return null;

    }

    public static <T> T jsonStringToEntity(String res, Class<T> c) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(res, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonStringToEntity(String res, java.lang.reflect.Type typeOfT) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(res, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
