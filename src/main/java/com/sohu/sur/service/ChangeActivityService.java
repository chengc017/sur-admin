package com.sohu.sur.service;

import java.util.Date;

import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.Product;

/**
 * 用户产品活跃度接口
 *
 * User: 郭勇
 * Date: 2011-3-15 11:02:58
 */
public interface ChangeActivityService {

    /**
     * 变更用户某个产品的活跃度
     * @param uid 用户id
     * @param productCode 产品代码
     * @return ChangeActivityServiceResult
     */
    ChangeActivityServiceResult changeActivity(String uid, Product product);
    
    /**
     * 根据用户活动值最后更新时间，扣减未登录期间的活动值
     * 1、<code>activity.latestActiveDate</code>和<code>date</code>是同一天或是其后的某天，不做处理。
     * 2、<code>activity.latestActiveDate</code>是<code>date</code>之前第n天，
     * 则<code>activity.contActiveDays</code>减n，更新<code>activity.latestActiveDate</code>为<code>date</code>，
     * 并写活动值变更记录。
     * @param product
     * @param activity
     * @param date
     */
    void minusActivity(Product product, AccountActivity activity, Date date);
    
    /**
     * 取当前时间的前一天为基准日，扣减活动值到基准日
     * @param product
     * @param activity
     */
    void minusActivity(Product product, AccountActivity activity);
    /**
     * 根据用户活动值最后更新时间 变更活动值
     * @param product
     * @param activity
     * @param interval 最后执行时间到现在间隔天数
     */
    public void minusAndPlus(Product product, AccountActivity activity, int interval);
    /**
     * 活动值加1，并写活动值变更记录。
     * @param product
     * @param activity
     */
    void plusActivity(Product product, AccountActivity activity);
    
    void minusActivityForNoLoginUsers(Product product);
}
