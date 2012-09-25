package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.sohu.sur.model.ScoreLotteryAnalyse;
import com.sohu.sur.model.ScoreUser;

public interface ScoreLotteryAnalyseDao{
	/**
	 * 查询某时间段抽奖 兑换分析
	 * @param type
	 * @param scdate  
	 * @param ecdate  
	 * @return
	 */
	public List<ScoreLotteryAnalyse> findLotteryAnalyse(String type,Date scdate, Date ecdate);

	/**
	 * save
	 * @param scoreLotteryAnalyse
	 */
	public void saveScoreLotteryAnalyse(ScoreLotteryAnalyse scoreLotteryAnalyse);
}
