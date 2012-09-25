package com.sohu.sur.service;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * User: 郭勇 Date: 2011-3-15 11:07:51
 */
public class ChangeActivityServiceResult {

	public static final ChangeActivityServiceResult OK = new ChangeActivityServiceResult(
			0, "OK");
	public static final ChangeActivityServiceResult INVALID_PARAMETER = new ChangeActivityServiceResult(
			1, "INVALID_PARAMETER");
	public static final ChangeActivityServiceResult NO_SUCH_PRODUCT = new ChangeActivityServiceResult(
			2, "NO_SUCH_PRODUCT");
	public static final ChangeActivityServiceResult DISABLED_PRODUCT = new ChangeActivityServiceResult(
			3, "DISABLED_PRODUCT");
	@Deprecated
	public static final ChangeActivityServiceResult INVALID_SIGN = new ChangeActivityServiceResult(
			4, "INVALID_SIGN");
	@Deprecated
	public static final ChangeActivityServiceResult NO_SUCH_ACCOUNT = new ChangeActivityServiceResult(
			5, "NO_SUCH_ACCOUNT");
	public static final ChangeActivityServiceResult FAILED = new ChangeActivityServiceResult(
			6, "FAILED");
	public static final ChangeActivityServiceResult BANNED_ACCOUNT = new ChangeActivityServiceResult(
			7, "BANNED_ACCOUNT");
	public static final ChangeActivityServiceResult ALREADY_UPDATED = new ChangeActivityServiceResult(
			8, "ALREADY_UPDATED");
	public static final ChangeActivityServiceResult UID_MISMATCHED = new ChangeActivityServiceResult(
			9, "UID_MISMATCHED");
	public static final ChangeActivityServiceResult UNKNOWN_ERROR = new ChangeActivityServiceResult(
			10, "UNKNOWN_ERROR");

	private int status;
	private String msg;

	private boolean cached = false;

	private ChangeActivityServiceResult(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	private ChangeActivityServiceResult(int status, String msg, boolean cached) {
		this.status = status;
		this.msg = msg;
		this.cached = cached;
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public static ChangeActivityServiceResult ALREADY_UPDATED() {
		return new ChangeActivityServiceResult(8, "ALREADY_UPDATED", true);
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ChangeActivityServiceResult)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ChangeActivityServiceResult rhs = (ChangeActivityServiceResult) obj;
		return status == rhs.getStatus();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("status", status)
				.append("msg", msg).toString();
	}

	public String toJsonString() {
		StringBuilder ret = new StringBuilder("{\"result\":")
				.append(getStatus()).append(",\"msg\":\"").append(getMsg())
				.append("\"");
		if (cached) {
			ret.append(",\"cached\":1");
		}
		ret.append("}");
		return ret.toString();
	}

	public String toJsonpString() {
		return new StringBuilder("surChangeActivityCallback(")
				.append(toJsonString()).append(");").toString();
	}
}
