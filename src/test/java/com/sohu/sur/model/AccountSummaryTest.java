package com.sohu.sur.model;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: guoyong
 * Date: 11-3-17 下午4:09
 */
public class AccountSummaryTest {

    Account account = new Account();
    AccountActivity activity = new AccountActivity();
    List<AccountActivity> activities = new ArrayList<AccountActivity>();
    List<Rank> allRanks = new ArrayList<Rank>();
    List<Medal> allMedals = new ArrayList<Medal>();

    @Before
    public void setUp() {

        account.setId(new ObjectId());
        account.setUid("yongguo@sohu-inc.com");
        account.setExp(128);

        ObjectId productId = new ObjectId();

        activity.setId(new ObjectId());
        activity.setUserId(account.getId());
        activity.setUid(account.getUid());
        activity.setContActiveDays(90);
        activity.setLatestActiveDate(new Date());
        activity.setProductId(productId);
        activity.setProductCode("blog");

        activities.add(activity);

        Medal medal1 = new Medal();
        medal1.setId(new ObjectId());
        medal1.setName("铜章");
        medal1.setForProduct(productId);
        medal1.setMinActiveDays(4);
        medal1.setMaxActiveDays(7);

        Medal medal2 = new Medal();
        medal2.setId(new ObjectId());
        medal2.setName("银章");
        medal2.setForProduct(productId);
        medal2.setMinActiveDays(8);
        medal2.setMaxActiveDays(30);

        Medal medal3 = new Medal();
        medal3.setId(new ObjectId());
        medal3.setName("金章");
        medal3.setForProduct(productId);
        medal3.setMinActiveDays(31);
        medal3.setMaxActiveDays(90);

        Medal medal4 = new Medal();
        medal4.setId(new ObjectId());
        medal4.setName("钻石");
        medal4.setForProduct(productId);
        medal4.setMinActiveDays(91);
        medal4.setMaxActiveDays(Integer.MAX_VALUE);

        Medal medal5 = new Medal();
        medal5.setId(new ObjectId());
        medal5.setName("钻石");
        medal5.setForProduct(new ObjectId());
        medal5.setMinActiveDays(91);
        medal5.setMaxActiveDays(Integer.MAX_VALUE);

        allMedals.add(medal1);
        allMedals.add(medal2);
        allMedals.add(medal3);
        allMedals.add(medal4);
        allMedals.add(medal5);
    }

    @Test
    public void testNewAccountSummary() {

//        AccountSummary summary = new AccountSummary(account, allRanks, activities, allMedals);
//
//        assertEquals(1, summary.getEarnedMedals().size());
//        assertEquals(allMedals.get(2).getId(), summary.getEarnedMedals().get(0).getId());
//
//        assertEquals(4, summary.getUnearnedMedals().size());
    }
}
