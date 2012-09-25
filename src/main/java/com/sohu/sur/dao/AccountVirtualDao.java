package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountVirtual;

/**
 * AccountVirtualDao
 * @author xuewuhao
 *
 */
public interface AccountVirtualDao extends ManagedEntityDao<AccountVirtual, ObjectId> {

	/**
	 * 查找某人——购买/接收/赠送——商品giftId记录
	 * @param owner
	 * @param actType  记录类型 0购买  1接收礼物  2对人赠送
	 * @param giftId
	 * @return
	 */
	public AccountVirtual findAccountVirtual(String owner,int actType,int giftId);
	
	/**
	 * 查找某人——购买/接收/赠送——商品列表
	 * @param owner
	 * @param actType 记录类型 0购买  1接收礼物  2对人赠送
	 * @return
	 */
	public List<AccountVirtual> findAccountVirtualList(String owner,int actType);
	
	/**
	 * 查找某个虚拟商品信息
	 * @param giftId
	 * @return
	 */
	public AccountVirtual findAccountVirtualOneGift(int giftId);
	/**
	 * 查找指定人 指定虚拟商品已购买记录信息
	 * @param giftId
	 * @return
	 */
	public AccountVirtual findAccountVirtualOneGiftByOwner(int giftId,String owner);
}
