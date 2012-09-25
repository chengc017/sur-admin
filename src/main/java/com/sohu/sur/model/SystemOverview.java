package com.sohu.sur.model;

import java.util.Date;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * 系统概况
 * 
 * @author xuewuhao
 * 
 */
@Entity("system_overview")
public class SystemOverview extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	private long countIn;
	private long userCountIn;
	private long bonusTotalIn;
	private long countOut;
	private long userCountOut;
	private long bonusTotalOut;
	private long surplus;

	private long countConsumeOut;
	private long userCountConsumeOut;
	private long bonusConsumeTotalOut;
	
	private long countPunishOut;
	private long userCountPunishOut;
	private long bonusPunishTotalOut;
	
	@Indexed
	private Date cdate;

	public long getSurplus() {
		return surplus;
	}

	public void setSurplus(long surplus) {
		this.surplus = surplus;
	}

	public long getCountConsumeOut() {
		return countConsumeOut;
	}

	public void setCountConsumeOut(long countConsumeOut) {
		this.countConsumeOut = countConsumeOut;
	}

	public long getUserCountConsumeOut() {
		return userCountConsumeOut;
	}

	public void setUserCountConsumeOut(long userCountConsumeOut) {
		this.userCountConsumeOut = userCountConsumeOut;
	}

	public long getBonusConsumeTotalOut() {
		return bonusConsumeTotalOut;
	}

	public void setBonusConsumeTotalOut(long bonusConsumeTotalOut) {
		this.bonusConsumeTotalOut = bonusConsumeTotalOut;
	}

	public long getCountPunishOut() {
		return countPunishOut;
	}

	public void setCountPunishOut(long countPunishOut) {
		this.countPunishOut = countPunishOut;
	}

	public long getUserCountPunishOut() {
		return userCountPunishOut;
	}

	public void setUserCountPunishOut(long userCountPunishOut) {
		this.userCountPunishOut = userCountPunishOut;
	}

	public long getBonusPunishTotalOut() {
		return bonusPunishTotalOut;
	}

	public void setBonusPunishTotalOut(long bonusPunishTotalOut) {
		this.bonusPunishTotalOut = bonusPunishTotalOut;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public long getCountIn() {
		return countIn;
	}

	public void setCountIn(long countIn) {
		this.countIn = countIn;
	}

	public long getUserCountIn() {
		return userCountIn;
	}

	public void setUserCountIn(long userCountIn) {
		this.userCountIn = userCountIn;
	}

	public long getBonusTotalIn() {
		return bonusTotalIn;
	}

	public void setBonusTotalIn(long bonusTotalIn) {
		this.bonusTotalIn = bonusTotalIn;
	}

	public long getCountOut() {
		return countOut;
	}

	public void setCountOut(long countOut) {
		this.countOut = countOut;
	}

	public long getUserCountOut() {
		return userCountOut;
	}

	public void setUserCountOut(long userCountOut) {
		this.userCountOut = userCountOut;
	}

	public long getBonusTotalOut() {
		return bonusTotalOut;
	}

	public void setBonusTotalOut(long bonusTotalOut) {
		this.bonusTotalOut = bonusTotalOut;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

}
