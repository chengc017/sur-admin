package com.sohu.sur.dao;

import com.sohu.sur.model.BonusChangeLog;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.util.Page;

import java.util.Date;
import java.util.List;

/**
 * User: 郭勇
 * Date: 2011-3-11 15:35:15
 */
public interface BonusChangeLogDao {

    /**
     * 写积分变更日志
     * @param cLog 积分变更记录
     */
    void writeBonusChangeLog(BonusChangeLog cLog);

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
     * @param uid 用户passport
     * @param pageNo  页号/第几页
     * @param pageSize 每页显示条目数
     * @return  积分变动记录列表
     */
    List<BonusChangeLog> findBonusChangeLogs(String uid, int pageNo, int pageSize);

    /**
     * 分页查询积分变动日志
     * 大数据集效率会低，需要后续改进
     *
     * @param uid 用户passport
     * @param changeType 积分变动类型
     * @param page
     * @return  积分变动记录列表
     */
    List<BonusChangeLog> findBonusChangeLogs(String uid, BonusChangeType changeType, Date startTime, Date endTime, Page page);
    /**
     * 分页查询积分变动日志
     * 大数据集效率会低，需要后续改进
     *
     * @param uid 用户passport
     * @param page
     * @return  积分变动记录列表
     */
    List<BonusChangeLog> findBonusChangeLogs(String uid, Date startTime, Date endTime, Page page);
    /**
     * 查询某天 某动作记录
     * @param actionCode
     * @param startTime
     * @param endTime
     * @return
     */
    public List<BonusChangeLog> findBonusChangeLogsLottery(String actionCode,Date startTime, Date endTime);
}
