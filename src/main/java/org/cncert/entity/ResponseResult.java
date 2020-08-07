package org.cncert.entity;

import java.io.Serializable;

/**
 * @Author: sdcert
 * @Date 2020/5/26 上午5:50
 * @Description
 */
public class ResponseResult<T>  implements Serializable {
	
	

    public ResponseResult() {
    }



    public ResponseResult(int code, Integer offset, T data) {
        this.setCode(code);
        this.setMsg("成功");
        this.setOffset(offset);
        this.setHosts(data);
    }
    public ResponseResult(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
        this.setOffset(1);

    }

    public ResponseResult(int code, Integer offset) {
        this.setCode(code);
        this.setMsg("成功");
        this.setOffset(offset);
    }
    public ResponseResult(int code, String msg, Integer offset) {
        this.setCode(code);
        this.setMsg(msg);
        this.setOffset(offset);
    }

    /**
     * 状态码
     * 成功：200
     * 失败：其他
     */
    private int code;

    /**
     * 失败状态码描述
     * 如果成功不返回
     * 失败返回状态码对应的msg消息
     */
    private String msg;

    private Integer offset;




    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * 请求数据的结果
     */
    private T hosts;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



	public T getHosts() {
		return hosts;
	}



	public void setHosts(T hosts) {
		this.hosts = hosts;
	}



	@Override
	public String toString() {
		return "ResponseResult [code=" + code + ", msg=" + msg + ", offset=" + offset + ", hosts=" + hosts + "]";
	}

//    public T getData() {
//        return hosts;
//    }
//
//    public void setData(T data) {
//        this.hosts = data;
//    }
	
}
