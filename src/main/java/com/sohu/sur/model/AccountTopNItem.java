package com.sohu.sur.model;

import java.util.List;

/**
 * User: guoyong
 * Date: 11-3-18 下午4:23
 */
public class AccountTopNItem {

    private String uid;

    private Medal medal;

    private int activityValue;

    public AccountTopNItem(AccountActivity activity, List<Medal> allMedals) {

        uid = activity.getUid();
        activityValue = activity.getContActiveDays();

        for (Medal m : allMedals) {

            if (activityValue >= medal.getMinActiveDays()
                    && activityValue <= medal.getMaxActiveDays()) {
                medal = m;
            }
        }
    }

    public String getUid() {
        return uid;
    }

    public Medal getMedal() {
        return medal;
    }

    public int getActivityValue() {
        return activityValue;
    }
}
