package org.cncert.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {
    private  Integer code;
    private  String msg;
    private Data data;

    public Message(Integer code, String msg, Data data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data{
        @SerializedName("recvMsgs")
        private List<Msg> msg;

        public List<Msg> getMsg() {
            return msg;
        }

        public void setMsg(List<Msg> msg) {
            this.msg = msg;
        }

        public Data(List<Msg> msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "msg=" + msg +
                    '}';
        }
    }


    public static class Msg {
        private String msgId;
        private String msgBody;

        public Msg(String msgId, String msgBody) {
            this.msgId = msgId;
            this.msgBody = msgBody;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getMsgBody() {
            return msgBody;
        }

        public void setMsgBody(String msgBody) {
            this.msgBody = msgBody;
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "msgId='" + msgId + '\'' +
                    ", msgBody='" + msgBody + '\'' +
                    '}';
        }
    }
}




