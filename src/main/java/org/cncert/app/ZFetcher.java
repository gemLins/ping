package org.cncert.app;

import org.apache.commons.lang3.StringUtils;
import org.cncert.entity.Message;
import org.cncert.utils.HttpUtils;
import org.cncert.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZFetcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(ZFetcher.class);
    private String url;
    private Long start_time;

    public ZFetcher(String url, Long start_time) {
        this.url = url;
        this.start_time = start_time;
    }


    public String run() {
        // 获取数据的时间不能超过60s
        String result = "";
        while (gettime() - start_time <= 60) {
            String s = HttpUtils.get(url, HttpUtils.getmd5());
            LOGGER.info("v网返回给z网的数据为: {}", s);
            Message message = JsonUtils.getJsonObject(s,Message.class);
            List<Message.Msg> msg = message.getData().getMsg();
            if (msg.size() == 0) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (int i = 0; i < msg.size(); i++) {
                if (StringUtils.isNotBlank(msg.get(i).getMsgBody())) {
                     result = msg.get(i).getMsgBody();
                    return result;
                }
            }


        }
        return result;
    }

    private static Long gettime() {
        return System.currentTimeMillis() / 1000;
    }
}
