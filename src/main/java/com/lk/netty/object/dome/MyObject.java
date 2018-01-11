package com.lk.netty.object.dome;

import java.io.Serializable;

/**
 * 用来模拟传输的载体，例子中的信息只有id,message
 * @author lkj41110
 * @version time：2017年1月16日 下午10:07:30
 */
public class MyObject implements Serializable {
	//一定要序列化！！！
	private static final long serialVersionUID = 1L;
	private int id;
	private String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MyObject [id=" + id + ", message=" + message + "]";
	}
	
}
