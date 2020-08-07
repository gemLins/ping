package org.cncert.utils;

import com.google.gson.Gson;
import org.cncert.entity.Message;
import org.cncert.entity.Token;

public class JsonUtils{
    private static Gson gson = new Gson();




    public static <T>T getJsonObject(String json,Class<T> clazz) {
        return gson.fromJson(json,clazz);

    }
}
