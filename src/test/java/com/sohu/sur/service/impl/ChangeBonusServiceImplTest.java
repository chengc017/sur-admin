package com.sohu.sur.service.impl;

import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;
import com.sohu.sur.model.*;
import com.sohu.sur.service.ChangeBonusService;
import com.sohu.sur.service.ChangeBonusServiceResult;
import com.sohu.sur.test.SpringTests;
import com.sohu.sur.util.SignUtils;
import net.sf.ehcache.Ehcache;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: 郭勇
 * Date: 2011-3-10 17:22:54
 */
public class ChangeBonusServiceImplTest extends SpringTests {

    Mongo mongo;
    Datastore ds;

    ChangeBonusService changeBonusService;

    final String uid = "yongguo@sohu-inc.com";
    final String actionCode = "view";

    String sign;

    Account acct;
    Action action;

    String cacheKey = uid + "|" + actionCode;

    @Before
    public void setUp() throws Exception {

        mongo = getBean(Mongo.class);
        ds = getBean(Datastore.class);

        mongo.dropDatabase(getDbName());

        changeBonusService = getBean(ChangeBonusService.class);

        String validationCode = UUID.randomUUID().toString();        

        action = new Action();
        action.setName("浏览");
        action.setDescription("浏览");
        action.setCode(actionCode);
        action.setValidationCode(validationCode);
        action.setEnabled(true);
        action.setExpCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        action.setExpDefaultValue(1);
        action.setExpTargetValue(0);
        action.setBonusCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        action.setBonusDefaultValue(1);
        action.setBonusTargetValue(0);
        action.setMaxExpPerDay(50);
        action.setBonusChangeType(BonusChangeType.REWARD.getValue());

        ds.save(action);

        acct = Account.create("yongguo@sohu-inc.com", action.getCode());
        acct.setExp(100);
        acct.setBonus(90);
        acct.getBonusTotalList().add(new BonusTotal(
                action.getBonusChangeType(), 90
        ));

        sign = SignUtils.generateSign(
                new StringBuffer(uid).append(actionCode).append(0),
                validationCode);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testChangeBonus_FirstTime() {

        ChangeBonusServiceResult ret = changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加1分", sign);

        Assert.assertEquals(ChangeBonusServiceResult.OK, ret);

        Account acctCreated = ds.find(Account.class, "uid", uid).get();

        Assert.assertNotNull(acctCreated);
        Assert.assertEquals(action.getExpDefaultValue(), acctCreated.getExp());
        Assert.assertNotNull(acctCreated.findDailyExpByActionCode(actionCode));
        Assert.assertEquals(action.getBonusDefaultValue(),
                acctCreated.findBonusTotal(
                        BonusChangeType.valueOf(action.getBonusChangeType())
                ).getTotal()
        );

        Assert.assertEquals(action.getBonusDefaultValue(),
                acctCreated.getBonusEarnedToday().getCurrentValue());

        BonusChangeLog cLog = ds.find(BonusChangeLog.class, "uid", uid).get();
        Assert.assertNotNull(cLog);
        Assert.assertEquals(action.getExpDefaultValue(), cLog.getChangedValue());

    }

    @Test
    public void testChangeBonus_DailyFirstTime() {

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        acct.findDailyExpByActionCode(action.getCode()).setCurrentValue(50);
        acct.findDailyExpByActionCode(action.getCode()).setUpdateTime(yesterday.getTime());

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -1);
        acct.setBonusEarnedToday(new DailyEarnedBonus(50, today.getTime()));
        ds.save(acct);

        changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加1分", sign);

        Account updatedAcct = ds.find(Account.class, "uid", uid).get();

        Assert.assertNotNull(updatedAcct);
        Assert.assertEquals(acct, updatedAcct);
        Assert.assertEquals("exp should be added", acct.getExp() + action.getExpDefaultValue(), updatedAcct.getExp());
        Assert.assertEquals(acct.getBonus() + action.getBonusDefaultValue(), updatedAcct.getBonus());
        Assert.assertEquals(action.getExpDefaultValue(),
                updatedAcct.findDailyExpByActionCode(actionCode).getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.findDailyExpByActionCode(actionCode).getUpdateTime(),
            new Date()
        ));

        BonusChangeLog cLog = ds.find(BonusChangeLog.class, "uid", uid).get();
        Assert.assertNotNull(cLog);
        Assert.assertEquals(action.getBonusDefaultValue(), cLog.getChangedValue());

