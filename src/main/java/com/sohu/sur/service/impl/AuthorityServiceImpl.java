package com.sohu.sur.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.dao.ScoreFuncDao;
import com.sohu.sur.dao.ScoreRoleDao;
import com.sohu.sur.dao.ScoreUserDao;
import com.sohu.sur.model.ScoreFunc;
import com.sohu.sur.model.ScoreRole;
import com.sohu.sur.model.ScoreUser;
import com.sohu.sur.service.AuthorityService;

/**
 * 权限管理service
 * 
 * @author xuewuhao
 * 
 */
public class AuthorityServiceImpl implements AuthorityService {
	private static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
	private ScoreUserDao scoreUserDao;
	private ScoreRoleDao scoreRoleDao;
	private ScoreFuncDao scoreFuncDao;

	public AuthorityServiceImpl(ScoreUserDao scoreUserDao, ScoreRoleDao scoreRoleDao, ScoreFuncDao scoreFuncDao) {

		this.scoreFuncDao = scoreFuncDao;
		this.scoreRoleDao = scoreRoleDao;
		this.scoreUserDao = scoreUserDao;
	}

	@Override
	public ScoreUser loginCheck(String name, String pwd) {
		// TODO Auto-generated method stub
		List<ScoreUser> listScoreUser = scoreUserDao.checkScoreUser(name, pwd);
		if (listScoreUser != null && listScoreUser.size() > 0) {
			return listScoreUser.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<ScoreUser> getAllScoreUser(String name, int state) {
		// TODO Auto-generated method stub
		return scoreUserDao.findScoreUser(name, state);
	}


	public List<ScoreFunc> findFuncbyPid(ObjectId pid) {
		return this.scoreFuncDao.findPidFunc(pid);
	}

	public List<ScoreFunc> getUserFunc(ScoreUser scoreUser) {
		return scoreUser.getScoreRoleList().get(0).getScoreFuncList();
	}

	@Override
	public ScoreUser findUserById(ObjectId id) {
		// TODO Auto-generated method stub
		return this.scoreUserDao.selectById(id);
	}

	@Override
	public ScoreRole findRoleById(ObjectId id) {
		// TODO Auto-generated method stub
		return this.scoreRoleDao.selectById(id);
	}

	@Override
	public ScoreFunc findFuncById(ObjectId id) {
		// TODO Auto-generated method stub
		return this.scoreFuncDao.selectById(id);
	}

	@Override
	public void saveScoreUser(ScoreUser su) {
		// TODO Auto-generated method stub
		this.scoreUserDao.insert(su);
	}

	@Override
	public void updateScoreUser(ScoreUser su) {
		// TODO Auto-generated method stub
		this.scoreUserDao.update(su);
	}

	@Override
	public void saveScoreRole(ScoreRole sr) {
		// TODO Auto-generated method stub
		this.scoreRoleDao.insert(sr);
	}

	@Override
	public void updateScoreRole(ScoreRole sr) {
		// TODO Auto-generated method stub
		this.scoreRoleDao.update(sr);
	}

	@Override
	public void saveScoreFunc(ScoreFunc sf) {
		// TODO Auto-generated method stub
		this.scoreFuncDao.insert(sf);
	}

	@Override
	public void updateScoreFunc(ScoreFunc sf) {
		// TODO Auto-generated method stub
		this.scoreFuncDao.update(sf);
	}

	@Override
	public List<ScoreUser> selectAllScoreUser() {
		// TODO Auto-generated method stub
		return this.scoreUserDao.selectAll();
	}

	@Override
	public List<ScoreRole> selectAllScoreRole() {
		// TODO Auto-generated method stub
		return this.scoreRoleDao.selectAll();
	}

	@Override
	public List<ScoreFunc> selectAllScoreFunc() {
		// TODO Auto-generated method stub
		return this.scoreFuncDao.selectAll();
	}

	@Override
	public void deleteUserById(ObjectId id) {
		// TODO Auto-generated method stub
		this.scoreUserDao.delById(id);
	}

	@Override
	public void deleteRoleById(ObjectId id) {
		// TODO Auto-generated method stub
		this.scoreRoleDao.delById(id);
	}

	@Override
	public void deleteFuncById(ObjectId id) {
		// TODO Auto-generated method stub
		this.scoreFuncDao.delById(id);
	}

}
