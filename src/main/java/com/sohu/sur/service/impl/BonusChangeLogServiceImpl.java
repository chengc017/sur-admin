package com.sohu.sur.service.impl;

import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.BonusChangeLogDao;
import com.sohu.sur.model.BonusChangeLog;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.model.ScoreLotteryAnalyse;
import com.sohu.sur.service.BonusChangeLogService;
import com.sohu.sur.util.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: guoyong Date: 11-3-18 上午10:29
 */
public class BonusChangeLogServiceImpl implements BonusChangeLogService {

	private final BonusChangeLogDao bonusChangeLogDao;

	private final AccountDao accountDao;

	public BonusChangeLogServiceImpl(BonusChangeLogDao bonusChangeLogDao, AccountDao accountDao) {
		this.bonusChangeLogDao = bonusChangeLogDao;
		this.accountDao = accountDao;
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, int pageNo, int pageSize) {

		if (pageNo <= 0 || pageSize <= 0) {
			throw new IllegalArgumentException("pageNo or pageSize must be greater than zero.");
		}
		if (changeType == BonusChangeType.UNKNOWN) {
			return bonusChangeLogDao.findBonusChangeLogs(uid, pageNo, pageSize);
		} else {
			return bonusChangeLogDao.findBonusChangeLogs(uid, changeType, pageNo, pageSize);
		}
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, String day, Page page) {
		Date startTime = null;
		Date endTime = null;
		if (day != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
			try {
				startTime = sdf.parse(day + "000000000");
				endTime = sdf.parse(day + "235959999");
			} catch (ParseException pe) {
			}
		}
		if (changeType == BonusChangeType.UNKNOWN) {
			return bonusChangeLogDao.findBonusChangeLogs(uid, startTime, endTime, page);
		} else {
			return bonusChangeLogDao.findBonusChangeLogs(uid, changeType, startTime, endTime, page);
		}
	}

	@Override
	public int getTotalBonus(String uid, BonusChangeType changeType) {

		return accountDao.getTotalBonus(uid, changeType);
	}
	@Override
	public List<BonusChangeLog> findBonusChangeLogsLotterys(String actionCode,Date stime, Date etime) {
		return bonusChangeLogDao.findBonusChangeLogsLottery(actionCode,stime, etime);
	}
}
