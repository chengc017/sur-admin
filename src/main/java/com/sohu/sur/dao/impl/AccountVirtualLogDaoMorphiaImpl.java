package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.AccountVirtualLogDao;
import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.util.Page;

public class AccountVirtualLogDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<AccountVirtualLog, ObjectId> implements
		AccountVirtualLogDao {

	public AccountVirtualLogDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	
	public List<AccountVirtualLog> findAccountVirtualLogList(String uid,int actType,Page page) {
		// TODO Auto-generated method stub
		String usertmp = "";
		if(actType==1){
			usertmp = "to_user";
		}else if(actType == 2){
			usertmp = "from_user";
		}
		Query<AccountVirtualLog> query = this.createQuery().field("act_type").equal(actType).field(usertmp).equal(uid);
		query.order("-c_date").offset(page.getStart()).limit(page.getSize());
		page.setCount(this.count(query));
		List<AccountVirtualLog> list = this.find(query).asList();
		return list;
	}

	public List<AccountVirtualLog> findAccountVirtualLogAll(String uid,int actType) {
		// TODO Auto-generated method stub
		String usertmp = "";
		if(actType==1){
			usertmp = "to_user";
		}else if(actType == 2){
			usertmp = "from_user";
		}
		Query<AccountVirtualLog> query = this.createQuery().field("act_type").equal(actType).field(usertmp).equal(uid);
		query.order("-c_date");
		List<AccountVirtualLog> list = this.find(query).asList();
		return list;
	}

	@Override
	public List<AccountVirtualLog> findAccountVirtualLogByDate(Date s, Date e) {
		// TODO Auto-generated method stub
		Query<AccountVirtualLog> query = this.createQuery().field("c_date").greaterThanOrEq(s).field("c_date").lessThan(e);
		List<AccountVirtualLog> list = this.find(query).asList();
		return list;
	}
}
