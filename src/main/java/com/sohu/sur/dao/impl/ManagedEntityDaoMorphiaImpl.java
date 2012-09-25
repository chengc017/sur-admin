package com.sohu.sur.dao.impl;

import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.WriteResult;
import com.sohu.sur.dao.ManagedEntityDao;
import com.sohu.sur.model.ManagedEntity;

public class ManagedEntityDaoMorphiaImpl<E extends ManagedEntity<ID>, ID> extends BasicDAO<E, ID>
		implements ManagedEntityDao<E, ID> {

	public ManagedEntityDaoMorphiaImpl(Datastore datastore) {
		super(datastore);
	}

	@Override
	public List<E> selectAll() {
		return super.find().asList();
	}

	@Override
	public int update(E managedEntity) {
		Key<E> key = super.save(managedEntity);
		if(null != key && null != key.getId()){
			return 1;
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID insert(E managedEntity) {
		Key<E> key = super.save(managedEntity);
		return (ID)key.getId();
	}

	@Override
	public E selectById(ID id) {
		return super.findOne("id", id);
	}

	@Override
	public int delById(ID id) {
		WriteResult result = super.deleteById(id);
		return result.getN();
	}
}
