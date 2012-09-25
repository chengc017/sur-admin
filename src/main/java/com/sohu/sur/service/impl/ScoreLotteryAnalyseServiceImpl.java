package com.sohu.sur.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sohu.sur.dao.ScoreLotteryAnalyseDao;
import com.sohu.sur.model.ScoreLotteryAnalyse;
import com.sohu.sur.service.ScoreLotteryAnalyseService;

public class ScoreLotteryAnalyseServiceImpl implements ScoreLotteryAnalyseService {

	private ScoreLotteryAnalyseDao scoreLotteryAnalyseDao;

	public ScoreLotteryAnalyseServiceImpl(ScoreLotteryAnalyseDao scoreLotteryAnalyseDao) {
		this.scoreLotteryAnalyseDao = scoreLotteryAnalyseDao;
	}

	@Override
	public void saveScoreLotteryAnalyse(ScoreLotteryAnalyse scoreLotteryAnalyse) {
		scoreLotteryAnalyseDao.saveScoreLotteryAnalyse(scoreLotteryAnalyse);
	}

	@Override
	public List<ScoreLotteryAnalyse> findScoreLotteryAnalyse(String type,String stime, String etime) {
		Date scdate = null;
		Date ecdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * set默认查询时间为昨天
		 */
		if (stime == null || stime.equals("")) {
			stime = this.getYestoday();
			etime = stime;
		}
		if (stime != null && !stime.equals("")) {
			try {
				scdate = sdf.parse(stime + " 00:00:00");
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		if (etime != null && !etime.equals("")) {
			try {
				ecdate = sdf.parse(etime + " 23:59:59");
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		return scoreLotteryAnalyseDao.findLotteryAnalyse(type,scdate, ecdate);
	}

	@Override
	public List<ScoreLotteryAnalyse> findScoreLotteryAnalyse(String type,Date stime, Date etime) {
		return scoreLotteryAnalyseDao.findLotteryAnalyse(type,stime, etime);
	}
	/**
	 * 获取昨天字符串
	 * 
	 * @return
	 */
	public String getYestoday() {
		String strpattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(strpattern);
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, -1);
		return sdf.format(calendar.getTime());
	}
}
