package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ScoreLogDao;
import com.sohu.sur.model.ScoreLog;
import com.sohu.sur.util.Page;

public class ScoreLogDaoMorphiaImpl extends BasicDAO<ScoreLog, String> implements ScoreLogDao {

	public ScoreLogDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public void saveLog(ScoreLog scoreLog) {
		this.save(scoreLog);
	}

	@Override
	public List<ScoreLog> findLogs(String uid, Date sDate, Date eDate, Page page) {
		Query<ScoreLog> q = this.createQuery();
		if (uid != null&&!uid.equals("")) {
			q.filter("uid = ", uid);
		}
		if (sDate != null) {
			q.field("ctime").greaterThanOrEq(sDate);
		}
		if (eDate != null) {
			q.field("ctime").lessThanOrEq(eDate);
		}
		q.order("-ctime").offset(page.getStart()).limit(page.getSize());
		page.setCount(this.count(q));
		return this.find(q).asList();
	}

}
