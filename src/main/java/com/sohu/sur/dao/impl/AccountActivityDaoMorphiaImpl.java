package com.sohu.sur.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.sohu.sur.dao.AccountActivityDao;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.Product;
import com.sohu.sur.util.Page;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * User: 郭勇 Date: 2011-3-15 15:52:49
 */
public class AccountActivityDaoMorphiaImpl extends BasicDAO<AccountActivity, ObjectId> implements AccountActivityDao {

	@Override
	public List<AccountActivity> findAccountActivities(String uid) {
		Query<AccountActivity> q = this.createQuery().field("uid").equal(uid).field("cont_active_days")
				.greaterThanOrEq(3);
		return this.find(q).asList();
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByUid(String uid) {
		Query<AccountActivity> q = this.createQuery().field("uid").equal(uid);
		return this.find(q).asList();
	}

	
	public AccountActivityDaoMorphiaImpl(Datastore datastore) {
		super(datastore);
	}

	@Override
	public AccountActivity findAccountActivity(Account account, Product product) {
		Query<AccountActivity> q = this.createQuery().field("user_id").equal(account.getId()).field("in_product")
				.equal(product.getId());
		return this.findOne(q);
	}

	@Override
	public void saveOrUpdateActivity(AccountActivity activity) {
		this.save(activity);
	}

	@Override
	public List<AccountActivity> findAccountActivities(Account account) {

		Query<AccountActivity> q = this.createQuery().field("user_id").equal(account.getId());
		return this.find(q).asList();
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByProduct(Product product, int limit) {

		Query<AccountActivity> q = this.createQuery().field("in_product").equal(product.getId())
				.order("-cont_active_days").limit(limit);

		return find(q).asList();
	}

	@Override
	public List<AccountActivity> findAccountActivitiesUpdateBeforeDate(Product product, Date date, int start, int limit) {
		Query<AccountActivity> q = this.createQuery().field("in_product").equal(product.getId())
				.field("latest_active_date").lessThan(date).field("cont_active_days").greaterThan(0).offset(start)
				.limit(limit);
		return find(q).asList();
	}

	@Override
	public long countAccountActivitiesByProductAndActiveDays(Product product, int contActiveDays) {
		Query<AccountActivity> q = this.createQuery().field("in_product").equal(product.getId())
				.field("cont_active_days").equal(contActiveDays);
		return count(q);
	}

	@Override
	public int findMaxAccountActivity(Product product) {
		Query<AccountActivity> q = this.createQuery().field("in_product").equal(product.getId())
				.order("-cont_active_days").limit(1);
		List<AccountActivity> accountActivities = find(q).asList();
		if (accountActivities != null && accountActivities.size() > 0) {
			return accountActivities.get(0).getContActiveDays();
		} else {
			return 0;
		}
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByActiveDays(String productCode, int minActiveDays,
			int maxActiveDays, Page page) {
		Query<AccountActivity> q = this.createQuery().field("productCode").equal(productCode).field("cont_active_days")
				.greaterThanOrEq(minActiveDays).field("cont_active_days").lessThanOrEq(maxActiveDays);
		page.setCount(q.countAll());
		q.order("-cont_active_days").offset(page.getStart()).limit(page.getSize());
		return find(q).asList();
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByActiveDays(String productCode, int minActiveDays,
			int maxActiveDays) {
		Query<AccountActivity> q = this.createQuery().field("productCode").equal(productCode).field("cont_active_days")
				.greaterThanOrEq(minActiveDays).field("cont_active_days").lessThanOrEq(maxActiveDays)
				.order("-cont_active_days");
		return find(q).asList();
	}
}
