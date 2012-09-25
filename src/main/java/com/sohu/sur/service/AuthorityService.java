package com.sohu.sur.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.ScoreFunc;
import com.sohu.sur.model.ScoreRole;
import com.sohu.sur.model.ScoreUser;

public interface AuthorityService {
	/**
	 * 用户登录验证 成功返回该用户记录 反之返回null
	 * @param name
	 * @param pwd
	 * @return
	 */
	public ScoreUser loginCheck(String name,String pwd);
    /**
     * 获取所有用户
     * @param name
     * @param state
     * @return
     */
	public List<ScoreUser>  getAllScoreUser(String name,int state);
	
	
	/**
	 * 保存用户
	 */
	public void saveScoreUser(ScoreUser su);
	/**
	 * 修改 用户
	 */
	public void updateScoreUser(ScoreUser su);
	/**
	 * 保存角色
	 */
	public void saveScoreRole(ScoreRole sr);
	/**
	 * 修改角色
	 */
	public void updateScoreRole(ScoreRole sr);
	/**
	 * 保存功能
	 */
	public void saveScoreFunc(ScoreFunc sf);
	/**
	 * 修改功能
	 */
	public void updateScoreFunc(ScoreFunc sf);
	
	/**
	 * 返回所有功能
	 * @return
	 */
	public List<ScoreFunc> findFuncbyPid(ObjectId pid);
	
	/**
	 * 查找用户功能
	 * @param id
	 * @return
	 */
	public List<ScoreFunc> getUserFunc(ScoreUser scoreUser);
	/**
	 * 查找用户
	 * @param id
	 * @return
	 */
	public ScoreUser findUserById(ObjectId id);
	/**
	 * 查找角色
	 * @param id
	 * @return
	 */
	public ScoreRole findRoleById(ObjectId id);
	/**
	 * 查找功能
	 * @param id
	 * @return
	 */
	public ScoreFunc findFuncById(ObjectId id);
	/**
	 * 查找所有用户
	 * @return
	 */
	public List<ScoreUser> selectAllScoreUser();
	/**
	 * 查找所有角色
	 * @return
	 */
	public List<ScoreRole> selectAllScoreRole();
	/**
	 * 查找所有功能
	 * @return
	 */
	public List<ScoreFunc> selectAllScoreFunc();
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUserById(ObjectId id);
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRoleById(ObjectId id);
	/**
	 * 删除功能
	 * @param id
	 */
	public void deleteFuncById(ObjectId id);

}
