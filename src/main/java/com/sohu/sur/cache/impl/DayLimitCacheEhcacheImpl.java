package com.sohu.sur.cache.impl;

import com.sohu.sur.cache.DayLimitCache;
import com.sohu.sur.util.DateUtils;
import net.sf.ehcache.Element;
import net.sf.ehcache.Ehcache;

/**
 * User: guoyong
 * Date: 11-3-21 上午10:08
 */
public class DayLimitCacheEhcacheImpl implements DayLimitCache {

    private final Ehcache dayLimitCache;

    public DayLimitCacheEhcacheImpl(Ehcache dayLimitCache) {
        this.dayLimitCache = dayLimitCache;
    }

    @Override
    public void setBonusReachedLimitation(String uid, String actionCode) {
        String key = generateKey(uid, actionCode);
        Element element = new Element(key, Boolean.TRUE);
        element.setTimeToLive(DateUtils.calculateSecondsToLive());
        dayLimitCache.put(element);
    }

    @Override
    public boolean getBonusReachedLimitation(String uid, String actionCode) {
        String key = generateKey(uid, actionCode);
        Element element = dayLimitCache.get(key);
        if (element == null) {
            return false;
        }
        return (Boolean) element.getValue();
    }

    @Override
    public void setActivityUpdated(String uid, String productCode) {
        String key = generateKey(uid, productCode);
        Element element = new Element(key, Boolean.TRUE);
        element.setTimeToLive(DateUtils.calculateSecondsToLive());
        dayLimitCache.put(element);
    }

    @Override
    public boolean getActivityUpdated(String uid, String productCode) {
        String key = generateKey(uid, productCode);
        Element element = dayLimitCache.get(key);
        if (element == null) {
            return false;
        }
        return (Boolean) element.getValue();
    }

    private String generateKey(String uid, String productCode) {
        return new StringBuffer(uid).append("|").append(productCode).toString();
    }


}
