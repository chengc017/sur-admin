package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Action;

/**
 * User: 郭勇 Date: 2011-3-9 17:38:54
 */
public interface ActionDao extends ManagedEntityDao<Action, ObjectId> {

	/**
	 * 根据动作代码获取变更动作
	 * 
	 * @param actionCode
	 *            动作代码
	 * @return 变更动作，找不到时返回null
	 */
	Action findActionByCode(String actionCode);
	
	Action findActionByName(String actionName);
	/**
	 * 查找所有有效动作
	 * @return
	 */
    List<Action> findAllEnabledActions();
}
