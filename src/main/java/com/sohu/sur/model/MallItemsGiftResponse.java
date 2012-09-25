package com.sohu.sur.model;

import java.util.List;

public class MallItemsGiftResponse {
	private int count;
	private List<GiftItem> gift;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<GiftItem> getGift() {
		return gift;
	}

	public void setGift(List<GiftItem> gift) {
		this.gift = gift;
	}
}
