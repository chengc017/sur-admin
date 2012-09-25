package com.sohu.sur.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.BonusChangeLogDao;
import com.sohu.sur.model.BonusChangeLog;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.util.Page;

import java.util.Date;
import java.util.List;

/**
 * User: 郭勇 Date: 2011-2-25 10:11:19
 */
public class BonusChangeLogDaoMorphiaImpl extends BasicDAO<BonusChangeLog, String> implements BonusChangeLogDao {

	public BonusChangeLogDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public void writeBonusChangeLog(BonusChangeLog cLog) {
		this.save(cLog);
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, int pageNo, int pageSize) {

		Query<BonusChangeLog> q = this.createQuery().filter("uid =", uid).filter("ltype =", changeType.getValue());

		q.order("-ltime").offset((pageNo - 1) * pageSize).limit(pageSize);

		return this.find(q).asList();
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, int pageNo, int pageSize) {

		Query<BonusChangeLog> q = this.createQuery().filter("uid =", uid);

		q.order("-ltime").offset((pageNo - 1) * pageSize).limit(pageSize);

		return this.find(q).asList();
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, Date startTime,
			Date endTime, Page page) {
		Query<BonusChangeLog> q = this.createQuery().filter("uid =", uid).filter("ltype =", changeType.getValue());
		if (startTime != null) {
			q.field("ltime").greaterThanOrEq(startTime);
		}
		if (endTime != null) {
			q.field("ltime").lessThanOrEq(endTime);
		}
		page.setCount(q.countAll());
		q.order("-ltime").offset(page.getStart()).limit(page.getSize());
		return this.find(q).asList();
	}

	@Override
	public List<BonusChangeLog> findBonusChangeLogs(String uid, Date startTime, Date endTime, Page page) {
		Query<BonusChangeLog> q = this.createQuery().filter("uid =", uid);
		if (startTime != null) {
			q.field("ltime").greaterThanOrEq(startTime);
		}
		if (endTime != null) {
			q.field("ltime").lessThanOrEq(endTime);
		}
		page.setCount(q.countAll());
		q.order("-ltime").offset(page.getStart()).limit(page.getSize());
		return this.find(q).asList();
	}

	public List<BonusChangeLog> findBonusChangeLogsLottery(String actionCode,Date startTime, Date endTime) {
		// 消费 抽奖
		Query<BonusChangeLog> q = this.createQuery().filter("ltype =", 4).filter("acode =", actionCode);
		if (startTime != null) {
			q.field("ltime").greaterThanOrEq(startTime);
		}
		if (endTime != null) {
			q.field("ltime").lessThanOrEq(endTime);
		}
		q.order("-ltime");
		return this.find(q).asList();
	}
}
