package com.sohu.sur.dao.impl;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.sohu.sur.dao.ActionDao;
import com.sohu.sur.model.Action;
import com.sohu.sur.test.MongoDBHelper;

/**
 * User: 郭勇
 * Date: 2011-3-10 17:31:04
 */
public class ActionDaoMorphiaImplTests {

    MongoDBHelper helper;

    protected ActionDao actionDao;

    final String actionCode = "view";

    @Before
    public void setUp() throws Exception {

        helper = MongoDBHelper.getInstance();

        Datastore ds = helper.getDatastore();

        actionDao = new ActionDaoMorphiaImpl(ds);

        Action act = new Action();
        act.setCode(actionCode);
        act.setEnabled(true);
        act.setCreateTime(new Date());
        ds.save(act);
    }

    @After
    public void tearDown() {
        helper.cleanDB();
    }

    @Test
    public void testFindActionByCode() {
        Action act = actionDao.findActionByCode(actionCode);
        Assert.assertNotNull(act);
        Assert.assertNotNull(act.getId());
        Assert.assertEquals(actionCode, act.getCode());
    }
    
    @Test
    public void testFindAllAction(){
    	List<Action> actions = actionDao.selectAll();
    	Assert.assertEquals(1, actions.size());
    }
    
    @Test
    public void testSaveAction(){
    	String name = "浏览新闻";
    	Action action = actionDao.findActionByCode(actionCode);
    	action.setName(name);
    	actionDao.insert(action);
    	action = actionDao.findActionByCode(actionCode);
    	Assert.assertEquals(name, action.getName());
    }
    
    @Test
    public void testDeleteAction(){
    	Action action = actionDao.findActionByCode(actionCode);
    	actionDao.delById(action.getId());
    	List<Action> actions = actionDao.selectAll();
    	Assert.assertEquals(0, actions.size());
    	
    }   
}
