package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.BannedAppeal;
import com.sohu.sur.util.Page;

public interface BannedAppealService {
	
	/**
	 * 按条件查询BannedAppeal列表
	 * @param isValid
	 * @param isDeal
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<BannedAppeal> findBannedAppeals(boolean isOverrule, Page page);

	/**
	 * 通过UID，查找申诉记录
	 * @param uid
	 */
	public BannedAppeal findBannedAppealByUid(String uid);

	/**
	 * 如果存在这个用户的申诉记录，直接在申诉记录上修改
	 * @param bannedAppeal
	 */
	public boolean saveOrUpdateBannedAppeal(BannedAppeal bannedAppeal);
	
	/**
	 * 删除申诉记录,通过BannedAppeal的ID进行删除
	 * @param bannedAppeal
	 */
	public boolean deleteBannedAppeal(BannedAppeal bannedAppeal);
	
	/**
	 * 将该用户从黑名单中移除
	 * @param uid
	 * @return
	 */
	public void unBanAccount(String uid);
	
}
