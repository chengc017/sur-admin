package com.sohu.sur.model;

import java.util.Date;

import javax.persistence.Transient;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

/**
 * 用户虚拟物品实体赠送历史
 * 
 * @author xuewuhao
 * 
 */
@Entity("account_virtual_log")
public class AccountVirtualLog extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;
	
	
	/**
	 * 记录类型  对人赠送0
	 */
	@Indexed
	@Property("record_type")
	private int recordType=0;
	
	
	/**
	 * 赠送类型  1收到  2送出
	 */
	@Indexed
	@Property("act_type")
	private int actType;
	
	
	// 插入时间
	@Indexed
	@Property("c_date")
	private Date cdate;
	//商品Id
	@Property("gift_id")
	private int giftId;
	//商品名称
	@Property("gift_name")
	private String giftName;
	
	//商品单价金币
	private int coin;
	//商品url
	private String url;
	//商品logo
	private String logo;
	//接受者
	@Indexed
	@Property("to_user")
	private String toUser;
	
	//赠送者
	@Indexed
	@Property("from_user")
	private String fromUser;
	
	//留言
	private String info;
	
	//备注
	private String desc;

	//辅助显示
	private String fromUserNickName;
	private String toUserNickName;
	private String logId;
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getActType() {
		return actType;
	}


	public void setActType(int actType) {
		this.actType = actType;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 初始化虚拟商品历史记录
	 * @param actType  1接收 2送出
	 * @param info  留言
	 * @param fromUser  赠送者
	 * @param av   被赠送者对象
	 * @return
	 */
	public static AccountVirtualLog createLog(int actType,String info,String fromUser,AccountVirtual av){
		AccountVirtualLog avl = new AccountVirtualLog();
		avl.setActType(actType);
		avl.setCdate(new Date());
		avl.setCoin(av.getCoin());
		avl.setFromUser(fromUser);
		avl.setGiftId(av.getGiftId());
		avl.setGiftName(av.getGiftName());
		avl.setToUser(av.getOwner());
		avl.setLogo(av.getLogo());
		avl.setInfo(info);
		avl.setUrl("http://gift.sohu.com/gift/details/"+av.getGiftId());
		return avl;
	}
	@Transient
	public String getFromUserNickName() {
		return fromUserNickName;
	}

	public void setFromUserNickName(String fromUserNickName) {
		this.fromUserNickName = fromUserNickName;
	}
	@Transient
	public String getToUserNickName() {
		return toUserNickName;
	}

	public void setToUserNickName(String toUserNickName) {
		this.toUserNickName = toUserNickName;
	}
	@Transient
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	
	
}
