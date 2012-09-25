package com.sohu.sur.util.po;

/**
 * 调宝接口返回封装
 * @author xuewuhao
 *
 */
public class GiftBagResult {
    /**
     * 接口调用结果  0成功  1失败 2达到每天上线（100）
     */
	private  String  status;
	 /**
     * 描述（成功或者失败原因）
     */
	private  String  msg;
	 /**
     * 中奖结果 0中奖 1未中 2已中奖
     */
	private  String  result;
	 /**
     * 获得碎片 0(未获得碎片)、1、2、3、4
		0几率=50% 
		1+2+3+4几率=50% 

     */
	private  String  fragment;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFragment() {
		return fragment;
	}
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
	
	
	
	
}
