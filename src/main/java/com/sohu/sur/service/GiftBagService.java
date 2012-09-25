package com.sohu.sur.service;

import com.sohu.sur.model.GiftBag;
import com.sohu.sur.util.po.GiftBagResult;

public interface GiftBagService {

	/**
	 * 查找当天获奖记录数
	 * @return
	 */
    int countNum();
	/**
	 * 查找记录
	 * @param uid
	 * @return
	 */
	GiftBag findGiftBag(String uid);
	
	/**
	 * 点击领取奖品接口
	 * @param uid
	 * @param count 当天中奖数
	 * @return
	 */
	GiftBagResult  doGiftBag(String uid,String count);
}
