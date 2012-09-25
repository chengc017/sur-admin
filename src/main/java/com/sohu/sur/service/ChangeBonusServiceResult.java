package com.sohu.sur.service;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * User: 郭勇 Date: 2011-3-8 16:35:32
 */
public class ChangeBonusServiceResult {

	public static final ChangeBonusServiceResult OK = new ChangeBonusServiceResult(
			0, "OK");
	public static final ChangeBonusServiceResult INVALID_PARAMETER = new ChangeBonusServiceResult(
			1, "INVALID_PARAMETER");
	public static final ChangeBonusServiceResult NO_SUCH_ACTION = new ChangeBonusServiceResult(
			2, "NO_SUCH_ACTION");
	public static final ChangeBonusServiceResult INVALID_SIGN = new ChangeBonusServiceResult(
			3, "INVALID_SIGN");
	public static final ChangeBonusServiceResult DISABLED_ACTION = new ChangeBonusServiceResult(
			4, "DISABLED_ACTION");
	public static final ChangeBonusServiceResult LIMITATION_REACHED = new ChangeBonusServiceResult(
			5, "LIMITATION_REACHED");
	public static final ChangeBonusServiceResult FAILED = new ChangeBonusServiceResult(
			6, "FAILED");
	public static final ChangeBonusServiceResult BANNED_ACCOUNT = new ChangeBonusServiceResult(
			7, "BANNED_ACCOUNT");
	public static final ChangeBonusServiceResult INSUFFICIENT_BONUS = new ChangeBonusServiceResult(
			8, "INSUFFICIENT_BONUS");
	public static final ChangeBonusServiceResult UID_MISMATCHED = new ChangeBonusServiceResult(
			9, "UID_MISMATCHED");
	public static final ChangeBonusServiceResult UNKNOWN_ERROR = new ChangeBonusServiceResult(
			10, "UNKNOWN_ERROR");
    public static final ChangeBonusServiceResult REFERER_MISMATCHED = new ChangeBonusServiceResult(
			11, "REFERER_MISMATCHED");
    public static final ChangeBonusServiceResult INVALID_DATETIME = new ChangeBonusServiceResult(
    		12, "INVALID_DATETIME");
    public static final ChangeBonusServiceResult EXCEED_MAXVALUE = new ChangeBonusServiceResult(
    		13, "EXCEED_MAXVALUE");
	private int status;
	private String msg;

	private int currentBonus;

	private boolean cached = false;

    private int showType;

    private int changeBonus;

	private ChangeBonusServiceResult(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	private ChangeBonusServiceResult(int status, String msg, boolean cached) {
		this.status = status;
		this.msg = msg;
		this.cached = cached;
	}

	private ChangeBonusServiceResult(int status, String msg, int currentBonus) {
		this.status = status;
		this.msg = msg;
		this.currentBonus = currentBonus;
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

    public int getShowType() {
        return showType;
    }
    
    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getChangeBonus() {
        return changeBonus;
    }

    public void setChangeBonus(int changeBonus) {
        this.changeBonus = changeBonus;
    }

	public static ChangeBonusServiceResult OK(int currentBonus) {
		return new ChangeBonusServiceResult(0, "OK", currentBonus);
	}

    public static ChangeBonusServiceResult OK(int currentBonus, int showType, int changeBonus) {
        ChangeBonusServiceResult result = new ChangeBonusServiceResult(0, "OK", currentBonus);
        result.setChangeBonus(changeBonus);
        result.setShowType(showType);
        return result;
    }

	public static ChangeBonusServiceResult LIMITATION_REACHED() {
		return new ChangeBonusServiceResult(5, "LIMITATION_REACHED", true);
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ChangeBonusServiceResult)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ChangeBonusServiceResult rhs = (ChangeBonusServiceResult) obj;
		return status == rhs.getStatus();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("status", status)
				.append("msg", msg).toString();
	}

	public String toJsonString() {
		StringBuilder ret = new StringBuilder(
				"{\"result\":");
		ret.append(getStatus()).append(",\"msg\":\"").append(getMsg())
				.append("\"");
		if (ChangeBonusServiceResult.OK.equals(this)) {
			ret.append(",\"bonus\":").append(currentBonus);
			ret.append(",\"showType\":").append(showType);
            ret.append(",\"changeBonus\":").append(changeBonus);
		} else {
			ret.append(",\"bonus\":0");
		}
		if (cached) {
			ret.append(",\"cached\":1");
		}
		ret.append("}");
		return ret.toString();
	}

	public String toJsonpString() {
		return new StringBuilder("surChangeBonusCallback(")
				.append(toJsonString()).append(");").toString();
	}
}
