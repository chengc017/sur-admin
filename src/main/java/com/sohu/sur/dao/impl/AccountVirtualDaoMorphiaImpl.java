package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.WriteResult;
import com.sohu.sur.dao.AccountVirtualDao;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountVirtual;

public class AccountVirtualDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<AccountVirtual, ObjectId> implements
		AccountVirtualDao {

	public AccountVirtualDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public AccountVirtual findAccountVirtual(String owner, int actType, int giftId) {
		// TODO Auto-generated method stub
		Query<AccountVirtual> query = this.createQuery().field("act_type").equal(actType).field("owner").equal(owner)
				.field("gift_id").equal(giftId);
		List<AccountVirtual> list = this.find(query).asList();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else
			return null;
	}


	@Override
	public List<AccountVirtual> findAccountVirtualList(String owner, int actType) {
		// TODO Auto-generated method stub
		Query<AccountVirtual> query = this.createQuery().field("act_type").equal(actType).field("owner").equal(owner);
		query.order("-c_date");
		List<AccountVirtual> list = this.find(query).asList();
		return list;
	}
	
	public AccountVirtual findAccountVirtualOneGift(int giftId){
		Query<AccountVirtual> q = this.createQuery().field("gift_id").equal(giftId).order("-c_date");
		return this.find(q).asList().get(0);
	}
	
	public AccountVirtual findAccountVirtualOneGiftByOwner(int giftId,String owner){
		Query<AccountVirtual> q = this.createQuery().field("act_type").equal(0).field("gift_id").equal(giftId).field("owner").equal(owner).order("-c_date");
		return this.find(q).asList().get(0);
	}
	
}
