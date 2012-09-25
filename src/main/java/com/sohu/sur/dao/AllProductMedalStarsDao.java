package com.sohu.sur.dao;

import com.sohu.sur.model.AllProductMedalStars;

public interface AllProductMedalStarsDao {
	/**
	 * 保存一条名人堂数据
	 * @param productMedalStarsList
	 */
	void saveAllProductMedalStars(
			AllProductMedalStars allProductMedalStars);

	/**
	 * 获取最新的一条名人堂数据
	 * @return
	 */
	AllProductMedalStars getLatestAllProductMedalStars();
}
