package com.sohu.sur.dao;

import java.util.List;

import com.sohu.sur.model.ManagedEntity;

/**
 * @author 韩孝冰
 *
 * @param <E> 实体类型
 * @param <ID> 实体id属性类型
 */
public interface ManagedEntityDao<E extends ManagedEntity<ID>, ID> {
	/**
	 * 查询全部实体
	 * 
	 * @return 实体列表
	 */
	List<E> selectAll();

	/**
	 * 更新指定实体
	 * 
	 * @param managedEntity
	 * @return 影响行数
	 */
	int update(E managedEntity);

	/**
	 * 新增实体
	 * 
	 * @param managedEntity
	 * @return 实体Id属性
	 */
	ID insert(E managedEntity);

	/**
	 * 根据id查询实体
	 * 
	 * @param id
	 * @return
	 */
	E selectById(ID id);

	/**
	 * 删除实体
	 * 
	 * @param id
	 * @return 影响行数
	 */
	int delById(ID id);
	
}