        Assert.assertEquals(acct.findBonusTotal(
                BonusChangeType.valueOf(action.getBonusChangeType())).getTotal()
                    + action.getBonusDefaultValue(),
                updatedAcct.findBonusTotal(
                        BonusChangeType.valueOf(action.getBonusChangeType())
                ).getTotal()
        );

        Assert.assertEquals(action.getBonusDefaultValue(),
                updatedAcct.getBonusEarnedToday().getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.getBonusEarnedToday().getUpdateTime(),
            new Date()
        ));

    }

    @Test
    public void testChangeBonus_Normal() {

        acct.findDailyExpByActionCode(action.getCode()).setCurrentValue(3);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -1);
        acct.setBonusEarnedToday(new DailyEarnedBonus(50, today.getTime()));

        ds.save(acct);

        changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加1分", sign);
        changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加1分", sign);

        Account updatedAcct = ds.find(Account.class, "uid", uid).get();

        Assert.assertNotNull(updatedAcct);
        Assert.assertEquals(acct, updatedAcct);
        Assert.assertEquals(acct.getExp() + 2 * action.getExpDefaultValue(), updatedAcct.getExp());
        Assert.assertEquals(acct.getBonus() + 2 * action.getBonusDefaultValue(), updatedAcct.getBonus());
        Assert.assertEquals(
                acct.findDailyExpByActionCode(actionCode).getCurrentValue()
                    + 2 * action.getExpDefaultValue(),
                updatedAcct.findDailyExpByActionCode(actionCode).getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.findDailyExpByActionCode(actionCode).getUpdateTime(),
            new Date()
        ));

        List<BonusChangeLog> cLogs = ds.find(BonusChangeLog.class, "uid", uid).asList();
        Assert.assertEquals(2, cLogs.size());

        Assert.assertEquals(acct.findBonusTotal(
                BonusChangeType.valueOf(action.getBonusChangeType())).getTotal()
                    + 2 * action.getBonusDefaultValue(),
                updatedAcct.findBonusTotal(
                        BonusChangeType.valueOf(action.getBonusChangeType())
                ).getTotal()
        );

        Assert.assertEquals(2 * action.getBonusDefaultValue(),
                updatedAcct.getBonusEarnedToday().getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.getBonusEarnedToday().getUpdateTime(),
            new Date()
        ));

    }

    @Test
    public void testChangeBonus_OverLimitation() {

        Ehcache localCache = getBean(Ehcache.class);

        Assert.assertNull(localCache.get(cacheKey));

        acct.findDailyExpByActionCode(action.getCode()).setCurrentValue(action.getMaxExpPerDay() - 1);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -1);
        acct.setBonusEarnedToday(new DailyEarnedBonus(50, today.getTime()));

        ds.save(acct);

        action.setExpDefaultValue(2);
        action.setBonusDefaultValue(2);
        ds.save(action);

        ChangeBonusServiceResult ret = changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加2分", sign);
        Assert.assertEquals(ChangeBonusServiceResult.OK, ret);

        ret = changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加2分", sign);
        Assert.assertEquals(ChangeBonusServiceResult.LIMITATION_REACHED, ret);
        Assert.assertNotNull(localCache.get(cacheKey));

        ret = changeBonusService.changeBonus(uid, action, null, 0, "浏览新闻加2分", sign);
        Assert.assertEquals(ChangeBonusServiceResult.LIMITATION_REACHED, ret);

        Account updatedAcct = ds.find(Account.class, "uid", uid).get();

        Assert.assertNotNull(updatedAcct);
        Assert.assertEquals(acct, updatedAcct);
        Assert.assertEquals(acct.getExp() + action.getExpDefaultValue(), updatedAcct.getExp());
        Assert.assertEquals(acct.getBonus() + action.getBonusDefaultValue(), updatedAcct.getBonus());
        Assert.assertTrue(action.getMaxExpPerDay() <=
                updatedAcct.findDailyExpByActionCode(actionCode).getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.findDailyExpByActionCode(actionCode).getUpdateTime(),
            new Date()
        ));

        List<BonusChangeLog> cLogs = ds.find(BonusChangeLog.class, "uid", uid).asList();
        Assert.assertEquals(1, cLogs.size());


        Assert.assertEquals(action.getBonusDefaultValue(),
                updatedAcct.getBonusEarnedToday().getCurrentValue());
        Assert.assertTrue(DateUtils.isSameDay(
            updatedAcct.getBonusEarnedToday().getUpdateTime(),
            new Date()
        ));
    }

    // Todo 测试积分商城兑换消耗积分的接口调用
}
