package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.SystemOverviewActionDao;
import com.sohu.sur.model.SystemOverviewAction;

public class SystemOverviewActionDaoMorphiaImpl extends BasicDAO<SystemOverviewAction, String> implements SystemOverviewActionDao {

	public SystemOverviewActionDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<SystemOverviewAction> findSystemOverview(Date scdate, Date ecdate) {
		// TODO Auto-generated method stub
		Query<SystemOverviewAction> q = this.createQuery();
		if (scdate != null) {
			q.field("cdate").greaterThanOrEq(scdate);
		}
		if (ecdate != null) {
			q.field("cdate").lessThanOrEq(ecdate);
		}
		q.order("-cdate,bonusTotal");
		return this.find(q).asList();
	}

}
