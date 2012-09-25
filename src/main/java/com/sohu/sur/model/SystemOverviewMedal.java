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
@Entity("system_overview_medal")
public class SystemOverviewMedal extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	private long countTotal;
	private long count;
	private long tong;
	private long yin;
	private long jin;
	private long zuan;
	private String name;
	@Indexed
	private Date cdate;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public long getCountTotal() {
		return countTotal;
	}
	public void setCountTotal(long countTotal) {
		this.countTotal = countTotal;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getTong() {
		return tong;
	}
	public void setTong(long tong) {
		this.tong = tong;
	}
	public long getYin() {
		return yin;
	}
	public void setYin(long yin) {
		this.yin = yin;
	}
	public long getJin() {
		return jin;
	}
	public void setJin(long jin) {
		this.jin = jin;
	}
	public long getZuan() {
		return zuan;
	}
	public void setZuan(long zuan) {
		this.zuan = zuan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	
}
