package com.sohu.sur.dao;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Action;
import com.sohu.sur.model.GiftBag;
/**
 * 
 * @author xuewuhao
 *
 */
public interface GiftBagDao extends ManagedEntityDao<GiftBag, ObjectId> {

	/**
	 * 查找记录uid
	 * @param uid
	 * @return
	 */
	GiftBag findGiftBagByUid(String uid);
	/**
	 * 查询当天中奖记录数
	 * @return
	 */
	int countGiftBagCurDay();
}
