package com.sohu.sur.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sohu.sur.dao.MallLogDao;
import com.sohu.sur.model.MallLog;
import com.sohu.sur.service.MallLogService;

public class MallLogServiceImpl implements MallLogService {
	private MallLogDao mallLogDao;
	public MallLogServiceImpl() {
	}

	public MallLogServiceImpl(MallLogDao mallLogDao) {
		this.mallLogDao = mallLogDao;
	}

	@Override
	public void writeMallLog(MallLog mlog) {
		mallLogDao.writeMallLog(mlog);
	}

	@Override
	public List<MallLog> findMallLogs(String uid, int pageNo, int pageSize) {
		return mallLogDao.findMallLogs(uid, pageNo, pageSize);
	}
	@Override
	public List<MallLog> findLotteryMallLogs(Date stime, Date etime) {
		return mallLogDao.findLotteryMallLogs(stime, etime);
	}

	@Override
	public List<MallLog> findVirtualMallLogs(Date stime, Date etime) {
		// TODO Auto-generated method stub
		return mallLogDao.findVirtualMallLogs(stime, etime);
	}

	

}
