package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.ScoreLog;
import com.sohu.sur.util.Page;

public interface ScoreLogService {

	/**
	 * 保存
	 * @param log
	 */
	void saveLog(ScoreLog log);

	/**
	 * 查找行为日志
	 * @param uid
	 * @param stime
	 * @param etime
	 * @param page
	 * @return
	 */
	List<ScoreLog> findLogs(String uid,String stime,String etime, Page page);
}
