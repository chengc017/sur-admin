package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.SystemOverview;

public interface SystemOverviewDao{
	/**
	 * 查询某时间段系统概况
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<SystemOverview> findSystemOverview(Date scdate, Date ecdate);

}
