package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.ScoreRole;
import com.sohu.sur.model.ScoreUser;

public interface ScoreUserDao extends ManagedEntityDao<ScoreUser, ObjectId>{
	/**
	 * 用户登录检查
	 * @param name
	 * @param pwd
	 * @return
	 */
	public List<ScoreUser> checkScoreUser(String name, String pwd);

	/**
	 * 根据用户名查找用户
	 * @param name
	 * @return
	 */
	public ScoreUser findScoreUser(String name);
	/**
	 * 根据用户名、状态查找用户
	 * @param name
	 * @param state 1有效/0无效
	 * @return
	 */
	public List<ScoreUser> findScoreUser(String name,int state);
	
}
