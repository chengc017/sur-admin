package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.VirtualOverviewDao;
import com.sohu.sur.model.VirtualOverview;

public class VirtualOverviewDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<VirtualOverview, ObjectId> implements VirtualOverviewDao {

	public VirtualOverviewDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<VirtualOverview> findVirtualOverview(Date scdate, Date ecdate) {
		// TODO Auto-generated method stub
		Query<VirtualOverview> q = this.createQuery();
		if (scdate != null) {
			q.field("cdate").greaterThanOrEq(scdate);
		}
		if (ecdate != null) {
			q.field("cdate").lessThan(ecdate);
		}
		q.order("-cdate");
		return this.find(q).asList();
	}

}
