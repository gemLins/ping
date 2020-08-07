package org.cncert.app;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.cncert.entity.Message;
import org.cncert.utils.HttpUtils;
import org.cncert.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VFetcher implements Runnable {

    private final static String DD_URL = "http://dd2.aiops.pub:8080/dd/msg";
    private final static Logger LOGGER = LoggerFactory.getLogger(VFetcher.class);
    private final static Gson GSON = new Gson();

    @Override
    public void run() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("启动fetcher线程");
        while (true) {
            String s = HttpUtils.get(DD_URL, HttpUtils.getmd5());
            if (StringUtils.isNotBlank(s)) {
                Message message = JsonUtils.getJsonObject(s,Message.class);
                List<Message.Msg> msg = message.getData().getMsg();
                if (msg.size() == 0) {
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                String msgBody = message.getData().getMsg().get(0).getMsgBody();// ping地址
                LOGGER.info("获取ping数据的地址: {}",msgBody);
                String s1 = HttpUtils.get(msgBody, "");// ping不需要token
                // 将ping结果返回给单刀
                Map<String,String> map = new HashMap<>();
                LOGGER.info("v网返回给z网数据: {}", s1);
                map.put("msgBody",s1);
                HttpUtils.postJsonParams(DD_URL, GSON.toJson(map), HttpUtils.getmd5());
            }
        }

    }
}
