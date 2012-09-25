package com.sohu.sur.cache.impl;

import java.util.List;

import net.rubyeye.xmemcached.XMemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountSummary;
import com.sohu.sur.model.AccountVirtual;
import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.model.MallLog;
import com.sohu.sur.util.DateUtils;
import com.sohu.sur.util.po.UserMedal;

public class ObjectCacheImpl implements ObjectCache {
	private final static Logger logger = LoggerFactory.getLogger(ObjectCacheImpl.class);
	private final XMemcachedClient memcachedClient;

	public ObjectCacheImpl(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public void setAccountSummaryValue(String uid, AccountSummary as) {
		// TODO Auto-generated method stub
		String key = DigestUtils.sha256Hex(uid);
		int expires = DateUtils.calculateSecondsToLive();
		try {
			memcachedClient.add(key, expires, as);
		} catch (Exception e) {
			logger.error("error occured when adding AccountSummary to memcached", e);
		}

	}

	@Override
	public AccountSummary getAccountSummaryValue(String uid) {
		// TODO Auto-generated method stub
		AccountSummary as = null;
		String key = DigestUtils.sha256Hex(uid);
		try {
			Object obj = memcachedClient.get(key);
			if (obj != null) {
				as = (AccountSummary) obj;
			} else {
				logger.info("not exist " + key);
			}

		} catch (Exception e) {
			logger.error("error occured when adding AccountSummary to memcached", e);
		}
		return as;
	}

	@Override
	public void setToolbarStr(String key, String str) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex(key);
		try {
			// 默认过期时间为3个小时
			memcachedClient.add(keyTmp, 3 * 60 * 60, str);
		} catch (Exception e) {
			logger.error("error occured when setToolbarStr to memcached", e);
		}
	}

