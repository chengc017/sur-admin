package com.sohu.sur.dao.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.sohu.sur.dao.AllProductMedalStarsDao;
import com.sohu.sur.model.AllProductMedalStars;

public class AllProductMedalStarsDaoMorphiaImpl extends
		BasicDAO<AllProductMedalStars, ObjectId> implements
		AllProductMedalStarsDao {

	public AllProductMedalStarsDaoMorphiaImpl(Datastore ds) {
		super(ds);
	}

	@Override
	public void saveAllProductMedalStars(
			AllProductMedalStars allProductMedalStars) {
		super.save(allProductMedalStars);
	}

	@Override
	public AllProductMedalStars getLatestAllProductMedalStars() {
		return ds.createQuery(AllProductMedalStars.class).order("-$natural")
				.limit(1).get();
	}

}
