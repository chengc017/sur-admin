package com.sohu.sur.dao;

import com.google.code.morphia.Datastore;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.model.DailyExp;
import com.sohu.sur.util.Page;

import java.util.List;

/**
 * User: 郭勇
 * Date: 2011-3-9 14:30:47
 */
public interface AccountDao {

    /**
     * 根据uid查找用户账号信息
     * @param uid  用户账号id
     * @return 用户账号信息 or null
     */
    Account findAccountByUid(String uid);

    /**
     * 更新用户的经验值/积分
     * @param acct  用户信息 已更新经验值和积分
     */
    void updateExp(Account acct);

    /**
     * 根据积分变动类型查询积分总和
     *
     * @param uid 用户passport
     * @param changeType 积分变动类型
     * @return 积分总和
     */
    int getTotalBonus(String uid, BonusChangeType changeType);

    void banAccount(String uid);

    void unBanAccount(String uid);

    List<Account> findBannedAccount(Page page);

    void validAccount(String uid, boolean isSpace);

    List<Account> findUnValidateAccount(int skip, int limit);
    /**
     * 查找金币数大于bonus的用户
     * @param bonus
     * @return
     */
    List<Account> findAccount(int bonus);
    public void changeDataStore(String uid);
    public void saveAccount(Account account);
    public List<Account> findAccount(Page page);
}
