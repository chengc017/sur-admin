package com.sohu.sur.util.po;

import java.util.List;

public class UserMedalRet {
	/**
	 * 0成功 1失败
	 */
	private String code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 返回结果
	 */
	private List<UserMedal> listUserMedal;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<UserMedal> getListUserMedal() {
		return listUserMedal;
	}
	public void setListUserMedal(List<UserMedal> listUserMedal) {
		this.listUserMedal = listUserMedal;
	}

}