	@Override
	public String getToolbarStr(String key) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex(key);
		String str = "";
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				str = obj.toString();
			} else {
				logger.info("not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("error occured when getToolbarStr to memcached", e);
		}
		return str;
	}

	@Override
	public String getCurMaxHuojiangNum(String key) {
		String rest = "";
		String keyTemp = DigestUtils.sha256Hex(key);
		try {
			Object obj = memcachedClient.get(keyTemp);
			if (obj != null) {
				rest = obj.toString();
			} else {
				logger.warn("not exist " + keyTemp);
			}

		} catch (Exception e) {
			logger.error("error occured when getCurMaxHuojiangNum to memcached", e);
		}
		return rest;
	}

	@Override
	public void setCurMaxHuojiangNum(String key, String value) {
		String keyTemp = DigestUtils.sha256Hex(key);
		int expires = DateUtils.calculateSecondsToLive();
		try {
			memcachedClient.add(keyTemp, expires, value);
		} catch (Exception e) {
			logger.error("error occured when setCurMaxHuojiangNum to memcached", e);
		}
		
	}

	@Override
	public void setAccountVirtual(String key,int actType, List<AccountVirtual> lav) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("accountvirtual_" +actType+"_"+ key);
		try {
			// 永久有效
			memcachedClient.set(keyTmp, 0, lav);
		} catch (Exception e) {
			logger.error("setAccountVirtual", e);
		}

	}

	@Override
	public List<AccountVirtual> getAccountVirtual(String key,int actType) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("accountvirtual_" +actType+"_"+ key);
		List<AccountVirtual> lav = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				lav = (List<AccountVirtual>) obj;
			} else {
				logger.warn("accountvirtual_" +actType+"_"+ key + " not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("getAccountVirtual", e);
		}
		return lav;
	}

	@Override
	public void setLastReceiveVirtual(String key, List<AccountVirtualLog> lavl) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("lastreceivevirtual_"+ key);
		try {
			// 永久有效
			memcachedClient.set(keyTmp, 0, lavl);
		} catch (Exception e) {
			logger.error("setLastReceiveVirtual", e);
		}

	}

	@Override
	public List<AccountVirtualLog> getLastReceiveVirtual(String key) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("lastreceivevirtual_"+ key);
		List<AccountVirtualLog> lavl = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				lavl = (List<AccountVirtualLog>) obj;
			} else {
				logger.warn("lastreceivevirtual_"+ key + " not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("getLastReceiveVirtual", e);
		}
		return lavl;
	}

	@Override
	public void setAccountActivity(String key, List<AccountActivity> listAccountActivity) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("account_activity_"+ key);
		try {
			// 永久cache
			memcachedClient.set(keyTmp, 0, listAccountActivity);
		} catch (Exception e) {
			logger.error("setAccountActivity", e);
		}
		
	}

	@Override
	public List<AccountActivity> getAccountActivity(String key) {
		String keyTmp = DigestUtils.sha256Hex("account_activity_"+ key);
		List<AccountActivity> lavl = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				lavl = (List<AccountActivity>) obj;
			} else {
				logger.warn("account_activity_"+ key + " not exist " + keyTmp);
			}
		} catch (Exception e) {
			logger.error("getAccountActivity", e);
		}
		return lavl;
	}

	@Override
	public void setAccount(String key, Account account) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("account_"+key);
		try {
			// cache不自动过期
			memcachedClient.set(keyTmp, 0, account);
		} catch (Exception e) {
			logger.error("error occured when setAccount to memcached", e);
		}
		
	}

	@Override
	public Account getAccount(String key) {
		// TODO Auto-generated method stub
		String keyTmp = DigestUtils.sha256Hex("account_"+key);
		Account acct = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				acct = (Account)obj;
			} else {
				logger.info("not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("error occured when getAccount from memcached", e);
		}
		return acct;
	}

	@Override
	public void setListMallLog(String key, Integer giftType, List<MallLog> listMallLog) {
		String keyTmp = "";
		if(giftType==null){
			keyTmp = DigestUtils.sha256Hex("malllog_"+key);
		}else if(giftType.intValue()==2){
			keyTmp = DigestUtils.sha256Hex("malllog_virtual_"+key);
		}
		try {
			// cache不自动过期
			memcachedClient.set(keyTmp, 0, listMallLog);
		} catch (Exception e) {
			logger.error("error occured when setListMallLog to memcached", e);
		}
		
	}

	@Override
	public List<MallLog> getListMallLog(String key, Integer giftType) {
		// TODO Auto-generated method stub
		String keyTmp = "";
		if(giftType==null){
			keyTmp = DigestUtils.sha256Hex("malllog_"+key);
		}else if(giftType.intValue()==2){
			keyTmp = DigestUtils.sha256Hex("malllog_virtual_"+key);
		}
		List<MallLog> listMallLog = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				listMallLog = (List<MallLog>)obj;
			} else {
				logger.info("not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("error occured when getListMallLog from memcached", e);
		}
		return listMallLog;
	}

	@Override
	public void setVirtualLogs(String key, int actType, List<AccountVirtualLog> lavl) {
		key = "account_virtual_log_" +actType+"_"+ key;
		String keyTmp = DigestUtils.sha256Hex(key);
		try {
			// 永久有效
			memcachedClient.set(keyTmp, 0, lavl);
		} catch (Exception e) {
			logger.error("setVirtualLogs", e);
		}

		
	}

	@Override
	public List<AccountVirtualLog> getVirtualLogs(String key, int actType) {
		key = "account_virtual_log_" +actType+"_"+ key;
		String keyTmp = DigestUtils.sha256Hex(key);
		List<AccountVirtualLog> lavl = null;
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				lavl = (List<AccountVirtualLog>) obj;
			} else {
				logger.warn(key + " not exist " + keyTmp);
			}

		} catch (Exception e) {
			logger.error("getVirtualLogs", e);
		}
		return lavl;
	
	}

	@Override
	public void setActivityTime(String uid, String productCode) {
		StringBuffer key = new StringBuffer(); 
		key.append("activity_").append(uid).append("_").append(productCode);
		String keyTmp = DigestUtils.sha256Hex(key.toString());
		try {
			memcachedClient.set(keyTmp, DateUtils.liveSeconds().intValue(), true);
		} catch (Exception e) {
			logger.error("setActivityTime", e);
		}
	}

	@Override
	public boolean getActivityTime(String uid, String productCode) {
		boolean flag = false;
		StringBuffer key = new StringBuffer(); 
		key.append("activity_").append(uid).append("_").append(productCode);
		String keyTmp = DigestUtils.sha256Hex(key.toString());
		try {
			Object obj = memcachedClient.get(keyTmp);
			if (obj != null) {
				flag = (Boolean) obj;
			} else {
				logger.warn(key.toString() + " not exist " + keyTmp);
			}
		} catch (Exception e) {
			logger.error("getActivityTime", e);
		}
		return flag;
	}

}
