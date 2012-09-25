package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.SystemOverviewMedalDao;
import com.sohu.sur.model.SystemOverviewMedal;

public class SystemOverviewMedalDaoMorphiaImpl extends BasicDAO<SystemOverviewMedal, String> implements SystemOverviewMedalDao {

	public SystemOverviewMedalDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<SystemOverviewMedal> findSystemOverview(Date scdate, Date ecdate) {
		// TODO Auto-generated method stub
		Query<SystemOverviewMedal> q = this.createQuery();
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
