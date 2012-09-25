package com.sohu.sur.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * 活跃度cache存活时间计算
 * @author xuewuhao
 *
 */
public class DateUtils {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private DateUtils() {
	}

	public static int calculateSecondsToLive() {
		DateTime now = new DateTime();
		DateTime tomorrow = now.plusDays(1);
		MutableDateTime mdt = tomorrow.toMutableDateTime();
		mdt.setSecondOfDay(0);
		mdt.setMillisOfDay(0);
		Period period = new Period(now, mdt.toDateTime(), PeriodType.seconds());
		return period.getSeconds();
	}

	/**
	 * 距离第二天0点的秒数
	 * @return
	 */
	public static Long liveSeconds() {
		Date now = new Date();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
		calendarEnd.set(Calendar.MINUTE, 0);
		calendarEnd.set(Calendar.SECOND, 0);
		return (calendarEnd.getTimeInMillis() - now.getTime())/1000;
	}

	/**
	 * 将日期改为字符串
	 * @param date
	 * @return
	 */
	public static String date2Str(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.format(date);
	}

	public static void main(String args[]) {
		long time = liveSeconds();
		System.out.println(time / 1000.0 / 60.0 / 60.0);
	}
}
