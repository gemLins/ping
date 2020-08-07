package org.cncert.resource;

import com.google.common.base.Joiner;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.cncert.app.ZFetcher;
import org.cncert.entity.ResponseResult;
import org.cncert.app.PingResultService;
import org.cncert.entity.PingResult;
import org.cncert.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: sdcert
 * @Date 2020/5/26 下午2:19
 * @Description
 */

@Path("/ping")
public class PingResource {


    private  static Gson gson = new Gson();
    private final static Logger LOGGER = LoggerFactory.getLogger(PingResource.class);
    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseResult ping(@QueryParam("ip") String ip, @QueryParam("net") String net, @QueryParam("offset") Integer offset)
            throws InterruptedException {
        LOGGER.info("offset: {}",offset);
        if(offset == null ){
            offset = 1;
        }
        if(StringUtils.isBlank(ip)){
            return new ResponseResult(400,"ip为空");
        }

        if(net == null){
            net = "z";
        }

        // 这里暂时不验证ip的正确性
        List<String> ips = Arrays.asList(ip.split(","));
        LOGGER.info("查询的ip个数为: {}",ips.size());
         if(net.equals("v")){
            String dd_url = "http://dd2.aiops.pub:8080/dd/msg";// 单刀地址
            Map<String,String> map = new HashMap<>();
            String msgbody = "http://ping.aiops.pub:8081/ping/ping?ip="+ Joiner.on(",").join(ips)+"&net=v";
            LOGGER.info("发送给v网的: {}",msgbody);
            map.put("msgBody",msgbody);
            HttpUtils.postJsonParams(dd_url,gson.toJson(map),HttpUtils.getmd5()); //发送到单刀
            ZFetcher fetcher = new ZFetcher(dd_url,System.currentTimeMillis() / 1000);
            String run = fetcher.run();
            if(StringUtils.isBlank(run)){
                    return  new ResponseResult(0,offset);
            }else {
            	JSONObject json_test = JSONObject.fromObject(run);  
            	Object obj=json_test.get("hosts");
                return  new ResponseResult(0,offset,obj);
            }
        } 
        List<PingResult> result = PingResultService.getresult(ips, net);
        result = result.stream().sorted(Comparator.comparing(PingResult::getIp)).collect(Collectors.toList());
        LOGGER.info("结果为: {}", result.size());
        if (result.size() == 0) {
            return new ResponseResult(0, offset);
        }
        int start = offset;
        if (offset > result.size()) {
            return new ResponseResult(400, "offset设置太大，超过了结果集的大小", offset);
        }

        return new ResponseResult(0, offset, result.subList(start - 1, result.size()));

    }

}
