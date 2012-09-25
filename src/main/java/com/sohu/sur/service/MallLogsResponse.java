package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.MallLog;

public class MallLogsResponse {
	private List<MallLog> giftDealResult;

	public List<MallLog> getGiftDealResult() {
		return giftDealResult;
	}

	public void setGiftDealResult(List<MallLog> giftDealResult) {
		this.giftDealResult = giftDealResult;
	}
}
