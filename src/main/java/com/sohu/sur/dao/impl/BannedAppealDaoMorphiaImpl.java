package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.BannedAppealDao;
import com.sohu.sur.model.BannedAppeal;
import com.sohu.sur.util.Page;

public class BannedAppealDaoMorphiaImpl extends
		ManagedEntityDaoMorphiaImpl<BannedAppeal, ObjectId> implements
		BannedAppealDao {

	public BannedAppealDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public List<BannedAppeal> findBannedAppeals(boolean isOverrule, Page page) {
		Query<BannedAppeal> query = this.createQuery().field("is_overrule").equal(isOverrule);
		page.setCount(query.countAll());
		query.order("-appeal_date").offset(page.getStart()).limit(page.getSize());
		return this.find(query).asList();
	}

	@Override
	public BannedAppeal findBannedAppealByUid(String uid) {
		Query<BannedAppeal> query = this.createQuery().field("uid").equal(uid);
		return this.findOne(query);
	}

	@Override
	public boolean saveOrUpdateBannedAppeal(BannedAppeal bannedAppeal) {
		Key<BannedAppeal> key = this.save(bannedAppeal);
		if (null == key.getId()) {
			return false;
		}
		return true; 
	}

	@Override
	public boolean deleteBannedAppeal(BannedAppeal bannedAppeal) {
		int count = this.delById(bannedAppeal.getId());
		if(count>0){
			return true;
		}
		return false;
	}

}
