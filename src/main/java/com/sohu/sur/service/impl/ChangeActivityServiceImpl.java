package com.sohu.sur.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.concurrent.LockManager;
import com.sohu.sur.dao.AccountActivityDao;
import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.Product;
import com.sohu.sur.service.ChangeActivityService;
import com.sohu.sur.service.ChangeActivityServiceResult;

/**
 * User: 郭勇 Date: 2011-3-15 11:18:04
 */
public class ChangeActivityServiceImpl implements ChangeActivityService {

    private static Logger logger = LoggerFactory
            .getLogger(ChangeActivityServiceImpl.class);

    private final AccountDao accountDao;
    private final AccountActivityDao accountActivityDao;
    private final LockManager lockManager;
    private final ObjectCache objectCache;
    
    public ChangeActivityServiceImpl(AccountDao accountDao, AccountActivityDao accountActivityDao,
                                     LockManager lockManager,ObjectCache objectCache) {
        this.accountDao = accountDao;
        this.accountActivityDao = accountActivityDao;
        this.lockManager = lockManager;
        this.objectCache = objectCache;
    }

    @Override
	public ChangeActivityServiceResult changeActivity(String uid, Product product) {
		if (logger.isDebugEnabled())
			logger.debug(">>> changeActivity");
		if (product == null) {
			return ChangeActivityServiceResult.NO_SUCH_PRODUCT;
		}
		if (!product.isEnabled()) {
			return ChangeActivityServiceResult.DISABLED_PRODUCT;
		}
		// 先从cache查account
		Account account = objectCache.getAccount(uid);
		if (account == null) {
			account = accountDao.findAccountByUid(uid);
			// 如果不存在 创建该用户
			if (account == null) {
				account = Account.create(uid);
				accountDao.updateExp(account);
			}
			objectCache.setAccount(uid, account);
		}
		if (account != null) {
			if (account.isBanned()) {
				return ChangeActivityServiceResult.BANNED_ACCOUNT;
			}
		}

		String lockKey = new StringBuffer(uid).append("-").append(product.getCode()).toString();
		Lock lock = lockManager.getLock(lockKey);
		lock.lock();
		try {

			// 查用户 对应产品的活跃度记录
			AccountActivity activity = userProductActivity(account,product,uid);

			// 增加productCode字段 2011-04-25
			activity.setProductCode(product.getCode());

			Days d = Days.daysBetween(new DateTime(activity.getLatestActiveDate()).toDateMidnight(), new DateTime(
					new Date()).toDateMidnight());
			if (d.getDays() > 1) {
				minusAndPlus(product, activity,d.getDays());
			} else if (d.getDays() == 1) {
				plusActivity(product, activity);
			} else if (d.getDays() <= 0) {
				return ChangeActivityServiceResult.ALREADY_UPDATED;
			}
		} catch (RuntimeException e) {
			logger.error("changeActivity failed", e);
			return ChangeActivityServiceResult.FAILED;
		} finally {
			lock.unlock();
		}
		return ChangeActivityServiceResult.OK;
	}

    @Override
    public void minusActivity(Product product, AccountActivity activity) {
        Calendar targetCalendar = getLastDay();
        minusActivity(product, activity, targetCalendar.getTime());
    }

    @Override
    public void minusActivity(Product product, AccountActivity activity,Date date) {
		Days d = Days.daysBetween(new DateTime(activity.getLatestActiveDate()).toDateMidnight(),
				new DateTime(date).toDateMidnight());
        int inactiveDays = d.getDays();
        if (inactiveDays > 0) {
            String uid = activity.getUid();
            int oldContActiveDays = activity.getContActiveDays();
            int newContActiveDays = oldContActiveDays - inactiveDays;
            newContActiveDays = newContActiveDays < 0 ? 0 : newContActiveDays;
            activity.setContActiveDays(newContActiveDays);
            activity.setLatestActiveDate(date);
            int changedValue = newContActiveDays - oldContActiveDays;
            logger.info("minusActivityflag product={} uid={} inactive={} oldActive={} newActive={} changed={}",
                    new Object[]{product.getCode(), uid, inactiveDays, oldContActiveDays, newContActiveDays, changedValue});
            accountActivityDao.saveOrUpdateActivity(activity);
            //变更完后 将该用户的活跃度放到memcache 
            List<AccountActivity> listAccountActivity = accountActivityDao.findAccountActivitiesByUid(uid);
            objectCache.setAccountActivity(uid, listAccountActivity);
        }
    }

