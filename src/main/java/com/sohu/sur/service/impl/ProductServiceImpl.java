package com.sohu.sur.service.impl;

import com.sohu.sur.dao.AccountActivityDao;
import com.sohu.sur.dao.MedalDao;
import com.sohu.sur.dao.ProductDao;
import com.sohu.sur.model.*;
import com.sohu.sur.service.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: guoyong Date: 11-3-18 下午4:32
 */
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;

	private final MedalDao medalDao;

	private final AccountActivityDao accountActivityDao;

	public ProductServiceImpl(ProductDao productDao, MedalDao medalDao,
			AccountActivityDao accountActivityDao) {
		this.productDao = productDao;
		this.medalDao = medalDao;
		this.accountActivityDao = accountActivityDao;
	}

	@Override
	public List<Product> findAllProducts() {

		return productDao.findAllProducts();

	}

	@Override
	public List<Product> findAllEnabledProducts() {
		
		return productDao.findAllEnabledProducts();
	}

	@Override
	public List<AccountTopNItem> getAccountTopNList(String productCode, int n) {

		Product product = productDao.findProductByCode(productCode);

		List<Medal> allMedals = medalDao.findAllMedals();

		List<AccountActivity> accountActivities = accountActivityDao
				.findAccountActivitiesByProduct(product, n);

		List<AccountTopNItem> topNList = new ArrayList<AccountTopNItem>(
				accountActivities.size());

		for (AccountActivity activity : accountActivities) {

			topNList.add(new AccountTopNItem(activity, allMedals));
		}

		return topNList;
	}

	@Override
	public List<ProductWithMedals> findAllEnabledProductsWithMedals() {

		List<ProductWithMedals> ret = new ArrayList<ProductWithMedals>();
		List<Product> products = productDao.findAllEnabledProducts();
		for (Product product : products) {
			List<Medal> medalList = medalDao.findProductMedals(product);
			ret.add(new ProductWithMedals(product, medalList));
		}
		return ret;
	}
}
