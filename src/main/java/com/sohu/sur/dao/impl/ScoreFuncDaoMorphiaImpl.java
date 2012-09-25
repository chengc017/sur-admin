package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ScoreFuncDao;
import com.sohu.sur.model.ScoreFunc;

public class ScoreFuncDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<ScoreFunc, ObjectId> implements ScoreFuncDao {

	public ScoreFuncDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}


	public List<ScoreFunc> findPidFunc(ObjectId pid){
		Query<ScoreFunc> q = this.createQuery().filter("pid =", pid);
		return this.find(q).asList();
	}

}
