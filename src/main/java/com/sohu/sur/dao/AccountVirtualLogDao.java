package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.util.Page;

/**
 * AccountVirtualLogDao
 * @author xuewuhao
 *
 */
public interface AccountVirtualLogDao extends ManagedEntityDao<AccountVirtualLog, ObjectId> {

	/**
	 * 虚拟商品接收/赠送列表
	 * @param uid
	 * @param actType 1接收 2赠送
	 * @param page
	 * @return
	 */
	public List<AccountVirtualLog> findAccountVirtualLogList(String uid,int actType,Page page);
	
	/**
	 * 查找某用户接收/赠送所有记录
	 * @param uid
	 * @param actType 1接收 2赠送
	 * @return
	 */
	public List<AccountVirtualLog> findAccountVirtualLogAll(String uid,int actType);
	
	/**
	 * 查找某时间段虚拟礼物赠送、接收列表
	 * @param s
	 * @param e
	 * @return
	 */
	public List<AccountVirtualLog> findAccountVirtualLogByDate(Date s,Date e);
	
}
