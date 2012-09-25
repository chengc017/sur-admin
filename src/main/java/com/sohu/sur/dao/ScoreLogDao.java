package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.ScoreLog;
import com.sohu.sur.util.Page;

/**
 * 后台操作日志dao
 * @author xuewuhao
 *
 */
public interface ScoreLogDao{

	void saveLog(ScoreLog scoreLog);

	/**
	 * 查询指定用户、时间段操作记录
	 * @param uid
	 * @param sDate
	 * @param eDate
	 * @param page
	 * @return
	 */
	List<ScoreLog> findLogs(String uid,Date sDate,Date eDate, Page page);
}
