package com.sohu.sur.dao.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.sohu.sur.dao.ToolbarDao;
import com.sohu.sur.model.Toolbar;

public class ToolbarDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<Toolbar, ObjectId> implements ToolbarDao {

	public ToolbarDaoMorphiaImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}


}
