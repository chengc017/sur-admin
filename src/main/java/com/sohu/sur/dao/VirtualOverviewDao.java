package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.VirtualOverview;

public interface VirtualOverviewDao extends ManagedEntityDao<VirtualOverview, ObjectId>{
	/**
	 * 查询某时间段虚拟商品概况
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<VirtualOverview> findVirtualOverview(Date scdate, Date ecdate);

}
