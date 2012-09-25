package com.sohu.sur.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sur.service.aspect.DayLimitCacheAspect;

public abstract class MedalUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(MedalUtil.class);
	
	private static final String PREFIX = "http://s2.suc.itc.cn/i/score/img/medals/";

	public static String getTinyIconUri(String medalCode) {
		return new StringBuilder(PREFIX).append(medalCode).append("_small.gif")
				.toString();
	}

	public static String getUnearnedTinyIconUri(String medalCode) {
		if (null != medalCode) {
			String productCode = medalCode.substring(0, medalCode.indexOf('_'));
			return new StringBuilder(PREFIX).append(productCode)
					.append("_no_small.gif").toString();
		} else {
			return "";
		}

	}

	public static String getMiddleIconUri(String medalCode) {
		return new StringBuilder(PREFIX).append(medalCode).append("_big.gif")
				.toString();
	}

	public static String getUnearnedMiddleIconUri(String medalCode) {
		if (null != medalCode) {
			String productCode = medalCode.substring(0, medalCode.indexOf('_'));
			return new StringBuilder(PREFIX).append(productCode)
					.append("_no_big.gif").toString();
		} else {
			return "";
		}
	}

	public static String getMiddleExtIconUri(String medalCode) {
		return new StringBuilder(PREFIX).append(medalCode).append("_big.gif")
				.toString();
	}

	public static String getUnearnedMiddleExtIconUri(String medalCode) {
		if (null != medalCode) {
			String productCode = medalCode.substring(0, medalCode.indexOf('_'));
			return new StringBuilder(PREFIX).append(productCode)
					.append("_no_big.gif").toString();
		} else {
			return "";
		}
	}

	public static String getLargeIconUri(String medalCode) {
		return new StringBuilder(PREFIX).append(medalCode).append("_big.gif")
				.toString();
	}

	public static String getUnearnedLargeIconUri(String medalCode) {
		if (null != medalCode) {
			String productCode = medalCode.substring(0, medalCode.indexOf('_'));
			return new StringBuilder(PREFIX).append(productCode)
					.append("_no_big.gif").toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		String code = "news_01";
		logger.info(MedalUtil.getLargeIconUri(code));
		logger.info(MedalUtil.getUnearnedLargeIconUri(code));
		logger.info(MedalUtil.getTinyIconUri(code));
		logger.info(MedalUtil.getUnearnedTinyIconUri(code));
		logger.info(MedalUtil.getMiddleIconUri(code));
		logger.info(MedalUtil.getMiddleExtIconUri(code));
		logger.info(MedalUtil.getUnearnedMiddleIconUri(code));
		logger.info(MedalUtil.getUnearnedMiddleExtIconUri(code));
	}

	/**
	 * 已获得的勋章是否需要的勋章条件
	 * 
	 * @param earnedMedalCode
	 * @param neededMedalCode
	 * @return
	 */
	public static boolean allow(String earnedMedalCode, String neededMedalCode) {
		String[] earnedMedalCodePair = earnedMedalCode.split("[_]");
		String[] neededMedalCodePair = neededMedalCode.split("[_]");
		return earnedMedalCode.equals(neededMedalCode)
				|| (earnedMedalCodePair.length == 2
						&& neededMedalCodePair.length == 2
						&& earnedMedalCodePair[0]
								.equals(neededMedalCodePair[0]) && earnedMedalCodePair[1]
						.compareTo(neededMedalCodePair[1]) >= 0);
	}
}
