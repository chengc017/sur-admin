package com.sohu.sur.dao;

import java.util.Date;
import java.util.List;

import com.sohu.sur.model.ScoreLotteryProductAnalyse;

public interface ScoreLotteryProductAnalyseDao{
	/**
	 * 查询某时间段  某商品抽奖兌換统计
	 * @param productId
	 * @param type
	 * @param order
	 * @param scdate
	 * @param ecdate
	 * @return
	 */
	public List<ScoreLotteryProductAnalyse> findScoreLotteryProductAnalyse(String productId,String type,String order,Date scdate, Date ecdate);

	/**
	 * save
	 * @param scoreLotteryProductAnalyse
	 */
	public void saveScoreLotteryProductAnalyse(ScoreLotteryProductAnalyse scoreLotteryProductAnalyse);
}
