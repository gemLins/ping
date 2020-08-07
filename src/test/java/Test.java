import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.cncert.entity.Message;
import org.cncert.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

//    public static void main(String[] args) {
//        String dd_url = "http://dd2.aiops.pub:8080/dd/msg";
//        Map<String,String> map = new HashMap<>();
//        List<String> objects = Lists.newArrayList();
//        objects.add("10.245.86.62");
//        map.put("msgBody","http://ping.aipos.pub:8080/ping/ping?ip="+ Joiner.on(",").join(objects));
//        Gson gson = new Gson();
//        HttpUtils.postJsonParams(dd_url,gson.toJson(map));
//    }

//    public static void main(String[] args) {
//        String json ="{\n" +
//                "    \"code\": 0," +
//                "    \"msg\": \"操作成功\",\n" +
//                "    \"data\": {\n" +
//                "        \"recvMsgs\": [ \n" +
//                "               {\n" +
//                "                    \"msgId\": \"U01-20200620010010103011\",\n" +
//                "                    \"msgBody\": \"我是消息，最大不超过10MB\"\n" +
//                "               },\n" +
//                "               {\n" +
//                "                    \"msgId\": \"U01-20200620010010103012\",\n" +
//                "                    \"msgBody\": \"我是消息2，最大不超过10MB\"\n" +
//                "               }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "}";
//        String json1 ="{\n" +
//                "    \"code\": 0," +
//                "    \"msg\": \"操作成功\",\n" +
//                "    \"data\": {\n" +
//                "        \"recvMsgs\": [ \n" +
//                "        ]\n" +
//                "    }\n" +
//                "}";
//        Gson gson = new Gson();
//        Message message = gson.fromJson(json1, Message.class);
//        System.out.println(message.getData().getMsg().size());
//        if(message != null){
//
////        System.out.println(message.getList().toString());
//        }
//    }

    public static void main(String[] args) {
//        Map<String,String> map = new HashMap<>();
//
//        map.put("msgBody","msgBody");
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(map));

        String json = "{\\\"code\\\":0,\\\"msg\\\":\\\"\\u6210\\u529f\\\",\\\"offset\\\":1,\\\"data\\\":[{\\\"ip\\\":\\\"10.202.16.4\\\",\\\"net\\\":\\\"v\\\",\\\"delay\\\":0}]}";
        System.out.println(json.replace("\\",""));
    }
}
