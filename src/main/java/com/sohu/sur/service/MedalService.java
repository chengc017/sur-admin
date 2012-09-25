package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.Medal;

/**
 * User: guoyong
 * Date: 11-3-18 上午9:25
 */
public interface MedalService {

    /**
     * 查找系统内设置的所有勋章
     * @return 勋章列表
     */
    List<Medal> findAllMedals();

    /**
     * 根据产品代码查找它的所有勋章
     * @param productCode  产品代码
     * @return 勋章列表
     */
    List<Medal> findProductMedals(String productCode);

}
