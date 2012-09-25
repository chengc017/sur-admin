package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.Product;
import com.sohu.sur.util.Page;

/**
 * User: 郭勇
 * Date: 2011-3-15 11:27:41
 */
public interface AccountActivityDao {
	
	/**
     * 查找用户有勋章活跃度列表 
     * @param uid 用户账号
     * @return 用户所有产品的活跃度列表（连续活跃天数>=3）
     */
    List<AccountActivity> findAccountActivities(String uid);

    /**
     * 查找用户在某个产品上的活跃度
     * @param account   用户账号
     * @param product 产品
     * @return AccountActivity 没找到返回null
     */
    AccountActivity findAccountActivity(Account account, Product product);

    /**
     * 新建或更新用户活跃度
     * @param activity 用户活跃度
     */
    void saveOrUpdateActivity(AccountActivity activity);

    /**
     * 查找用户所有产品的活跃度
     * @param account 用户账号
     * @return 用户所有产品的活跃度列表
     */
    List<AccountActivity> findAccountActivities(Account account);

    /**
     * 查找某个产品的用户活跃记录排行
     * @param product 产品
     * @param limit  top n
     * @return 用户活跃记录排行，活跃值倒序排列
     */
    List<AccountActivity> findAccountActivitiesByProduct(Product product, int limit);
    
    /**
     * 根据活动值的更新时间查找
     * @param product
     * @param date
     * @param start
     * @param limit
     * @return
     */
    List<AccountActivity> findAccountActivitiesUpdateBeforeDate(Product product, Date date, int start, int limit);
    
    /**
     * 获取某个产品下的最大活动值
     * @param product
     * @return
     */
    int findMaxAccountActivity(Product product);
    
    /**
     * 统计某个产品下满足指定活动值的用户数量。
     * @param product
     * @param contActiveDays
     * @return
     */
    long countAccountActivitiesByProductAndActiveDays(Product product, int contActiveDays);

    List<AccountActivity> findAccountActivitiesByActiveDays(String productCode, int minActiveDays, int maxActiveDays, Page page);

    List<AccountActivity> findAccountActivitiesByActiveDays(String productCode, int minActiveDays, int maxActiveDays);
    
    /**
     * 根据passport查该用户活跃度信息
     * @param uid
     * @return
     */
    public List<AccountActivity> findAccountActivitiesByUid(String uid);
}
