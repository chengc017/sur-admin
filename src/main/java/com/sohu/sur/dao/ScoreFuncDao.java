package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Action;
import com.sohu.sur.model.ScoreFunc;

public interface ScoreFuncDao extends ManagedEntityDao<ScoreFunc, ObjectId>  {

	
	public List<ScoreFunc> findPidFunc(ObjectId pid);
	
}
