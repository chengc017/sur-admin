package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ScoreUserDao;
import com.sohu.sur.model.ScoreUser;

public class ScoreUserDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<ScoreUser, ObjectId>  implements ScoreUserDao {

	public ScoreUserDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ScoreUser> findScoreUser(String name, int state) {
		// TODO Auto-generated method stub
		Query<ScoreUser> query = this.createQuery().filter("state",state);
		if(name!=null&&!"".equals(name)){
			query.field("name").equal(name);
		}
		return this.find(query).asList();
	}

	@Override
	public List<ScoreUser> checkScoreUser(String name, String pwd) {
		// TODO Auto-generated method stub
		Query<ScoreUser> query = this.createQuery();
		if(name!=null&&!"".equals(name)){
			query.field("name").equal(name);
		}
		if(pwd!=null&&!"".equals(pwd)){
			query.field("pwd").equal(pwd);
		}
		return this.find(query).asList();
	}

	@Override
	public ScoreUser findScoreUser(String name) {
		// TODO Auto-generated method stub
		return this.findOne("name",name);
	}

}
