package com.sohu.sur.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.code.morphia.annotations.*;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSON;
import com.google.code.morphia.utils.IndexDirection;

/**
 * 积分变更记录
 * 
 * User: 郭勇 Date: 2011-2-25 13:29:36
 */
@Entity("bonus_change_logs")
public class BonusChangeLog implements Serializable{

	@Id
	private ObjectId id;

	@Indexed
	private String uid;

	@Indexed
	@Property("ltype")
	private int logType;

	@Property("acode")
    @Indexed
    private String actionCode;

	@Property("aname")
	private String actionName;

    @Property("pcode")
    @Indexed
    private String productCode;

	@Property("v")
	private int changedValue;

	@Property("desc")
	private String description;

    @Transient
	private Map<String, Object> descJsonObj = new HashMap<String, Object>();

	@Indexed(IndexDirection.DESC)
	@Property("ltime")
	private Date loggedTime;

	public BonusChangeLog() {
	}

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

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getChangedValue() {
		return changedValue;
	}

	public void setChangedValue(int changedValue) {
		this.changedValue = changedValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@SuppressWarnings("unchecked")
    // todo change method name to avoid morphia mapping warning
    public Map<String, Object> getDescJsonObj() {
		if (null == descJsonObj || descJsonObj.size() == 0) {
			descJsonObj.put("value", this.description);
			if (null != description && description.trim().length() > 0) {
				try {
					Object tmp = JSON.parse(description);
					if (null != tmp) {
						descJsonObj.putAll((Map<String, Object>) tmp);
					}
				} catch (Exception e) {
				}
			}
		}
		return descJsonObj;
	}

	public Date getLoggedTime() {
		return loggedTime;
	}

	public void setLoggedTime(Date loggedTime) {
		this.loggedTime = loggedTime;
	}

	public static BonusChangeLog create(Account acct, Action action,
			int changedValue, String desc) {
		BonusChangeLog ret = new BonusChangeLog();

		ret.setLoggedTime(new Date());

		ret.setLogType(action.getBonusChangeType());
        ret.setActionCode(action.getCode());
		ret.setActionName(action.getName());

		ret.setUid(acct.getUid());
		ret.setDescription(desc);
		ret.setChangedValue(changedValue);

		return ret;
	}

    public static BonusChangeLog create(Account account, Action action, String pcode, int changedValue, String desc){
        BonusChangeLog ret = BonusChangeLog.create(account,action,changedValue,desc);
        if(pcode==null){
            ret.setProductCode("n/a");
        }   else {
            ret.setProductCode(pcode);
        }
        return ret;
    }
}
