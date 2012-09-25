package com.sohu.sur.service.aspect;

import com.sohu.sur.cache.DayLimitCache;
import com.sohu.sur.service.ChangeActivityServiceResult;
import com.sohu.sur.service.ChangeBonusServiceResult;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: guoyong
 * Date: 11-3-21 上午11:16
 */
@Aspect
public class DayLimitCacheAspect {

    private final static Logger logger = LoggerFactory.getLogger(DayLimitCacheAspect.class);

    private final DayLimitCache dayLimitCacheLocal;

    private final DayLimitCache dayLimitCacheRemote;

    private final boolean useLocalCache;

    private final boolean useRemoteCache;

    public DayLimitCacheAspect(DayLimitCache dayLimitCacheLocal, DayLimitCache dayLimitCacheRemote,
                               boolean useLocalCache, boolean useRemoteCache) {
        this.dayLimitCacheLocal = dayLimitCacheLocal;
        this.dayLimitCacheRemote = dayLimitCacheRemote;
        this.useLocalCache = useLocalCache;
        this.useRemoteCache = useRemoteCache;
    }

    @Around("execution(* com.sohu.sur.service.ChangeBonusService.changeBonus(..)) &&"
            + " args(uid,actionCode,..)")
    public Object aroundChangeBonus(ProceedingJoinPoint pjp, String uid, String actionCode) throws Throwable {
    	if (logger.isDebugEnabled())
        logger.debug("before changeBonus, uid={}, actionCode={}", uid, actionCode);

        if (StringUtils.trimToNull(uid) == null) {
            return ChangeBonusServiceResult.INVALID_PARAMETER;
        }

        if (useLocalCache) {
            if (dayLimitCacheLocal.getBonusReachedLimitation(uid, actionCode)) {
            	if (logger.isDebugEnabled())
            	logger.debug("bonus limitation hit in local cache for uid={}, actionCode={}", uid, actionCode);
                return ChangeBonusServiceResult.LIMITATION_REACHED;
            }
        }

        Object retVal = pjp.proceed();

        if (ChangeBonusServiceResult.LIMITATION_REACHED
                .equals((ChangeBonusServiceResult) retVal)) {

            if (useLocalCache) {
            	if (logger.isDebugEnabled())
                logger.debug("set bonus limitation in local cache for uid={}, actionCode={}", uid, actionCode);
                dayLimitCacheLocal.setBonusReachedLimitation(uid, actionCode);
            }

            if (useRemoteCache) {
            	if (logger.isDebugEnabled())
                logger.debug("set bonus limitation in remote cache for uid={}, actionCode={}", uid, actionCode);
                dayLimitCacheRemote.setBonusReachedLimitation(uid, actionCode);
            }
        }
        return retVal;
    }

    @Around("execution(* com.sohu.sur.service.ChangeActivityService.changeActivity(..)) &&"
            + " args(uid,productCode,..)")
    public Object aroundChangeActivity(ProceedingJoinPoint pjp, String uid, String productCode) throws Throwable {

    	if (logger.isDebugEnabled())
        logger.debug("before changeActivity, uid={}, productCode={}", uid, productCode);

        if (StringUtils.trimToNull(uid) == null
                || StringUtils.trimToNull(productCode) == null) {
            return ChangeActivityServiceResult.INVALID_PARAMETER;
        }

        if (useLocalCache) {
            if (dayLimitCacheLocal.getActivityUpdated(uid, productCode)) {
            	if (logger.isDebugEnabled())
                logger.debug("activity limitation hit in local cache for uid={}, productCode={}", uid, productCode);
                return ChangeActivityServiceResult.ALREADY_UPDATED;
            }
        }

        Object retVal = pjp.proceed();

        if (ChangeActivityServiceResult.OK.equals(retVal)
                ||  ChangeActivityServiceResult.ALREADY_UPDATED.equals(retVal)) {

            if (useLocalCache) {
            	if (logger.isDebugEnabled())
                logger.debug("set activity limitation in local cache for uid={}, productCode={}", uid, productCode);
                dayLimitCacheLocal.setActivityUpdated(uid, productCode);
            }

            if (useRemoteCache) {
            	if (logger.isDebugEnabled())
                logger.debug("set activity limitation in remote cache for uid={}, productCode={}", uid, productCode);
                dayLimitCacheRemote.setActivityUpdated(uid, productCode);
            }
        }

        return retVal;
    }
}
