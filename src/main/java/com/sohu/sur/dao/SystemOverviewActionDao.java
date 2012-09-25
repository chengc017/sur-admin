package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.SystemOverviewAction;

public interface SystemOverviewActionDao{
	/**
	 * 查询某时间段系统动作概况
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<SystemOverviewAction> findSystemOverview(Date scdate, Date ecdate);

}
