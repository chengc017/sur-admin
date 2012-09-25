package com.sohu.sur.model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;

@Entity("mall_logs")
public class MallLog implements Serializable{
	@Id
	private ObjectId id;

	@Property("orig")
	private int origId;

	@Indexed
	@Property("uid")
	private String userId;

	@Property
	private int type;
	/**
	 * 0实物 1优惠劵 2虚拟
	 */
	@Property
	@Indexed(IndexDirection.DESC)
	private int giftType;
	
	@Indexed(IndexDirection.DESC)
	@Property("ctime")
	private Date createTime;

	@Property
	private int count;

	@Property
	private String logo;

	@Property("bonus")
	private int saleValue;

	@Property("gid")
	private int giftId;

	@Property("did")
	private String dealId;

	@Property("gname")
	private String giftName;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}


	public int getOrigId() {
		return origId;
	}

	public void setOrigId(int origId) {
		this.origId = origId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(int saleValue) {
		this.saleValue = saleValue;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public int getGiftType() {
		return giftType;
	}

	public void setGiftType(int giftType) {
		this.giftType = giftType;
	}

}
