package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.MallItem;

/**
 * @author 韩孝冰
 * 商品信息
 */
public interface MallItemService {
	/**
	 * 根据商品所需勋章代码获得商品信息
	 * @param medalCode
	 * @return
	 */
	List<MallItem> getMallItemByMedalCode(String medalCode);
	
	/**
	 * 获取全部商品信息
	 * @return
	 */
	List<MallItem> getAllMallItem();
	
}
