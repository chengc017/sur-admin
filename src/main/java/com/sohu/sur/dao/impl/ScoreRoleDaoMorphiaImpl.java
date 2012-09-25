package com.sohu.sur.dao.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.sohu.sur.dao.ScoreRoleDao;
import com.sohu.sur.model.ScoreRole;

public class ScoreRoleDaoMorphiaImpl  extends ManagedEntityDaoMorphiaImpl<ScoreRole, ObjectId>  implements ScoreRoleDao {

	public ScoreRoleDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ScoreRole findScoreRole(String name) {
		// TODO Auto-generated method stub
		return this.findOne("name", name);
	}
	

}
