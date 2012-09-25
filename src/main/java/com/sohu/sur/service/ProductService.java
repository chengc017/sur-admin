package com.sohu.sur.service;

import com.sohu.sur.model.AccountTopNItem;
import com.sohu.sur.model.Product;
import com.sohu.sur.model.ProductWithMedals;

import java.util.List;

/**
 * User: guoyong
 * Date: 11-3-18 下午4:19
 */
public interface ProductService {

    /**
     * 获取全部产品
     * @return 产品列表
     */
    List<Product> findAllProducts();

    /**
     * 计算某产品的用户排行榜
     * @param productCode 产品代码
     * @param n top n
     * @return  用户排行列表，按活跃度倒序排列
     */
    List<AccountTopNItem> getAccountTopNList(String productCode, int n);

    /**
     * 获取全部在用的产品（包含勋章信息）
     * @return 产品列表（包含勋章信息）
     */
    List<ProductWithMedals> findAllEnabledProductsWithMedals();
    
    /**
     * 获取全部在用的产品
     * @return 产品列表
     */
    List<Product> findAllEnabledProducts();
}
