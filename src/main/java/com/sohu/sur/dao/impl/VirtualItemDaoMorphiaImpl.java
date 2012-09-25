package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.VirtualItemDao;
import com.sohu.sur.model.VirtualItem;
import com.sohu.sur.model.VirtualOverview;

public class VirtualItemDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<VirtualItem, ObjectId> implements VirtualItemDao {

	public VirtualItemDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<VirtualItem> findVirtualItem(Date scdate, Date ecdate) {
		// TODO Auto-generated method stub
		Query<VirtualItem> q = this.createQuery();
		if (scdate != null) {
			q.field("cdate").greaterThanOrEq(scdate);
		}
		if (ecdate != null) {
			q.field("cdate").lessThan(ecdate);
		}
		q.order("-exchangeUidNum");
		return this.find(q).asList();
	}

}
