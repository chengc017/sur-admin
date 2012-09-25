package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ScoreLotteryAnalyseDao;
import com.sohu.sur.model.ScoreLotteryAnalyse;

public class ScoreLotteryAnalyseDaoMorphiaImpl extends BasicDAO<ScoreLotteryAnalyse, String> implements
		ScoreLotteryAnalyseDao {

	public ScoreLotteryAnalyseDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	public void saveScoreLotteryAnalyse(ScoreLotteryAnalyse scoreLotteryAnalyse) {
		this.save(scoreLotteryAnalyse);
	}

	public List<ScoreLotteryAnalyse> findLotteryAnalyse(String type,Date scdate, Date ecdate) {
		Query<ScoreLotteryAnalyse> q = this.createQuery();
		q.filter("type = ", type);
		if (scdate != null) {
			q.field("cdate").greaterThanOrEq(scdate);
		}
		if (ecdate != null) {
			q.field("cdate").lessThanOrEq(ecdate);
		}
		q.order("-cdate");
		return this.find(q).asList();
	}

}
