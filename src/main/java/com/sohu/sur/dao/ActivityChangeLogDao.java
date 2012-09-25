package com.sohu.sur.dao;

import com.sohu.sur.model.ActivityChangeLog;

/**
 * User: guoyong
 * Date: 11-4-25 下午4:29
 */
public interface ActivityChangeLogDao {

    /**
     * 写活跃度变更记录
     * @param changeLog
     */
    void writeActivityChangeLog(ActivityChangeLog changeLog);
}
