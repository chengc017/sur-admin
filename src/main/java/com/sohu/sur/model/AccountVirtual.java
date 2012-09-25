package com.sohu.sur.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Version;

/**
 * 用户虚拟物品实体
 * 
 * @author xuewuhao
 * 
 */
@Entity("account_virtual")
public class AccountVirtual extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;
	/**
	 * 记录类型 0购买  1收到  2送出
	 */
	@Indexed
	@Property("act_type")
	private int actType;
	// 插入时间
	@Indexed
	@Property("c_date")
	private Date cdate;
	//商品Id
	@Indexed
	@Property("gift_id")
	private int giftId;
	//商品名称
	@Property("gift_name")
	private String giftName;
	
	//商品单价金币
	private int coin;
	
	
	//商品总金币
	@Property("coin_sum")
	private int coinSum;
	
	//商品所有者
	@Indexed
	private String owner;
	
	//商品logo
	private String logo;
	//商品url
	private String url;
	//商品数量
	private int num;
	
	//备注
	private String desc;

	 @Version("verAccountVirtual")
	 long version;
	
	
	public int getCoinSum() {
		return coinSum;
	}

	public void setCoinSum(int coinSum) {
		this.coinSum = coinSum;
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

	public long getVersion() {
		return version;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVersion(long version) {
		this.version = version;
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

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
