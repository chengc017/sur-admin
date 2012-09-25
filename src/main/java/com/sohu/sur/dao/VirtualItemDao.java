package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.VirtualItem;

public interface VirtualItemDao extends ManagedEntityDao<VirtualItem, ObjectId>{
	/**
	 * 查询某天有数据的虚拟商品兑换和赠送情况
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<VirtualItem> findVirtualItem(Date scdate, Date ecdate);

}
