package org.cncert.entity;

import java.io.Serializable;

/**
 * @Author: sdcert
 * @Date 2020/5/26 下午2:37
 * @Description
 */
public class PingResult implements Serializable {
    private String ip;
    private String net;
    private Integer delay;

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public PingResult() {


    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
