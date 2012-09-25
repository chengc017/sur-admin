package com.sohu.sur.model;

import java.util.List;

public class MallItemsLotteryGiftResponse {
	private int count;
	private List<LotteryItem> lotteryGift;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<LotteryItem> getLotteryGift() {
		return lotteryGift;
	}

	public void setLotteryGift(List<LotteryItem> lotteryGift) {
		this.lotteryGift = lotteryGift;
	}


}
