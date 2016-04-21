package com.example.horacechan.parking.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class JSONUtils {

    public static String toString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> toList(String json, Type type) {
        Gson gson = new Gson();
        return (ArrayList<T>) gson.fromJson(json, type);
    }
}
