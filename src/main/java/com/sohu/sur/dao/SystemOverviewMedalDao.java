package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.SystemOverviewMedal;

public interface SystemOverviewMedalDao{
	/**
	 * 查询某时间段系统勋章概况
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<SystemOverviewMedal> findSystemOverview(Date scdate, Date ecdate);

}
