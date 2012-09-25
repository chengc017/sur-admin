package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Account;
import com.sohu.sur.model.BannedAppeal;
import com.sohu.sur.util.Page;

/**
 * 
 * @author leiyangbj6779
 * 
 */
public interface BannedAppealDao extends
		ManagedEntityDao<BannedAppeal, ObjectId> {

	public BannedAppeal findBannedAppealByUid(String uid);

	public boolean saveOrUpdateBannedAppeal(BannedAppeal bannedAppeal);
	
	public boolean deleteBannedAppeal(BannedAppeal bannedAppeal);
	
	public List<BannedAppeal> findBannedAppeals(boolean isOverrule,Page page);
}
