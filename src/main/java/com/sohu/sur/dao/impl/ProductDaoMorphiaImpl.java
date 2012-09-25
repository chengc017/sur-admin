package com.sohu.sur.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.ProductDao;
import com.sohu.sur.model.Product;

/**
 * User: 郭勇
 * Date: 2011-3-15 15:50:49
 */
public class ProductDaoMorphiaImpl extends ManagedEntityDaoMorphiaImpl<Product, ObjectId> implements ProductDao {

    public ProductDaoMorphiaImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public Product findProductByCode(String productCode) {
        return this.findOne("code", productCode);
    }

    @Override
    public List<Product> findAllProducts() {
        return find().asList();
    }
    
    public Product findProductByName(String name){
		return super.findOne("name", name);
	}

    @Override
    public List<Product> findAllEnabledProducts() {
        Query<Product> q = createQuery().field("ena").equal(true).order("s_order");
        return find(q).asList();
    }
}
