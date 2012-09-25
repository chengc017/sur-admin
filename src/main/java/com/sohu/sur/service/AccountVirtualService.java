package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.model.AccountVirtual;

/**
 * 虚拟商品接口方法
 * @author xuewuhao
 *
 */
public interface AccountVirtualService {
	
	/**
	 * 查找一个虚拟商品
	 * @param giftId
	 * @return
	 */
	public AccountVirtual findAccountVirtualGift(int  giftId);
	/**
	 * 查找指定用户指定虚拟商品已购买记录
	 * @param giftId
	 * @return
	 */
	public AccountVirtual findAccountVirtualGiftByOwner(int  giftId,String owner);
	/**
	 * 增加用户购买/被赠送者收到虚拟商品数量
	 * @param av
	 * @param numparams 购买数
	 * @return  
	 * 0 成功
	 * 1 失败
	 */
	public int incBuyOrGiveAccountVirtual(AccountVirtual av,int numparams);
	/**
	 * 增加赠送者送出虚拟商品数量
	 * @param avFrom
	 * @return  
	 * 0 成功
	 * 1 失败
	 */
	public int incGiveOutAccountVirtual(AccountVirtual avFrom);
	/**
	 * 减少赠送者购买虚拟商品数量
	 * @param av
	 * @return
	 * 0 成功
	 * 1 记录为空或者数量为0
	 * 2 失败
	 */
	public int decreaseBuyAccountVirtual(AccountVirtual av);
	/** 
	 * 赠送虚拟物品：增加受赠者收到商品数、减少赠送者已购买商品数、增加赠送虚拟商品历史记录 同时更新cache
	 * @param avTo    增加受赠者收到商品数对象
	 * @param fromUser  赠送者
	 * @param info  留言
	 * @param objectCache  memcache对象
	 * @return  
	 * 0 成功/1记录为空或者数量为0/2 失败
	 */
	public int giveAccountVirtual(AccountVirtual avTo, String fromUser, String info,ObjectCache objectCache);
	
	/**
	 * 商品列表
	 * @param actType  记录类型 0购买  1接收礼物  2对人赠送
	 * @param owner
	 * @return
	 */
	public List<AccountVirtual> virtualList(int actType, String owner);
	
	/**
	 * 批量送虚拟商品
	 * @param av
	 * @param toUsers
	 * @param fromUser
	 * @param info
	 * @param objectCache
	 * @return
	 */
	public int patchGiveAccountVirtual(AccountVirtual av,String[] toUsers, String fromUser, String info,ObjectCache objectCache);
	
}
