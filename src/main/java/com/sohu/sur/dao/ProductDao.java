package com.sohu.sur.dao;

import com.sohu.sur.model.Product;

import java.util.List;

import org.bson.types.ObjectId;

/**
 * User: 郭勇
 * Date: 2011-3-15 11:24:10
 */
public interface ProductDao extends ManagedEntityDao<Product, ObjectId>{

    /**
     * 根据产品代码获取产品
     * @param productCode 产品代码
     * @return 产品实例，找不到时返回null
     */
    Product findProductByCode(String productCode);

    /**
     * 获取全部产品
     * @return 产品列表
     */
    List<Product> findAllProducts();
    
    /**
     * 根据产品名称获取产品
     * @param productName
     * @return
     */
    Product findProductByName(String productName);

    /**
     * 获取全部在用的产品
     * @return 产品列表
     */
    List<Product> findAllEnabledProducts();
}
