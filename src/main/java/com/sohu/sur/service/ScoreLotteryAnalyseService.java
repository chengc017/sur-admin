package com.sohu.sur.service;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.ScoreLotteryAnalyse;

public interface ScoreLotteryAnalyseService {

	/**
	 * 保存
	 * 
	 * @param scoreLotteryAnalyse
	 */
	public void saveScoreLotteryAnalyse(ScoreLotteryAnalyse scoreLotteryAnalyse);

	/**
	 * 查找某天抽奖 兑换统计
	 * @param type 1兑换 2抽奖
	 * @param stime
	 * @param etime
	 * @return
	 */
	public List<ScoreLotteryAnalyse> findScoreLotteryAnalyse(String type,String stime, String etime);
	/**
	 * 查找某天抽奖 兑换统计
	 * @param type 1兑换 2抽奖
	 * @param type
	 * @param stime
	 * @param etime
	 * @return
	 */
	public List<ScoreLotteryAnalyse> findScoreLotteryAnalyse(String type,Date stime, Date etime);
}
