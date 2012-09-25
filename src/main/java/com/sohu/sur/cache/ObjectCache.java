package com.sohu.sur.cache;

import java.util.List;

import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountSummary;
import com.sohu.sur.model.AccountVirtual;
import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.model.MallLog;
import com.sohu.sur.util.po.UserMedal;

/**
 * memcache对象
 * 
 * @author xuewuhao
 * 
 */
public interface ObjectCache {

	public void setAccountSummaryValue(String key, AccountSummary as);

	public AccountSummary getAccountSummaryValue(String key);

	/**
	 * set吐给toolbar的文本信息
	 * 
	 * @param key
	 * @param str
	 * @return
	 */
	public void setToolbarStr(String key, String str);

	/**
	 * 获取toolbar的文本信息
	 * 
	 * @param key
	 * @param str
	 * @return
	 */
	public String getToolbarStr(String key);
	
	
	/**
	 * get当天调宝中奖记录数
	 * @param key
	 * @return
	 */
	public String getCurMaxHuojiangNum(String key);
	/**
	 * set当天调宝中奖记录数
	 * @param key
	 * @param value
	 * @return
	 */
	public void  setCurMaxHuojiangNum(String key,String value);
	
	/**
	 * 用户礼物set
	 * @param key
	 * @param actType 0可赠送 1收到 2送出
	 * @param lav
	 */
	public void setAccountVirtual(String key,int actType, List<AccountVirtual> lav);

	/**
	 * 用户礼物get
	 * @param key
	 * @param actType 0可赠送 1收到 2送出
	 * @return
	 */
	public List<AccountVirtual> getAccountVirtual(String key,int actType);

	
	/**
	 * set最近收到的礼物
	 * @param key pspt
	 * @param lavl
	 */
	public void setLastReceiveVirtual(String key, List<AccountVirtualLog> lavl);

	/**
	 * get最近收到的礼物
	 * @param key  pspt
	 * @return
	 */
	public List<AccountVirtualLog> getLastReceiveVirtual(String key);
	
	/**
	 * set收到/送出的礼物list
	 * @param key
	 * @param actType 1收到 2送出
	 * @param lavl
	 */
	public void setVirtualLogs(String key,int actType, List<AccountVirtualLog> lavl);

	/**
	 * get收到/送出的礼物list 1收到 2送出
	 * @param key
	 * @param actType
	 * @return
	 */
	public List<AccountVirtualLog> getVirtualLogs(String key,int actType);
	
	/**
	 * 用户活跃度set
	 * @param key
	 * @param listAccountActivity
	 */
	public void setAccountActivity(String key, List<AccountActivity> listAccountActivity);

	/**
	 * 用户活跃度get
	 * @param key
	 * @return
	 */
	public List<AccountActivity> getAccountActivity(String key);
	
	/**
	 * set用户信息
	 * @param key
	 * @param account
	 */
	public void setAccount(String key,Account account);
	/**
	 * get用户信息
	 * @param key
	 * @return
	 */
	public Account getAccount(String key);
	
	
	/**
	 * set用户商城兑换、抽奖信息
	 * @param key
	 * @param giftType 2代表虚拟商品   null代表全部
	 * @param listMallLog
	 */
	public void setListMallLog(String key,Integer giftType,List<MallLog> listMallLog);
	/**
	 * get用户商城兑换、抽奖信息
	 * @param key
	 * @param giftType 2代表虚拟商品   null代表全部
	 * @return
	 */
	public List<MallLog>  getListMallLog(String key,Integer giftType);
	/**
     * set用户在某频道已更改了活跃度，并设置有效期
     * @param uid
     * @param productCode
     */
    void  setActivityTime(String uid,String productCode);
    /**
     * 判断用户在某频道是否已更改了活跃度
     * @param uid
     * @param productCode
     * @return true已更改  反之
     */
    boolean getActivityTime(String uid,String productCode);
}
