package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;

/**
 * User: guoyong
 * Date: 11-3-17 下午4:56
 */
public interface MedalDao extends ManagedEntityDao<Medal, ObjectId>{

    /**
     * 查找系统内设置的所有勋章
     * @return 勋章列表
     */
    List<Medal> findAllMedals();

    /**
     * 根据产品代码查找它的所有勋章
     *
     * @param product  产品
     * @return 勋章列表
     */
    List<Medal> findProductMedals(Product product);
    
    /**
     * 根据名称查找勋章
     * @param name
     * @return
     */
    Medal findMedalByName(String name);
    
    /**
     * 根据编号查找勋章
     * @param code
     * @return
     */
    Medal findMedalByCode(String code);
}
