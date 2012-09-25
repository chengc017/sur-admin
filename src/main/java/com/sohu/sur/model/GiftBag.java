package com.sohu.sur.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;

/**
 * 随即调宝实体
 * @author xuewuhao
 *
 */
@Entity("gift_bag")
public class GiftBag extends ManagedEntity<ObjectId>{

	@Id
	private ObjectId id;
	/**
	 * 用户passport
	 */
	@Indexed(unique = true)
	private String uid;
	/**
	 * 碎片1
	 */
	private boolean fragment1 = false;
	/**
	 * 碎片2
	 */
	private boolean fragment2 = false;
	/**
	 * 碎片3
	 */
	private boolean fragment3 = false;
	/**
	 * 碎片4
	 */
	private boolean fragment4 = false;
	/**
	 * 最后更新时间
	 */
	@Indexed
	private Date cdate;
	/**
	 * 此用户调用接口次数
	 */
	private long num=0;
	/**
	 * 当天调用接口次数
	 */
	private int daynum=0;
	public ObjectId getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public int getDaynum() {
		return daynum;
	}

	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public boolean isFragment1() {
		return fragment1;
	}

	public void setFragment1(boolean fragment1) {
		this.fragment1 = fragment1;
	}

	public boolean isFragment2() {
		return fragment2;
	}

	public void setFragment2(boolean fragment2) {
		this.fragment2 = fragment2;
	}

	public boolean isFragment3() {
		return fragment3;
	}

	public void setFragment3(boolean fragment3) {
		this.fragment3 = fragment3;
	}

	public boolean isFragment4() {
		return fragment4;
	}

	public void setFragment4(boolean fragment4) {
		this.fragment4 = fragment4;
	}

	
}
