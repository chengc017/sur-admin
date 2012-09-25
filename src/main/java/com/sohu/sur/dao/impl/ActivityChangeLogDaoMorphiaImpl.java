package com.sohu.sur.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.sohu.sur.dao.ActivityChangeLogDao;
import com.sohu.sur.model.ActivityChangeLog;

/**
 * User: guoyong
 * Date: 11-4-25 下午4:31
 */
public class ActivityChangeLogDaoMorphiaImpl extends BasicDAO<ActivityChangeLog, String> implements ActivityChangeLogDao {

    public ActivityChangeLogDaoMorphiaImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public void writeActivityChangeLog(ActivityChangeLog changeLog) {
        this.save(changeLog);
    }
}
