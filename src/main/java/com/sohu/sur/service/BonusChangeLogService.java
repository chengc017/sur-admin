package com.sohu.sur.service;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.BonusChangeLog;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.util.Page;

/**
 * 积分变更记录接口
 * User: guoyong
 * Date: 11-3-18 上午9:27
 */
public interface BonusChangeLogService {

    /**
     * 分页查询积分变动日志
     * 大数据集效率会低，需要后续改进
     * @param uid 用户passport
     * @param changeType 积分变动类型
     * @param pageNo  页号/第几页
     * @param pageSize 每页显示条目数
     * @return  积分变动记录列表
     */
    List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, int pageNo, int pageSize);

    /**
     * 分页查询积分变动日志
     * 大数据集效率会低，需要后续改进
     *
     * @param uid 用户passport
     * @param changeType 积分变动类型
     * @param day 日期(yyyyMMdd)
     * @param page
     * @return  积分变动记录列表
     */
    List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, String day, Page page);

    /**
     * 根据积分变动类型查询积分总和
     * @param uid 用户passport
     * @param changeType 积分变动类型
     * @return 积分总和
     */
    int getTotalBonus(String uid, BonusChangeType changeType);
    
    /**
     * 抽奖 兑换等动作消费统计
     * @param actionCode 
     * @param stime  
     * @param etime
     * @return
     */
    public List<BonusChangeLog> findBonusChangeLogsLotterys(String actionCode,Date stime,Date etime);
}
