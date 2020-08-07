package org.cncert.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Token {
    private Integer code;
    private String msg;
    @SerializedName("data")
    private DataToken data;

    @Override
    public String toString() {
        return "Token{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Token(Integer code, String msg, DataToken data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DataToken getData() {
        return data;
    }

    public static  class DataToken {

        @SerializedName("expired")
        private String expired;

        @SerializedName("token")
        private String token;

        public String getExpired() {
            return expired;
        }

        public String getToken() {
            return token;
        }

        @Override
        public String toString() {
            return "DataToken{" +
                    "expired='" + expired + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }


    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = "{\"code\":0,\"msg\":\"操作成功\",\"data\":{\"expired\":\"2020-07-31 04:31:43\",\"token\":\"ad668ff95eb24cf390ec11c0ed66a476\"}}";
        Token token = gson.fromJson(json, Token.class);
        System.out.println(token);
    }
}
