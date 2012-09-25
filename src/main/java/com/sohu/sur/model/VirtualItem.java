package com.sohu.sur.model;

import java.util.Date;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * 虚拟商品概况实体
 * 
 * @author xuewuhao
 * 
 */
@Entity("virtual_item")
public class VirtualItem extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	
	@Indexed
	private int giftId;
	
	private String giftName;
	
	//礼物对换次数、礼物兑换量、兑换消费金币数、兑换人数
	private long exchangeCount=0;
	private long exchangeNum=0;
	private long exchangeConsumeCoinNum=0;
	private long exchangeUidNum=0;
	
	//赠送礼物次数 赠送礼物数量  送礼人数  收礼人数
	private long giveGiftCount=0;
	private long giveGiftNum=0;
	private long giveUidNum=0;
	private long receiveUidNum=0;
	//统计日期
	@Indexed
	private Date cdate;
    //入库时间
	@Indexed
	private Date indate;
	
	
	

	public long getExchangeCount() {
		return exchangeCount;
	}

	public void setExchangeCount(long exchangeCount) {
		this.exchangeCount = exchangeCount;
	}

	public long getGiveGiftCount() {
		return giveGiftCount;
	}

	public void setGiveGiftCount(long giveGiftCount) {
		this.giveGiftCount = giveGiftCount;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public long getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(long exchangeNum) {
		this.exchangeNum = exchangeNum;
	}

	public long getExchangeConsumeCoinNum() {
		return exchangeConsumeCoinNum;
	}

	public void setExchangeConsumeCoinNum(long exchangeConsumeCoinNum) {
		this.exchangeConsumeCoinNum = exchangeConsumeCoinNum;
	}

	public long getExchangeUidNum() {
		return exchangeUidNum;
	}

	public void setExchangeUidNum(long exchangeUidNum) {
		this.exchangeUidNum = exchangeUidNum;
	}

	public long getGiveGiftNum() {
		return giveGiftNum;
	}

	public void setGiveGiftNum(long giveGiftNum) {
		this.giveGiftNum = giveGiftNum;
	}

	public long getGiveUidNum() {
		return giveUidNum;
	}

	public void setGiveUidNum(long giveUidNum) {
		this.giveUidNum = giveUidNum;
	}

	public long getReceiveUidNum() {
		return receiveUidNum;
	}

	public void setReceiveUidNum(long receiveUidNum) {
		this.receiveUidNum = receiveUidNum;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

}
