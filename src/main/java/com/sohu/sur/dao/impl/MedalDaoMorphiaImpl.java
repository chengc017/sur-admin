package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.MedalDao;
import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;

/**
 * User: guoyong
 * Date: 11-3-18 下午2:12
 */
public class MedalDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<Medal, ObjectId> implements MedalDao {

    public MedalDaoMorphiaImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public List<Medal> findAllMedals() {
        Query<Medal> q = this.createQuery().order("-min_days");
        return find(q).asList();
    }

    @Override
    public List<Medal> findProductMedals(Product product) {
        Query<Medal> q = this.createQuery().filter("for_prod", product.getId()).order("-min_days");
        return find(q).asList();
    }
    
    @Override
    public Medal findMedalByCode(String code) {
    	return super.findOne("code", code);
    }
    
    @Override
    public Medal findMedalByName(String name) {
    	return super.findOne("name", name);
    }
}
