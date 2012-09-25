package com.sohu.sur.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.dao.AccountActivityDao;
import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.AllProductMedalStarsDao;
import com.sohu.sur.dao.MedalDao;
import com.sohu.sur.dao.ProductDao;
import com.sohu.sur.dao.RankDao;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountSummary;
import com.sohu.sur.model.AccountVirtual;
import com.sohu.sur.model.AllProductMedalStars;
import com.sohu.sur.model.DailyEarnedBonus;
import com.sohu.sur.model.DailyExp;
import com.sohu.sur.model.MallItem;
import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;
import com.sohu.sur.model.Rank;
import com.sohu.sur.model.User;
import com.sohu.sur.model.exception.AccountNotFoundException;
import com.sohu.sur.model.exception.InsufficientBonusException;
import com.sohu.sur.service.AccountService;
import com.sohu.sur.service.AccountVirtualService;
import com.sohu.sur.service.MallItemService;
import com.sohu.sur.util.AccountSummaryHelper;
import com.sohu.sur.util.Page;
import com.sohu.sur.util.po.UserMedal;
import com.sohu.sur.util.po.UserMedalRet;

/**
 * User: guoyong Date: 11-3-17 下午4:52
 */
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	private final AccountDao accountDao;
	private final AccountActivityDao accountActivityDao;
	private final MedalDao medalDao;
	private final RankDao rankDao;
	private final ProductDao productDao;
	private final AllProductMedalStarsDao allProductMedalStarsDao;
	private final MallItemService mallItemService;
	private final AccountVirtualService accountVirtualService;
	private final ObjectCache objectCache;
	public AccountServiceImpl(AccountDao accountDao, AccountActivityDao accountActivityDao, MedalDao medalDao,
			RankDao rankDao, ProductDao productDao, AllProductMedalStarsDao allProductMedalStarsDao,
			MallItemService mallItemService,AccountVirtualService accountVirtualService,ObjectCache objectCache) {

		this.accountDao = accountDao;
		this.accountActivityDao = accountActivityDao;
		this.medalDao = medalDao;
		this.rankDao = rankDao;
		this.productDao = productDao;
		this.allProductMedalStarsDao = allProductMedalStarsDao;
		this.mallItemService = mallItemService;
		this.accountVirtualService = accountVirtualService;
		this.objectCache = objectCache;
	}

	@Override
	public UserMedalRet batchGetUserMedals(List<Medal> allMedal, List<String> uids) {
		UserMedalRet umr = new UserMedalRet();
		List<UserMedal> listUserMedal = new ArrayList<UserMedal>();
		List<AccountActivity> listAccountActivity;
		UserMedal um;
		try {
			for (String uid : uids) {
				um = new UserMedal();
				listAccountActivity = accountActivityDao.findAccountActivities(uid);
				um.setUid(uid);
				um.setListMedal(AccountSummaryHelper.getEarnedMedals(allMedal, listAccountActivity));
				listUserMedal.add(um);
			}
			umr.setListUserMedal(listUserMedal);
			umr.setMsg("查询成功");
			umr.setCode("0");
		} catch (Exception e) {
			logger.error("batchGetUserMedals", e);
			umr.setMsg(e.getMessage());
			umr.setCode("5");
		}
		return umr;
	}
	@Override
	public UserMedalRet batchGetUserMedals(List<Medal> allMedal, String[] uids) {
		UserMedalRet umr = new UserMedalRet();
		List<UserMedal> listUserMedal = new ArrayList<UserMedal>();
		List<AccountActivity> listAccountActivity;
		UserMedal um;
		try {
			for (String uid : uids) {
				um = new UserMedal();
				listAccountActivity = getAccountActivities(uid);
				um.setUid(uid);
				um.setListMedal(AccountSummaryHelper.getEarnedMedals(allMedal, listAccountActivity));
				listUserMedal.add(um);
			}
			umr.setListUserMedal(listUserMedal);
			umr.setMsg("查询成功");
			umr.setCode("0");
		} catch (Exception e) {
			logger.error("batchGetUserMedals", e);
			umr.setMsg(e.getMessage());
			umr.setCode("5");
		}
		return umr;
	}
	
	/**
	 * 查找用户活跃度list
	 */
	public List<AccountActivity> getAccountActivities(String uid){
		
		List<AccountActivity> accountActivities=objectCache.getAccountActivity(uid);
		if(accountActivities==null||accountActivities.size()<1){
			accountActivities=accountActivityDao.findAccountActivities(uid);
			if(accountActivities!=null&&accountActivities.size()>0){
				objectCache.setAccountActivity(uid, accountActivities);
			}
			
		}
		return accountActivities;
	}
	@Override
	public Account findAccount(String uid) {
		return accountDao.findAccountByUid(uid);
	}

	/**
	 * 金币首页home.json  个人展示profile.json
	 */
	public AccountSummary getSummary2(String uid, List<Medal> allMedals, List<Rank> allRanks,
			List<Product> allProducts, AllProductMedalStars allProductMedalStars) throws AccountNotFoundException{

		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}
		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if (account == null) {
			account = findAccount(uid);
			if (account == null) {
				logger.warn("Account '{}' not found", uid);
				throw new AccountNotFoundException(uid);
			} else {
				objectCache.setAccount(uid, account);
			}
		}
		

		//先从memcache取活跃度
		List<AccountActivity> activities = objectCache.getAccountActivity(uid);
		 //不存在则从数据库取 set到memcache
		if(activities==null){
			activities = accountActivityDao.findAccountActivities(account);
			objectCache.setAccountActivity(uid, activities);
		}

		List<MallItem> mallItems = mallItemService.getAllMallItem();
		
		//先从memcache取
		List<AccountVirtual> lav = objectCache.getAccountVirtual(uid, 1);
		 //不存在则从数据库取 set到memcache
		if(lav==null){
			lav = accountVirtualService.virtualList(1, uid);
			objectCache.setAccountVirtual(uid, 1, lav);
		}
		
		return new AccountSummary(account, allRanks, allProducts, activities, allMedals, allProductMedalStars,
				mallItems,lav);
	}
	/**
	 * i.sohu.com首页
	 */
	public AccountSummary getSummary3(String uid, List<Medal> allMedals, List<Rank> allRanks,
			List<Product> allProducts) throws AccountNotFoundException{

		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}
		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if (account == null) {
			account = findAccount(uid);
			if (account == null) {
				logger.warn("Account '{}' not found", uid);
				throw new AccountNotFoundException(uid);
			} else {
				objectCache.setAccount(uid, account);
			}
		}

		
		//先从memcache取活跃度
		List<AccountActivity> activities = objectCache.getAccountActivity(uid);
		 //不存在则从数据库取 set到memcache
		if(activities==null){
			activities = accountActivityDao.findAccountActivities(account);
			objectCache.setAccountActivity(uid, activities);
		}
		
		//先从memcache取收到的礼物
		List<AccountVirtual> lav = objectCache.getAccountVirtual(uid, 1);
		 //不存在则从数据库取 set到memcache
		if(lav==null){
			lav = accountVirtualService.virtualList(1, uid);
			objectCache.setAccountVirtual(uid, 1, lav);
		}
		
		return new AccountSummary(account, allRanks, allProducts, activities, allMedals,lav);
	}
	@Override
	public AccountSummary getSummaryWithMedals(String uid) throws AccountNotFoundException {
		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}

		Account account = accountDao.findAccountByUid(uid);

		if (account == null) {
			logger.warn("Account '{}' not found", uid);
			throw new AccountNotFoundException(uid);
		}

		List<AccountActivity> activities = accountActivityDao.findAccountActivities(account);

		List<Medal> allMedals = medalDao.findAllMedals();
		List<Rank> allRanks = rankDao.findAllRanks();
		List<Product> allProducts = productDao.findAllEnabledProducts();
		return new AccountSummary(account, allRanks, allProducts, activities, allMedals,null);
	}

	@Override
	public int getAccountBonus(String uid) throws AccountNotFoundException {

		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}

		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if(account==null){
			account = findAccount(uid);
			//如果不存在  创建该用户
			if(account==null){
				account = Account.create(uid);
				accountDao.updateExp(account);
			}
			objectCache.setAccount(uid, account);
		}
		if (account == null) {
			logger.warn("Account '{}' not found", uid);
			throw new AccountNotFoundException(uid);
		}
		return account.getBonus();
	}

	@Override
	public boolean checkBonus(String uid, int bonus) throws AccountNotFoundException {

		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}
		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if(account==null){
			account = findAccount(uid);
			//如果不存在  创建该用户
			if(account==null){
				account = Account.create(uid);
				accountDao.updateExp(account);
			}
			objectCache.setAccount(uid, account);
		}
		if (account == null) {
			logger.warn("Account '{}' not found", uid);
			throw new AccountNotFoundException(uid);
		}
		return account.getBonus() >= bonus;
	}

	@Override
	public Medal checkBonusAndMedal(String uid, int bonus, String[] medalCodes,List<Medal> allMedals) throws AccountNotFoundException,
			InsufficientBonusException {

		if (StringUtils.trimToNull(uid) == null) {
			logger.warn("uid = {}", uid);
			throw new IllegalArgumentException("uid must not be null or empty string.");
		}

		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if(account==null){
			account = findAccount(uid);
			//如果不存在  创建该用户
			if(account==null){
				account = Account.create(uid);
				accountDao.updateExp(account);
			}
			objectCache.setAccount(uid, account);
		}
		if (account == null) {
			logger.warn("Account '{}' not found", uid);
			throw new AccountNotFoundException(uid);
		}

		//先从memcache取活跃度
		List<AccountActivity> activities = objectCache.getAccountActivity(uid);
		 //不存在则从数据库取 set到memcache
		if(activities==null){
			activities = accountActivityDao.findAccountActivities(account);
			objectCache.setAccountActivity(uid, activities);
		}

		List<Medal> earnedMedals = AccountSummaryHelper.getEarnedMedals(allMedals, activities);
		Medal ret = null;

		List<Medal> medalsToBeUsed = new LinkedList<Medal>();

		if (null != medalCodes && medalCodes.length > 0) {
			for (String code : medalCodes) {// 多个传入的medalCode之间是“或”关系
				for (Medal medal : earnedMedals) {
					String[] earnedMedalCodePair = medal.getCode().split("[_]");
					String[] neededMedalCodePair = code.split("[_]");
					if (earnedMedalCodePair.length == 2 && neededMedalCodePair.length == 2
							&& earnedMedalCodePair[0].equals(neededMedalCodePair[0])
							&& earnedMedalCodePair[1].compareTo(neededMedalCodePair[1]) >= 0) {
						medalsToBeUsed.add(medal);
					}
				}
			}
		} else {// 没有任何medalCode则用户所有的勋章都允许兑换
			for (Medal medal : earnedMedals) {
				medalsToBeUsed.add(medal);
			}
		}

		if (!medalsToBeUsed.isEmpty()) {

			ret = medalsToBeUsed.remove(0);

			// 找折扣最高的勋章
			for (Medal medal : medalsToBeUsed) {
				if (medal.getDiscount() < ret.getDiscount()) {
					ret = medal;
				}
			}

			// 折扣后的积分值必须小于等于用户当前的积分
			if (Math.floor(bonus * ret.getDiscount()) > account.getBonus()) {
				throw new InsufficientBonusException("积分不足");
			}
		} else if (null == medalCodes || medalCodes.length == 0) {// 没有任何medalCode且用户没有勋章，则返回code为1的勋章，标识允许兑换。
			ret = new Medal();
			ret.setCode("1");
			ret.setDiscount(1);

			// 折扣后的积分值必须小于等于用户当前的积分
			if (bonus * ret.getDiscount() > account.getBonus()) {
				throw new InsufficientBonusException("积分不足");
			}
		}

		return ret;
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByProduct(Product product, int limit) {
		int maxContActiveDays = accountActivityDao.findMaxAccountActivity(product);
		long count = accountActivityDao.countAccountActivitiesByProductAndActiveDays(product, maxContActiveDays);
		return accountActivityDao.findAccountActivitiesByProduct(product, count > limit ? (int) count : limit);
	}

	@Override
	public void createAccount(String uid) {
		Account account = Account.create(uid);
		accountDao.updateExp(account);
	}

	@Override
	public DailyEarnedBonus getDailyEarnedBonus(String uid) throws AccountNotFoundException {
		//先从cache查account
		Account account  = objectCache.getAccount(uid);
		if(account==null){
			account = findAccount(uid);
			//如果不存在  创建该用户
			if(account==null){
				account = Account.create(uid);
				accountDao.updateExp(account);
			}
			objectCache.setAccount(uid, account);
		}
		if (account == null) {
			logger.warn("Account '{}' not found", uid);
			throw new AccountNotFoundException(uid);
		} else {
			Date dateTmp = new Date();
			// 如果accounts表中当天获积分的最后更新时间和当前时间不是一天的话，更新时间为执行时间，并将当天获取积分设置为0
			if (!DateUtils.isSameDay(account.getBonusEarnedToday().getUpdateTime(), dateTmp)) {
				account.resetBonusEarnedToday();
			}
			// 只统计当天用户浏览浏览2012奥林匹克中国女篮专题行为次数
			DailyExp todayExp = account.findDailyExpByActionCode("2012Olympic-Basketball");
			// 如果当天有浏览行为 则取出当前次数（对于浏览一次加一分，所以浏览次数=积分数），反之为0
			if (null != todayExp) {
				if (DateUtils.isSameDay(todayExp.getUpdateTime(), dateTmp))
					account.getBonusEarnedToday().setViewNum(todayExp.getCurrentValue());
			}
			return account.getBonusEarnedToday();
		}
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByMedal(String medalCode, Page page) {
		Medal medal = medalDao.findMedalByCode(medalCode);
		if (medal != null) {
			return accountActivityDao.findAccountActivitiesByActiveDays(medal.getProductCode(),
					medal.getMinActiveDays(), medal.getMaxActiveDays(), page);
		} else {
			return null;
		}
	}

	@Override
	public List<AccountActivity> findAccountActivitiesByMedal(String medalCode) {
		Medal medal = medalDao.findMedalByCode(medalCode);
		if (medal != null) {
			return accountActivityDao.findAccountActivitiesByActiveDays(medal.getProductCode(),
					medal.getMinActiveDays(), medal.getMaxActiveDays());
		} else {
			return null;
		}
	}

	@Override
	public void banAccount(String uid) {
		accountDao.banAccount(uid);
	}

	@Override
	public void unBanAccount(String uid) {
		accountDao.unBanAccount(uid);
	}

	@Override
	public List<Account> findBannedAccounts(Page page) {
		return accountDao.findBannedAccount(page);
	}

	@Override
	public void validAccount(String uid, User user) {
		if (user == null) {
			accountDao.banAccount(uid);
			accountDao.validAccount(uid, false);
		} else {
			accountDao.validAccount(uid, true);
		}
	}

	@Override
	public List<Account> findUnValidateAccount(int pageNo, int pageSize) {
		pageNo = pageNo > 1 ? pageNo : 1;
		return accountDao.findUnValidateAccount((pageNo - 1) * pageSize, pageSize);
	}
}
