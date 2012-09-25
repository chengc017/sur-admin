package com.sohu.sur.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * User: guoyong Date: 11-4-25 下午4:00
 */
@Entity("activity_change_log")
public class ActivityChangeLog extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;

	@Indexed
	private String uid;

	@Indexed
	@Property("pcode")
	private String productCode;

	@Property("pname")
	private String productName;

	@Indexed
	@Property("v")
	private int changedValue;

	@Indexed
	@Property("curr_v")
	private int currentValue;

	@Indexed(IndexDirection.ASC)
	@Property("ltime")
	private Date loggedTime;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getChangedValue() {
		return changedValue;
	}

	public void setChangedValue(int changedValue) {
		this.changedValue = changedValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public Date getLoggedTime() {
		return loggedTime;
	}

	public void setLoggedTime(Date loggedTime) {
		this.loggedTime = loggedTime;
	}

	public static ActivityChangeLog create(AccountActivity activity,
			Product product, int changedValue) {

		return create(activity, product, changedValue, new Date());
	}

	public static ActivityChangeLog create(AccountActivity activity,
			Product product, int changedValue, Date date) {
		ActivityChangeLog ret = new ActivityChangeLog();

		ret.setLoggedTime(date);
		ret.setUid(activity.getUid());
		ret.setProductCode(activity.getProductCode());
		ret.setProductName(product.getName());
		ret.setChangedValue(changedValue);
		ret.setCurrentValue(activity.getContActiveDays());

		return ret;
	}
}
