package com.sohu.sur.model;

import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.sohu.sur.spring.SurDomainConfig;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * User: guoyong
 * Date: 11-3-29 上午11:53
 */
public class AccountPerfData {

    private static Logger logger = LoggerFactory.getLogger(AccountPerfData.class);

    private ApplicationContext ctx;

    private Mongo mongo;

    private Datastore ds;

    Random randomGenerator = new Random();


    public AccountPerfData() {

        ctx = new AnnotationConfigApplicationContext(SurDomainConfig.class);

        mongo = ctx.getBean(Mongo.class);
        ds = ctx.getBean(Datastore.class);
    }

    public void insert() {

        int total = 2000000;

//        int total = 2;

        StopWatch stopWatchTotal =  new Slf4JStopWatch(logger);
        StopWatch stopWatchTenThousand = new Slf4JStopWatch(logger);
        StopWatch stopWatchPerMillion = new Slf4JStopWatch(logger);

        int j = 1;
        for (int i = 1; i <= total; i++, j++) {
            Account acct = create();


            ds.save(acct, WriteConcern.NONE);
            //logger.debug("[{}] '{}' saved.", i, acct.getUid());

            if (i % 100000 == 0) {
                stopWatchTenThousand.stop("one hundred thousand", String.valueOf(i));
                stopWatchTenThousand.start();
            }

            if (i % 1000000 == 0) {
                stopWatchPerMillion.stop("one million", String.valueOf(i));
                stopWatchPerMillion.start();
            }
        }

        stopWatchTotal.stop("insertAccount", String.valueOf(total));
    }

    private Account create() {

        Account newAcct = new Account();

        newAcct.setUid(UUID.randomUUID() + "@example.com");
        newAcct.setBanned(false);
        newAcct.setExp(randomGenerator.nextInt(1000));
        newAcct.setBonus(randomGenerator.nextInt(1000));
        newAcct.setCreateTime(new Date());
        newAcct.setLastUpdateTime(new Date());

        DailyExp dailyExp = DailyExp.create("view");
        newAcct.addTodayExp(dailyExp);

        BonusTotal bonusTotal = new BonusTotal(BonusChangeType.REWARD.getValue(), newAcct.getBonus());
        List<BonusTotal> bonusTotalList = new ArrayList<BonusTotal>(1);
        bonusTotalList.add(bonusTotal);

        newAcct.setBonusTotalList(bonusTotalList);


        return newAcct;
    }

    public static void main(String[] args) {

        AccountPerfData tester = new AccountPerfData();
        tester.insert();
    }
}
