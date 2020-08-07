package org.cncert.utils;

import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.cncert.entity.Token;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils {

    private static Map<String,String> map = Maps.newConcurrentMap();
    public static  OkHttpClient okHttpClient(){
        return  new OkHttpClient.Builder().retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    private static  ConnectionPool pool(){
        return  new ConnectionPool(5,1, TimeUnit.SECONDS);
    }




    public static String postJsonParams(String url,String json,String token){
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request build = null;
        if(StringUtils.isNotBlank(token)){
            build = new Request.Builder().url(url).addHeader("authorization",token).post(requestBody).build();
        }else {
            build = new Request.Builder().url(url).put(requestBody).build();
        }
        Response response = null;
        try {
            response = okHttpClient().newCall(build).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }
        }catch (Exception e) {
            System.out.println(e);
        }finally {
            if(response != null){
                response.close();
            }
        }
        return responseBody;
    }

    public static String get(String url,String token){
        String responseBody = "";
        Request request = null;
        if(StringUtils.isBlank(token)){
            request =  new Request.Builder().url(url).get().build();
        }else {
            request = new Request.Builder().url(url).addHeader("authorization",token).get().build();
        }
        Response response  = null;
        try {
             response = okHttpClient().newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null){
                response.close();
            }
        }
        return responseBody;
    }


    public static  String getmd5(){
//        String password = "3Rxbyws!@#";
//        String salt = "aiops.cncert.org-1234567890abcdefghijklmnopqrstuvwxyz";
//        password = EncryptUtil.md5(password + salt + "ping").toLowerCase();

        if(map.containsKey("ping")){
            if(map.get("ping") != null){
                return map.get("ping");
            }
        }
        String url = "http://auth.aiops.pub:8080/auth/login";
//
        String json = "{\n" +
                "   \"username\":\"ping\",\n" +
                "   \"password\":\"abb8f4c0331e40b5695b830139408c9e\"\n" +
                "}";

        String s = postJsonParams(url, json, "");
        Token token = JsonUtils.getJsonObject(s,Token.class);
        String tk = token.getData().getToken();
        map.put("ping",tk);
        return  tk;


    }





}
