package com.sohu.sur.model;

import java.util.Date;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
/**
 * 金币动作概况
 * @author xuewuhao
 *
 */
@Entity("system_overview_action")
public class SystemOverviewAction extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	@Indexed
	private String actionName;
	private long count;
	private long userCount;
	private double averageUserCoin;
	private long bonusTotal;
	@Indexed
	private Date cdate;
	private Date indate;
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getUserCount() {
		return userCount;
	}
	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}
	public double getAverageUserCoin() {
		return averageUserCoin;
	}
	public void setAverageUserCoin(double averageUserCoin) {
		this.averageUserCoin = averageUserCoin;
	}
	public long getBonusTotal() {
		return bonusTotal;
	}
	public void setBonusTotal(long bonusTotal) {
		this.bonusTotal = bonusTotal;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	
}
