package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.RankDao;
import com.sohu.sur.model.Rank;

/**
 * User: guoyong
 * Date: 11-3-18 下午2:14
 */
public class RankDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<Rank, ObjectId> implements RankDao {

    public RankDaoMorphiaImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public List<Rank> findAllRanks() {
        Query<Rank> q = this.createQuery().order("min_exp");
        return find(q).asList();
    }
    @Override
    public Rank findRankByIconId(String iconId) {
    	return super.findOne("icon_id", iconId);
    }
    
    @Override
    public Rank findRankByName(String name) {
    	return super.findOne("name", name);
    }
}
