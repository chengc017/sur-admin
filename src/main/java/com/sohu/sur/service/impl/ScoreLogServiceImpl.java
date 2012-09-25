package com.sohu.sur.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sohu.sur.dao.ScoreLogDao;
import com.sohu.sur.model.ScoreLog;
import com.sohu.sur.service.ScoreLogService;
import com.sohu.sur.util.Page;

public class ScoreLogServiceImpl implements ScoreLogService {

	private ScoreLogDao scoreLogDao;

	public ScoreLogServiceImpl() {
	}

	public ScoreLogServiceImpl(ScoreLogDao scoreLogDao) {
		this.scoreLogDao = scoreLogDao;
	}

	@Override
	public void saveLog(ScoreLog log) {
		scoreLogDao.saveLog(log);
	}

	@Override
	public List<ScoreLog> findLogs(String uid, String stime, String etime, Page page) {
		Date sTime = null;
		Date eTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (stime != null&&!stime.equals("")) {

			try {
				sTime = sdf.parse(stime + " 00:00:00");
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		if (etime != null&&!etime.equals("")) {
			try {
				eTime = sdf.parse(etime + " 23:59:59");
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		return scoreLogDao.findLogs(uid, sTime, eTime, page);
	}

}