	public void plusActivity(Product product, AccountActivity activity) {
		int changedValue = 1;
		int oldContActiveDays = activity.getContActiveDays();
		int newContActiveDays = oldContActiveDays + changedValue;
		activity.setContActiveDays(oldContActiveDays + changedValue);
		activity.setLatestActiveDate(new Date());
		if (logger.isDebugEnabled())
			logger.debug("product={} uid={} oldActive={} newActive={} changed={}", new Object[] { product.getCode(),
					activity.getUid(), oldContActiveDays, newContActiveDays, changedValue });
		accountActivityDao.saveOrUpdateActivity(activity);

		// 变更完后 将该用户的活跃度放到memcache
		List<AccountActivity> listAccountActivity = accountActivityDao.findAccountActivitiesByUid(activity.getUid());
		objectCache.setAccountActivity(activity.getUid(), listAccountActivity);
		//memcache写入已变更标记
		objectCache.setActivityTime(activity.getUid(), product.getCode());
		// 不写入数据库改为打印log
		logger.info(
				"plusActivityflag product={} uid={}  oldActive={} newActive={} changed={}",
				new Object[] { product.getCode(), activity.getUid(), oldContActiveDays, newContActiveDays, changedValue });
	}

	
	public void minusAndPlus(Product product, AccountActivity activity, int interval) {
		int oldContActiveDays = activity.getContActiveDays();
		int newContActiveDays = oldContActiveDays - interval >= 0 ? oldContActiveDays - interval + 1 : 1;
		activity.setContActiveDays(newContActiveDays);
		activity.setLatestActiveDate(new Date());
		accountActivityDao.saveOrUpdateActivity(activity);
		// 变更完后 将该用户的活跃度放到memcache
		List<AccountActivity> listAccountActivity = accountActivityDao.findAccountActivitiesByUid(activity.getUid());
		objectCache.setAccountActivity(activity.getUid(), listAccountActivity);

		// memcache写入已变更标记
		objectCache.setActivityTime(activity.getUid(), product.getCode());
		// 不写入数据库改为打印log
		logger.info("plusActivityflag product={} uid={}  oldActive={} newActive={}", new Object[] { product.getCode(),
				activity.getUid(), oldContActiveDays, newContActiveDays });
	}
    @Override
	public void minusActivityForNoLoginUsers(Product product) {
		int sum = 0;
		int limit = 5000;
		int size = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date yesterday = calendar.getTime();
		List<AccountActivity> accountActivities = null;
		do {
			accountActivities = accountActivityDao.findAccountActivitiesUpdateBeforeDate(product, yesterday, 0, limit);
			size = accountActivities.size();
			if (logger.isDebugEnabled())
				logger.debug("Find {} activities last activity day before {}", new Object[] { size, yesterday });
			for (AccountActivity accountActivity : accountActivities) {
				try {
					minusActivity(product, accountActivity);
				} catch (RuntimeException e) {
					logger.warn("Minus activity [{}-{}] failure. Can't handle RuntimeException:{}", new Object[] {
							product.getCode(), accountActivity.getUid(), e });
				}
			}

			sum += size;
		} while (size >= limit);
		logger.info("Minus activity for {} {} users  no login after {}.", new Object[] { sum, product.getCode(),
				yesterday });
	}
  
    /**
     * 查用户  对应产品的活跃度记录
     * @param account
     * @param product
     * @param uid
     * @return
     */
	public AccountActivity userProductActivity(Account account,Product product, String uid) {

		AccountActivity activity = null;
		// 1 从cache取
		List<AccountActivity> listAccountActivityCache = objectCache.getAccountActivity(uid);
		// 2 cache不存在
		if (listAccountActivityCache == null || listAccountActivityCache.size() == 0) {
			// 3 从db取
			listAccountActivityCache = accountActivityDao.findAccountActivitiesByUid(uid);
			// db不存在 新建该产品对应的活跃度记录
			if (listAccountActivityCache == null || listAccountActivityCache.size() == 0) {
				activity = AccountActivity.create(account, product);
			}
			// 从集合取对应记录
			else {
				for (AccountActivity temp : listAccountActivityCache) {
					if (temp.getProductId().equals(product.getId())) {
						activity = temp;
						break;
					}
				}
				// 如果集合里没有这条记录 那么新建
				if (activity == null) {
					activity = AccountActivity.create(account, product);
				}
			}
		}
		// cache存在
		else {
			for (AccountActivity temp : listAccountActivityCache) {
				if (temp.getProductId().equals(product.getId())) {
					activity = temp;
					break;
				}
			}
			// 如果集合里没有这条记录 那么新建
			if (activity == null) {
				activity = AccountActivity.create(account, product);
			}
		}
		return activity;
	}
	/**
	 * get昨天23:59:59 999时间
	 * @return
	 */
	public  Calendar getLastDay(){
		Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.add(Calendar.DAY_OF_MONTH, -1);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 23);
        targetCalendar.set(Calendar.MINUTE, 59);
        targetCalendar.set(Calendar.SECOND, 59);
        targetCalendar.set(Calendar.MILLISECOND, 999);
        return targetCalendar;
	}
}
