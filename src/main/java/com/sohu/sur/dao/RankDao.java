package com.sohu.sur.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.Rank;

/**
 * User: guoyong
 * Date: 11-3-17 下午4:56
 */
public interface RankDao extends ManagedEntityDao<Rank, ObjectId>{

    /**
     * 查找系统内设置的所有用户等级
     * @return 用户等级列表
     */
    List<Rank> findAllRanks();
    
    /**
     * 根据名称查找等级
     * @param name
     * @return
     */
    Rank findRankByName(String name);
    
    /**
     * 根据图标编号查找等级
     * @param iconId
     * @return
     */
    Rank findRankByIconId(String iconId);
}
