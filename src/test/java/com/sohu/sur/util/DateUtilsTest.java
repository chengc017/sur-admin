package com.sohu.sur.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * 
 * @author xuewuhao
 *
 */
public class DateUtilsTest {
	@Test
	public void testCalculateSecondsToLive() throws Exception {

		System.out.println(DateUtils.calculateSecondsToLive());

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime dt1 = fmt.parseDateTime("2011-03-28 13:53:10");
		DateTime dt2 = fmt.parseDateTime("2011-03-28 09:53:10");

		System.out.println(Days.daysBetween(dt1, new DateTime()).getDays());

		System.out.println(Days.daysBetween(dt2, new DateTime()).getDays());

		System.out.println(Days.daysBetween(dt1.toDateMidnight(), new DateTime().toDateMidnight()).getDays());

		System.out.println(Days.daysBetween(dt2.toDateMidnight(), new DateTime().toDateMidnight()).getDays());

	}

	/**
	 * 测试Days的d.getDays()值
	 */
	@Test
	public void testDaysBetween() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt1 = fmt.parseDateTime("2012-08-06 23:53:10");
		Days d = Days.daysBetween(dt1.toDateMidnight(), new DateTime(new Date()).toDateMidnight());
		System.out.println(d.getDays());
	}
}
