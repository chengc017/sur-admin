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
@Entity("virtual_overview")
public class VirtualOverview extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	//兑换次数、兑换消费金币数、兑换人数、礼物兑换量
	private long exchangeCount=0;
	private long exchangeConsumeCoinNum=0;
	private long exchangeUidNum=0;
	private long exchangeNum=0;
	
	//赠送礼物次数  送礼人数  收礼人数、赠送礼物数
	private long giveGiftCount=0;
	private long giveUidNum=0;
	private long receiveUidNum=0;
	private long giveGiftNum=0;
	
	
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

	@Indexed(unique = true)
	private Date cdate;
	
	

	
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

}
