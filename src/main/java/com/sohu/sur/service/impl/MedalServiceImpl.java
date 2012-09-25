package com.sohu.sur.service.impl;

import com.sohu.sur.dao.MedalDao;
import com.sohu.sur.dao.ProductDao;
import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;
import com.sohu.sur.service.MedalService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: guoyong
 * Date: 11-3-18 下午2:36
 */
public class MedalServiceImpl implements MedalService {

    private final MedalDao medalDao;

    private final ProductDao productDao;

    public MedalServiceImpl(MedalDao medalDao, ProductDao productDao) {
        this.medalDao = medalDao;
        this.productDao = productDao;
    }

    @Override
    public List<Medal> findAllMedals() {
        return medalDao.findAllMedals();
    }

    @Override
    public List<Medal> findProductMedals(String productCode) {
        Product product = productDao.findProductByCode(productCode);
        if (product == null) {
            return new ArrayList<Medal>(0);
        }
        return medalDao.findProductMedals(product);
    }
}
