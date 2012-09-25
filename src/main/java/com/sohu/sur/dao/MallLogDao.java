package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.MallLog;

public interface MallLogDao {
	
	void writeMallLog(MallLog mlog);

	/**
	 * 查询指定用户的商城交易记录
	 * @param uid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<MallLog> findMallLogs(String uid, int pageNo, int pageSize);
	
	/**
	 * 查询某天商城抽奖记录
	 * @param stime
	 * @param etime
	 * @return
	 */
	List<MallLog> findLotteryMallLogs(Date stime,Date etime);
	
	/**
	 * 查询某天虚拟商品兑换记录
	 * @param stime
	 * @param etime
	 * @return
	 */
	List<MallLog> findVirtualMallLogs(Date stime,Date etime);

}
