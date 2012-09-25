package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ScoreLotteryProductAnalyseDao;
import com.sohu.sur.model.ScoreLotteryProductAnalyse;

public class ScoreLotteryProductAnalyseDaoMorphiaImpl extends BasicDAO<ScoreLotteryProductAnalyse, String> implements
ScoreLotteryProductAnalyseDao {

	public ScoreLotteryProductAnalyseDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	public void saveScoreLotteryProductAnalyse(ScoreLotteryProductAnalyse scoreLotteryProductAnalyse) {
		this.save(scoreLotteryProductAnalyse);
	}

	public List<ScoreLotteryProductAnalyse> findScoreLotteryProductAnalyse(String productId,String type,String order,Date scdate, Date ecdate) {
		Query<ScoreLotteryProductAnalyse> q = this.createQuery();
		q.filter("type = ", type);
		if(productId!=null&&!productId.equals("")){
			q.filter(" productId = ", productId);
		}
		if (scdate != null) {
			q.field("cdate").greaterThanOrEq(scdate);
		}
		if (ecdate != null) {
			q.field("cdate").lessThanOrEq(ecdate);
		}
		if(order == null){
			q.order("-lotteryNum");
		}else{
			q.order(order);
		}
		//默认最多返回1000条
		q.limit(1000);
		return this.find(q).asList();
	}

}
