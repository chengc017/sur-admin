package com.sohu.sur.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.GiftBagDao;
import com.sohu.sur.model.GiftBag;

public class GiftBagDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<GiftBag, ObjectId> implements GiftBagDao {

	public GiftBagDaoMorphiaImpl(Datastore datastore) {
		super(datastore);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GiftBag findGiftBagByUid(String uid) {
		// TODO Auto-generated method stub
		return this.findOne("uid", uid.trim());
	}

	@Override
	public int countGiftBagCurDay() {
		// TODO Auto-generated method stub
		int num = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		Query<GiftBag> query = this.createQuery().field("fragment1").equal(true).field("fragment2").equal(true)
				.field("fragment3").equal(true).field("fragment4").equal(true);
		query.field("cdate").greaterThanOrEq(calendar.getTime());
		List<GiftBag> list = query.asList();
		if(list!=null&&list.size()>0){
			num = list.size();
		}
		return num;
	}

}
