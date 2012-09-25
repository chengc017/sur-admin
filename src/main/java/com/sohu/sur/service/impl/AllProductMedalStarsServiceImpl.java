package com.sohu.sur.service.impl;

import com.sohu.sur.dao.AllProductMedalStarsDao;
import com.sohu.sur.model.AllProductMedalStars;
import com.sohu.sur.service.AllProductMedalStarsService;

public class AllProductMedalStarsServiceImpl implements
		AllProductMedalStarsService {

	public AllProductMedalStarsServiceImpl() {
	}

	public AllProductMedalStarsServiceImpl(
			AllProductMedalStarsDao allProductMedalStarsDao) {
		this.allProductMedalStarsDao = allProductMedalStarsDao;
	}

	@Override
	public AllProductMedalStars getLatestAllProductMedalStars() {
		return allProductMedalStarsDao.getLatestAllProductMedalStars();
	}

	@Override
	public void saveAllProductMedalStars(
			AllProductMedalStars allProductMedalStars) {
		allProductMedalStarsDao.saveAllProductMedalStars(allProductMedalStars);
	}

	private AllProductMedalStarsDao allProductMedalStarsDao;

	public AllProductMedalStarsDao getAllProductMedalStarsDao() {
		return allProductMedalStarsDao;
	}

	public void setAllProductMedalStarsDao(
			AllProductMedalStarsDao allProductMedalStarsDao) {
		this.allProductMedalStarsDao = allProductMedalStarsDao;
	}
}
