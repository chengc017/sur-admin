package com.sohu.sur.util.po;

import java.io.Serializable;
import java.util.List;

import com.sohu.sur.model.Medal;

/**
 * 用户以及该用户已有勋章
 * @author xuewuhao
 *
 */
public class UserMedal implements Serializable{

	private String uid;
	private List<Medal> listMedal;
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<Medal> getListMedal() {
		return listMedal;
	}

	public void setListMedal(List<Medal> listMedal) {
		this.listMedal = listMedal;
	}

	
}
