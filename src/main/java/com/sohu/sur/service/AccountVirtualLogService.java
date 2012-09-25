package com.sohu.sur.service;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.util.Page;

/**
 * 虚拟商品历史接口方法
 * @author xuewuhao
 *
 */
public interface AccountVirtualLogService {
	
	/**
	 * 虚拟商品接收/赠送列表
	 * @param uid
	 * @param actType 1接收 2赠送
	 * @param page
	 * @return
	 */
	List<AccountVirtualLog> findAccountVirtualLogs(String uid,int actType, Page page);
	/**
	 * 虚拟商品接收/赠送列表all
	 * @param uid
	 * @param actType 1接收 2赠送
	 * @param page
	 * @return
	 */
	List<AccountVirtualLog> findAccountVirtualLogsAll(String uid,int actType);
	/**
	 * 删除记录
	 * @param logId
	 */
	public void romoveLog(String logId); 
	/**
	 * 查找记录
	 * @param logId
	 * @return
	 */
	public AccountVirtualLog  findLog(String logId);
	
	
	/**
	 * 查找某时间段虚拟礼物赠送、接收列表
	 * @param s
	 * @param e
	 * @return
	 */
	public List<AccountVirtualLog> findLogByDate(Date s,Date e);
}
