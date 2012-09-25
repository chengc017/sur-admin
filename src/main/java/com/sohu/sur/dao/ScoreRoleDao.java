package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Action;
import com.sohu.sur.model.ScoreRole;

public interface ScoreRoleDao extends ManagedEntityDao<ScoreRole, ObjectId> {
	
	/**
	 * 查找角色
	 * @param name
	 * @return
	 */
	public ScoreRole findScoreRole(String name);
	
}
