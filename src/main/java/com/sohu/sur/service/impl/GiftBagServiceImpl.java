package com.sohu.sur.service.impl;

import java.util.Date;
import java.util.Random;

import com.sohu.sur.dao.GiftBagDao;
import com.sohu.sur.model.GiftBag;
import com.sohu.sur.service.GiftBagService;
import com.sohu.sur.util.DateUtil;
import com.sohu.sur.util.po.GiftBagResult;

public class GiftBagServiceImpl implements GiftBagService {

	/**
	 * 每天中奖上限
	 */
	private static final int maxNum = 4;
	/**
	 * 每天每人接口调用上限
	 */
	private static final int maxNumPerDay = 10;
	
	
	private GiftBagDao giftBagDao;

	public GiftBagServiceImpl() {
	}

	public GiftBagServiceImpl(GiftBagDao giftBagDao) {
		this.giftBagDao = giftBagDao;
	}

	@Override
	public GiftBag findGiftBag(String uid) {
		// TODO Auto-generated method stub
		return giftBagDao.findGiftBagByUid(uid);
	}

	@Override
	public GiftBagResult doGiftBag(String uid, String count) {
		// 接口返回对象
		GiftBagResult gbr = new GiftBagResult();

		GiftBag gb = this.giftBagDao.findGiftBagByUid(uid);
		Date now = new Date();
		if (gb == null) {
			gb = new GiftBag();
			gb.setCdate(new Date());
			gb.setUid(uid);
			giftBagDao.insert(gb);
		}
		Date lastDate = gb.getCdate();
		int countNum = Integer.parseInt(count);
		String fragment = "0";
		//是否超过每天调用接口上限 是如下
		if (gb.getDaynum() >= maxNumPerDay) {
			gbr.setStatus("2");
			gbr.setMsg("达到每天接口调用上限");
			gbr.setResult("1");
			gbr.setFragment("0");
		}
		//否 如下
		else {
			// 是否需要重置每天接口调用次数
			if (checkDates(now, lastDate)) {
				// 尚未达到一天中奖上限
				if (countNum < maxNum) {
					fragment = calculateFragment(gb, true);
					gbr.setStatus("0");
					gbr.setMsg("success");
					gbr.setFragment(fragment);
					// 中奖
					if (fragment.equals("4")) {
						gbr.setResult("0");
					}
					// 已中过奖
					else if (gb.isFragment1() && gb.isFragment2() && gb.isFragment3() && gb.isFragment4()) {
						gbr.setResult("2");
					}
					// 未中奖
					else {
						gbr.setResult("1");
					}

				} else {
					// 达到上限 不能再中奖
					fragment = calculateFragment(gb, false);
					gbr.setStatus("0");
					gbr.setMsg("达到每天中奖上限");
					gbr.setResult("1");
					gbr.setFragment(fragment);
				}
			}
			// 重置调宝接口当天调用次数
			else {
				gb.setDaynum(0);
			}
			// 更新该用户记录
			gb.setCdate(new Date());
			// 增加该用户调宝次数
			gb.setNum(gb.getNum() + 1);
			// 增加该用户当天调宝次数
			gb.setDaynum(gb.getDaynum() + 1);

			if (fragment.equals("1")) {
				gb.setFragment1(true);
			} else if (fragment.equals("2")) {
				gb.setFragment2(true);
			} else if (fragment.equals("3")) {
				gb.setFragment3(true);
			} else if (fragment.equals("4")) {
				gb.setFragment4(true);
			}
			giftBagDao.update(gb);
		}
		return gbr;
	}

	/**
	 * 计算领奖返回碎片 获得碎片 0(未获得碎片)、1、2、3、4 ；0几率=50% ；1+2+3+4几率=50%
	 * 
	 * @param gb
	 *            当前记录
	 * @param b
	 *            是否可以中奖标记 true可以中奖 ； false 不可以中奖
	 * @return
	 */
	String calculateFragment(GiftBag gb, boolean b) {
		String str = "0";
		Random rand = new Random();
		float temp = rand.nextFloat();
		// 可以中奖执行以下随机流程 ；反之可以返回0、1、2、3
		if (b) {
			// 50%未获得碎片
			if (temp > 0.51) {
				str = "0";
			}
			// 获得碎片
			// 已经获奖 依然返回未获得碎片
			else if (gb.isFragment1() && gb.isFragment2() && gb.isFragment3() && gb.isFragment4()) {
				str = "0";
			}
			// 尚未获奖
			// 尚未齐集前三片碎片 那么按照1/3概率分别获得1、2、3
			else if (!gb.isFragment1() || !gb.isFragment2() || !gb.isFragment3()) {
				if (temp <= 0.17) {
					str = "1";
				} else if (temp > 0.17 && temp <= 0.34) {
					str = "2";
				} else {
					str = "3";
				}
			}
			// 已经齐集3张碎片 还未获得第四张碎片
			else if (gb.isFragment1() && gb.isFragment2() && gb.isFragment3() && !gb.isFragment4()) {
				if (temp <= 0.125) {
					str = "1";
				} else if (temp > 0.125 && temp <= 0.25) {
					str = "2";
				} else if (temp > 0.25 && temp <= 0.375) {
					str = "3";
				} else {
					str = "4";
				}
			}
		} else {
			if (temp > 0.51) {
				str = "0";
			} else if (temp > 0.34 && temp <= 0.51) {
				str = "1";
			} else if (temp > 0.17 && temp <= 0.34) {
				str = "2";
			} else if (temp <= 0.17) {
				str = "3";
			}
		}
		return str;
	}

	@Override
	public int countNum() {
		// TODO Auto-generated method stub
		return giftBagDao.countGiftBagCurDay();
	}

	/**
	 * 比较两个日期是否是同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return true在同一天 反之不是一天
	 */
	boolean checkDates(Date d1, Date d2) {
		boolean b = false;
		String day1 = DateUtil.date2Str(d1, "yyyyMMdd");
		String day2 = DateUtil.date2Str(d2, "yyyyMMdd");
		if (day1.equals(day2)) {
			b = true;
		}
		return b;
	}
}
