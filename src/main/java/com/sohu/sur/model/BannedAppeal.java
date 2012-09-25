package com.sohu.sur.model;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

/**
 * 黑名单申诉表
 * 
 * @author leiyangbj6779
 * 
 */
@Entity("banned_appeal")
public class BannedAppeal extends ManagedEntity<ObjectId>{
	@Id
	private ObjectId id;

	/** 用户UID */
	@Indexed(unique = true)
	private String uid;

	/** 申诉内容 */
	@Property("appeal_content")
	private String appealContent;

	/** 申诉日期 */
	@Indexed
	@Property("appeal_date")
	private Date appealDate;

	/** 是否驳回 */
	@Indexed
	@Property("is_overrule")
	private boolean isOverrule;

	/** 处理本条记录的时间 */
	@Property("deal_date")
	private Date dealDate;
	
	/** 管理员备注*/
	@Property("remark")
	private String remark;

	public ObjectId getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public String getAppealContent() {
		return appealContent;
	}

	public Date getAppealDate() {
		return appealDate;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}

	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public boolean isOverrule() {
		return isOverrule;
	}

	public void setOverrule(boolean isOverrule) {
		this.isOverrule = isOverrule;
	}

	public static BannedAppeal createInstance(String uid,String appealMessage){
		BannedAppeal ba = new BannedAppeal();
		ba.uid = uid;
		ba.appealContent = appealMessage;
		ba.appealDate = new Date();
		ba.isOverrule = false;
		return ba;
	}

}
