package com.sohu.sur.service.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.dao.AccountVirtualLogDao;
import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.service.AccountVirtualLogService;
import com.sohu.sur.util.Page;

/**
 * 虚拟商品接口实现类
 * 
 * @author xuewuhao
 * 
 */
public class AccountVirtualLogServiceImpl implements AccountVirtualLogService {
	private static final Logger logger = LoggerFactory.getLogger(AccountVirtualLogServiceImpl.class);
	private AccountVirtualLogDao accountVirtualLogDao;

	public AccountVirtualLogServiceImpl() {

	}

	public AccountVirtualLogServiceImpl(AccountVirtualLogDao accountVirtualLogDao) {
		this.accountVirtualLogDao = accountVirtualLogDao;
	}

	@Override
	public List<AccountVirtualLog> findAccountVirtualLogs(String uid,int actType, Page page) {
		// TODO Auto-generated method stub
		return this.accountVirtualLogDao.findAccountVirtualLogList(uid,actType, page);
	}

	public void romoveLog(String logId) {
		// TODO Auto-generated method stub
		this.accountVirtualLogDao.delById(new ObjectId(logId));
	}
	public AccountVirtualLog  findLog(String logId){
		AccountVirtualLog avl = this.accountVirtualLogDao.selectById(new ObjectId(logId));
		return avl;
	}

	@Override
	public List<AccountVirtualLog> findLogByDate(Date s, Date e) {
		// TODO Auto-generated method stub
		return this.accountVirtualLogDao.findAccountVirtualLogByDate(s, e);
	}

	@Override
	public List<AccountVirtualLog> findAccountVirtualLogsAll(String uid, int actType) {
		// TODO Auto-generated method stub
		return this.accountVirtualLogDao.findAccountVirtualLogAll(uid, actType);
	}
}
