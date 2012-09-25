package com.sohu.sur.cache;

/**
 * User: guoyong
 * Date: 11-3-21 上午10:01
 */
public interface DayLimitCache {

    /**
     * 设置该动作的用户积分已达每日上限
     * @param uid 用户id
     * @param actionCode 动作代码
     *
     */
    void setBonusReachedLimitation(String uid, String actionCode);

    /**
     * 获取该动作的用户积分是否已达每日上限
     * @param uid 用户id
     * @param actionCode 动作代码
     * @return true 已达每日上限 false 未达每日上限
     */
    boolean getBonusReachedLimitation(String uid, String actionCode);

    /**
     * 设置用户指定产品当日活动值为已更新状态
     * @param uid 用户id
     * @param productCode 产品代码
     */
    void setActivityUpdated(String uid, String productCode);

    /**
     * 获取用户指定产品当日活动值的状态
     * @param uid  用户id
     * @param productCode  产品代码
     * @return    true 已更新 false 未更新
     */
    boolean getActivityUpdated(String uid, String productCode);
}
