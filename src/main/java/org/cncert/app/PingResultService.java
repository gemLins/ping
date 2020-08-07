package org.cncert.app;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.cncert.entity.PingResult;
import org.cncert.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author: sdcert
 * @Date 2020/5/26 下午2:46
 * @Description
 */
public class PingResultService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PingResultService.class);

    private  static ExecutorService exs = null;
    private static ThreadFactory nameed = new ThreadFactoryBuilder().setNameFormat("ping-%d").build();


    static {
       Integer cores = Runtime.getRuntime().availableProcessors();
       if(cores > 20){
           exs = new ThreadPoolExecutor(20,cores - 5,10,TimeUnit.MINUTES,new LinkedBlockingQueue<>(1024),nameed,new ThreadPoolExecutor.AbortPolicy());
       }else {
           exs = Executors.newFixedThreadPool(1);
       }
    }

    public synchronized  static List<PingResult> getresult(List<String> ips, String net) throws InterruptedException {

        List<PingResult> list = new ArrayList<>();
        List<PingResult> dataresult =  Lists.newArrayList();
        //创建临时文件夹，存储ping之后的结果
        File tempDir = Files.createTempDir();

        CountDownLatch countDownLatch = new CountDownLatch(ips.size() * 3);
        for (int i = 0; i < 3; i++) {
            for(String ip: ips){
                exs.submit(new CallableTask(ip,tempDir+"/",countDownLatch));
            }
        }

        countDownLatch.await();
        try {
            //读取整个目录
            File file = new File(tempDir+"/");
            File[] files = file.listFiles();
            if(files == null){
                return dataresult;
            }
            for(File f: files){
                List<String> lines = Files.readLines(f, Charsets.UTF_8);
                for(String line: lines){
                    if(StringUtils.isNotBlank(line)){
                        PingResult reponseResult = new PingResult();
                        reponseResult.setIp(line.split(" ")[0]);
                        reponseResult.setNet(net);
                        if(line.contains("100%")){
                            reponseResult.setDelay(-1);
                        }else {
                            Double value =  Double.valueOf(line.substring(line.lastIndexOf("/")+1));
                            reponseResult.setDelay(Integer.valueOf(String.format("%.0f",value)));
                        }
                        list.add(reponseResult);
                    }
                }
            }


            // 统计三次执行的结果，算平均时长
            Map<String, Map<String, Double>> collect = list.stream().collect(Collectors.groupingBy(PingResult::getIp, Collectors.groupingBy(PingResult::getNet, Collectors.averagingInt(PingResult::getDelay))));

            for(Map.Entry<String, Map<String, Double>> map: collect.entrySet()){
                PingResult responseResult  = new PingResult();
                responseResult.setIp(map.getKey());
                for(Map.Entry<String, Double> data: map.getValue().entrySet()){
                    responseResult.setNet(data.getKey());
                    responseResult.setDelay(Integer.valueOf(String.format("%.0f",data.getValue())));
                }
                dataresult.add(responseResult);
            }
            // 读取完成之后，删除目录
            FileUtils.deleteFileOrDirectory(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        list.clear(); // 清除结果
        return dataresult;


    }

    /**
     * 关闭线程
     */
    public static  void close(){
        try {
            if(!exs.awaitTermination(1000, TimeUnit.MILLISECONDS)){
                //规定时间内耗时的task 没有完成
                exs.shutdownNow(); // 强制关闭
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
