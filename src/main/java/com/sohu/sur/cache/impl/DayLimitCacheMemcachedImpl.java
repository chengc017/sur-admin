package com.sohu.sur.cache.impl;

import com.sohu.sur.cache.DayLimitCache;
import com.sohu.sur.service.ChangeActivityServiceResult;
import com.sohu.sur.service.ChangeBonusServiceResult;
import com.sohu.sur.util.DateUtils;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Messager;
import java.text.Format;
import java.text.MessageFormat;

/**
 * User: guoyong
 * Date: 11-3-21 下午2:32
 */
public class DayLimitCacheMemcachedImpl implements DayLimitCache {

    private final static Logger logger = LoggerFactory.getLogger(DayLimitCacheMemcachedImpl.class);

    private final XMemcachedClient memcachedClient;

    private final MessageFormat changeBonusUriFormat;

    private final MessageFormat changeActivityUriFormat;

    public DayLimitCacheMemcachedImpl(XMemcachedClient memcachedClient, String changeBonusUri, String changeActivityUri) {

        this.memcachedClient = memcachedClient;

        changeBonusUriFormat = new MessageFormat(changeBonusUri);
        changeActivityUriFormat = new MessageFormat(changeActivityUri);

        try {
            changeBonusUriFormat.format(new Object[] {"uid", "actionCode"});
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid changeBonusUri format");
        }

        try {
            changeActivityUriFormat.format(new Object[] {"uid", "productCode"});
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid changeActivityUriFormat format");
        }
    }

    @Override
    public void setBonusReachedLimitation(String uid, String actionCode) {

        String uri = changeBonusUriFormat.format(new Object[] {uid, actionCode});
        
        if (logger.isDebugEnabled())
        logger.debug("changeBonusUri={}", uri);

        String key = DigestUtils.sha256Hex(uri);

        if (logger.isDebugEnabled())
        logger.debug("sha256Hex(changeBonusUri)={}", key);

        int expires = DateUtils.calculateSecondsToLive();

        String jsonResult = ChangeBonusServiceResult.LIMITATION_REACHED().toJsonpString();

        try {
            memcachedClient.add(key, expires, jsonResult);
        } catch (Exception e) {
        	if (logger.isDebugEnabled())
            logger.warn("error occured when adding object to memcached", e);
        }
    }

    @Override
    public boolean getBonusReachedLimitation(String uid, String actionCode) {
        throw new UnsupportedOperationException("未实现");
    }

    @Override
    public void setActivityUpdated(String uid, String productCode) {

        String uri = changeActivityUriFormat.format(new Object[] {uid, productCode});
        if (logger.isDebugEnabled())
        logger.debug("changeActivityUri={}", uri);

        String key = DigestUtils.sha256Hex(uri);
        if (logger.isDebugEnabled())
        logger.debug("sha256Hex(changeActivityUri)={}", key);

        int expires = DateUtils.calculateSecondsToLive();

        String jsonResult = ChangeActivityServiceResult.ALREADY_UPDATED().toJsonpString();

        try {
            memcachedClient.add(key, expires, jsonResult);
        } catch (Exception e) {
        	if (logger.isDebugEnabled())
            logger.warn("error occured when adding object to memcached", e);
        }
    }

    @Override
    public boolean getActivityUpdated(String uid, String productCode) {
        throw new UnsupportedOperationException("未实现");
    }
}
