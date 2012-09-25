package com.sohu.sur.service;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.MallLog;

public interface MallLogService {

	void writeMallLog(MallLog mlog);

	List<MallLog> findMallLogs(String uid, int pageNo, int pageSize);
	/**
	 * 查找某天抽奖记录
	 * @param stime
	 * @param etime
	 * @return
	 */
	List<MallLog> findLotteryMallLogs(Date stime,Date etime);
	
	/**
	 * 查找某天虚拟商品兑换记录
	 * @param stime
	 * @param etime
	 * @return
	 */
	List<MallLog> findVirtualMallLogs(Date stime,Date etime);
}
