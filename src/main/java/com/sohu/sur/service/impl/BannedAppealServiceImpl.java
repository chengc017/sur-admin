package com.sohu.sur.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.BannedAppealDao;
import com.sohu.sur.model.BannedAppeal;
import com.sohu.sur.service.BannedAppealService;
import com.sohu.sur.util.Page;

public class BannedAppealServiceImpl implements BannedAppealService{
	
	private BannedAppealDao bannedAppealDao;
	private AccountDao accountDao;
	
	public BannedAppealServiceImpl(BannedAppealDao bannedAppealDao,AccountDao accountDao){
		this.bannedAppealDao = bannedAppealDao;
		this.accountDao = accountDao;
	}

	@Override
	public List<BannedAppeal> findBannedAppeals(boolean isOverrule, Page page) {
		return getBannedAppealDao().findBannedAppeals(isOverrule, page);
	}

	@Override
	public BannedAppeal findBannedAppealByUid(String uid) {
		if(StringUtils.isEmpty(uid)){
			return null;
		}
		return getBannedAppealDao().findBannedAppealByUid(uid);
	}

	@Override
	public boolean saveOrUpdateBannedAppeal(BannedAppeal bannedAppeal) {
		if(isAppealNullOrUidNull(bannedAppeal)){
			 throw new IllegalArgumentException("bannedAppeal arg must not be null,please check.");
		}
		return getBannedAppealDao().saveOrUpdateBannedAppeal(bannedAppeal);
	}

	@Override
	public boolean deleteBannedAppeal(BannedAppeal bannedAppeal) {
		if(isAppealNullOrUidNull(bannedAppeal)){
			 throw new IllegalArgumentException("bannedAppeal arg must not be null,please check.");
		}
		return getBannedAppealDao().deleteBannedAppeal(bannedAppeal);
	}
	
	public boolean isAppealNullOrUidNull(BannedAppeal bannedAppeal){
		if(bannedAppeal == null || StringUtils.isEmpty(bannedAppeal.getUid())){
			return true;
		}
		return false;
	}

	@Override
	public void unBanAccount(String uid) {
		accountDao.unBanAccount(uid);
	}
	
	/*****************getter and setter*****************/
	public BannedAppealDao getBannedAppealDao() {
		return bannedAppealDao;
	}

	public void setBannedAppealDao(BannedAppealDao bannedAppealDao) {
		this.bannedAppealDao = bannedAppealDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	

}
