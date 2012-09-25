package com.sohu.sur.service;

import com.sohu.sur.model.AllProductMedalStars;

public interface AllProductMedalStarsService {
	/**
	 * 保存一条名人堂数据
	 * @param allProductMedalStars
	 */
	void saveAllProductMedalStars(
			AllProductMedalStars allProductMedalStars);

	/**
	 * 获取最新的一条名人堂数据
	 * @return
	 */
	AllProductMedalStars getLatestAllProductMedalStars();
}
