package org.cncert.app;

import org.cncert.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


/**
 * @Author: sdcert
 * @Date 2020/5/26 下午2:48
 * @Description
 */
public class CallableTask implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(CallableTask.class);


    private String ip;
    private String path;
    private CountDownLatch countDownLatch;
    private static String scriptPath;
    public CallableTask(String ip, String path, CountDownLatch countDownLatch) {
        this.ip = ip;
        this.path = path;
        this.countDownLatch = countDownLatch;
        scriptPath = Main.getPath();
    }


    @Override
    public void run() {
        String uuid = UUID.randomUUID().toString();

        String cmd = "sh "+scriptPath+"/ip.sh "+ip + " " + path+uuid;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor(); //等待脚本执行完
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }
}
