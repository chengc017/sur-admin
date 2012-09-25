package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ActionDao;
import com.sohu.sur.model.Action;
import com.sohu.sur.model.Product;

/**
 * User: 郭勇 Date: 2011-3-10 17:27:27
 */
public class ActionDaoMorphiaImpl extends
		ManagedEntityDaoMorphiaImpl<Action, ObjectId> implements ActionDao {

	public ActionDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public Action findActionByCode(String actionCode) {
		return this.findOne("code", actionCode);
	}
	
	public Action findActionByName(String name){
		return super.findOne("name", name);		
	}
	@Override
    public List<Action> findAllEnabledActions() {
        Query<Action> q = createQuery().field("ena").equal(true);
        return find(q).asList();
    }
}
