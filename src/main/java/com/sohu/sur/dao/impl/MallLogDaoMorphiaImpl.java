package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.WriteResult;
import com.sohu.sur.dao.MallLogDao;
import com.sohu.sur.model.MallLog;

public class MallLogDaoMorphiaImpl extends BasicDAO<MallLog, String> implements
		MallLogDao {

	public MallLogDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public void writeMallLog(MallLog mlog) {
		this.save(mlog);
	}

	@Override
	public List<MallLog> findMallLogs(String uid, int pageNo, int pageSize) {
		Query<MallLog> q = this.createQuery().filter("uid =", uid);

		q.order("-ctime").offset((pageNo - 1) * pageSize).limit(pageSize);

		return this.find(q).asList();
	}
	
	public List<MallLog> findLotteryMallLogs(Date stime,Date etime){
		Query<MallLog> q = this.createQuery();
		if (stime != null) {
			q.field("ctime").greaterThanOrEq(stime);
		}
		if (etime != null) {
			q.field("ctime").lessThanOrEq(etime);
		}
		return this.find(q).asList();
	}


	@Override
	public List<MallLog> findVirtualMallLogs(Date stime, Date etime) {
		// TODO Auto-generated method stub
		Query<MallLog> q = this.createQuery().field("giftType").equal(2);
		if (stime != null) {
			q.field("ctime").greaterThanOrEq(stime);
		}
		if (etime != null) {
			q.field("ctime").lessThan(etime);
		}
		return this.find(q).asList();
	}

}
