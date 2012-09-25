package com.sohu.sur.service.impl;

import java.util.Date;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.concurrent.LockManager;
import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.ActionDao;
import com.sohu.sur.dao.BonusChangeLogDao;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.Action;
import com.sohu.sur.model.BonusCalculationMethod;
import com.sohu.sur.model.BonusChangeLog;
import com.sohu.sur.model.DailyEarnedBonus;
import com.sohu.sur.model.DailyExp;
import com.sohu.sur.model.exception.InsufficientBonusException;
import com.sohu.sur.service.ChangeBonusService;
import com.sohu.sur.service.ChangeBonusServiceResult;
import com.sohu.sur.util.SignUtils;

/**
 * User: 郭勇
 * Date: 2011-3-8 17:03:46
 */
public class ChangeBonusServiceImpl implements ChangeBonusService {

    private static Logger logger = LoggerFactory.getLogger(ChangeBonusServiceImpl.class);

    private final AccountDao accountDao;

    private final ActionDao actionDao;

    private final BonusChangeLogDao bonusChangeLogDao;

    private final LockManager lockManager;
    
    /**
     * 加入cache
     */
    private final ObjectCache objectCache;

    public ChangeBonusServiceImpl(AccountDao accountDao,
                                  ActionDao actionDao,
                                  BonusChangeLogDao bonusChangeLogDao,
                                  LockManager lockManager,
                                  ObjectCache objectCache) {
        this.accountDao = accountDao;
        this.actionDao = actionDao;
        this.bonusChangeLogDao = bonusChangeLogDao;
        this.lockManager = lockManager;
        this.objectCache = objectCache;
    }

    @Override
    public ChangeBonusServiceResult changeBonus(String uid, Action action, String productCode, int value, String desc, String sign) {
    	if (logger.isDebugEnabled())
        logger.debug(">>> changeBonus");
        if (StringUtils.trimToNull(uid) == null
                || StringUtils.trimToNull(action.getCode()) == null) {
            return ChangeBonusServiceResult.INVALID_PARAMETER;
        }
        if (action == null) {
            return ChangeBonusServiceResult.NO_SUCH_ACTION;
        }

        if (!action.isEnabled()) {
            return ChangeBonusServiceResult.DISABLED_ACTION;
        }

        if (action.getBonusCalcMethod() == BonusCalculationMethod.INPUT.getValue()) {

            if (!SignUtils.isValidSignForChangeBonus(
                    uid, action.getCode(), value, action.getValidationCode(), sign)) {

                return ChangeBonusServiceResult.INVALID_SIGN;
            }

        }
        //超过该动作一次变更上限
        if (action.getBonusMaxValue()<Math.abs(value)) {
            return ChangeBonusServiceResult.EXCEED_MAXVALUE;
        }
        desc = StringUtils.trimToEmpty(desc);

        Lock lock = lockManager.getLock(uid);
        lock.lock();  // 没有锁的线程会阻塞到这里，直到获得锁（拥有锁的线程释放了锁）

        ChangeBonusServiceResult ret;

        Account acct = null;
        int changedValue = 0;
        try {
            long t1 = System.nanoTime();
            //先从cache取account
            acct  = objectCache.getAccount(uid);
    		if(acct==null){
    			 acct = accountDao.findAccountByUid(uid);
    			 if (acct == null) {
    	                acct = Account.create(uid, action.getCode());
    	         } 
    		}
       
            if (acct.isBanned()) {
                 return ChangeBonusServiceResult.BANNED_ACCOUNT;
            }
            

            DailyExp todayExp = acct.findDailyExpByActionCode(action.getCode());

            Date today = new Date();

            if (action.getMaxExpPerDay() == -1) {  // 一次性动作
                if (todayExp.getCurrentValue() != 0) {
                    return ChangeBonusServiceResult.LIMITATION_REACHED;
                }
            } else {

                // 检查用户今天加的该动作经验值是否已超过该动作的每日上限
                if (DateUtils.isSameDay(todayExp.getUpdateTime(), today)) {

                    if (todayExp.getCurrentValue() >= action.getMaxExpPerDay()) {
                        return ChangeBonusServiceResult.LIMITATION_REACHED;
                    }
                } else { //新一天第一次
                    todayExp.setCurrentValue(0);
                }
            }

            DailyEarnedBonus dailyEarnedBonus = acct.getBonusEarnedToday();
            if(null == dailyEarnedBonus){
            	dailyEarnedBonus = new DailyEarnedBonus();
            	acct.setBonusEarnedToday(dailyEarnedBonus);
            }

            // 新一天第一次
            if (!DateUtils.isSameDay(dailyEarnedBonus.getUpdateTime(), today)) {
                acct.resetBonusEarnedToday();
            }

            // 变更积分
            changedValue = acct.incBonus(action, value);

            // 变更经验值
            acct.incExp(action, value);

            // 保存变更结果
            accountDao.updateExp(acct);
            ret = ChangeBonusServiceResult.OK(acct.getBonus(),action.getShowType(),action.getChangeValue(value));
            
            //变更用户当前account对象
            try{
            	objectCache.setAccount(acct.getUid(), acct);
            }catch(Exception e){
            	logger.error("set account memcache error",e);
            }
            //写入历史表
            BonusChangeLog cLog = BonusChangeLog.create(acct, action, productCode, changedValue, desc);
            this.bonusChangeLogDao.writeBonusChangeLog(cLog);
        } catch (InsufficientBonusException e) { // 积分不足，商城兑换失败

            logger.info("积分不足，商城兑换失败: uid={}, actionCode={}, value={}",
                    new Object[] {uid, action.getCode(), value});
            return ChangeBonusServiceResult.INSUFFICIENT_BONUS;

        } catch (RuntimeException e) {

            logger.error("updateExp cause an error", e);
            ret = ChangeBonusServiceResult.FAILED;

        } finally {
            lock.unlock();
        }

        return ret;
    }


}
