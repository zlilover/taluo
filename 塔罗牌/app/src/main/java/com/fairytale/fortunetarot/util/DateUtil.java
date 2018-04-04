package com.fairytale.fortunetarot.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zli
 */
public class DateUtil {

	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat format_point = new SimpleDateFormat("yyyy.MM.dd");
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static long currentTimeMillis = 0;

	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/*
	 * 获取当前日期
	 */
	public static String GetNowDate() {
		Date dt = new Date();
		return format.format(dt);
	}

	/*
	 * 获取当前日期
	 */
	public static String GetNowDateWithPoint() {
		Date dt = new Date();
		return format_point.format(dt);
	}

	/*
	将指定日期转化为指定格式的时间
	 */
	public static String getDate(Date date) {
		if (date == null) {
			return "";
		}
		return format.format(date);
	}

	public static String getDateBy19(Date date) {
		if (date == null) {
			return "";
		}
		return df.format(date);
	}

	// 时间比较
	public static long distance(String date) {
		try {
			Date d = df.parse(date);
			long diff = d.getTime() - new Date().getTime();
			return diff/1000;
		} catch (Exception e) {
		}
		return 0;
	}

	//获取当前距离凌晨的时间数 单位milliseconds
	public static long distanceFromLC(){
		String lc = getDate(new Date()) + " " + "00:00:00";
		try {
			long distance = new Date().getTime() - df.parse(lc).getTime();
			Logger.e("distance",distance+"");
			return distance;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/*
	 * 获取当前日期的前后一个月
	 */
	public static String getPeriodDate(Calendar c, boolean beforeOrAfter) {
		if (c == null) {
			return GetNowDate();
		}
		if (beforeOrAfter) {
			c.add(Calendar.MONTH, -1);
		} else {
			c.add(Calendar.MONTH, 1);
		}
		Date date = c.getTime();
		String s = format.format(date);
		return s;
	}

	/**
	 *
	 * @param day 正数往后推，负数往前推
	 * @return
	 */
	public static String getDayFromToday(int day) {
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		String dateString = format.format(date);
		return dateString;
	}

	/**
	 * 比较指定时间于当前时间的先后顺序，true大于现在日期
	 * @param date
	 * @return
	 */
	public static boolean diff(String date) {
		try {
			Date d = df.parse(date);
			long diff = d.getTime() - new Date().getTime();
			if (diff > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static String getDate(Calendar calendar){
		if (calendar==null){
			return GetNowDate();
		}
		Date date = calendar.getTime();
		String s = format.format(date);
		return s;
	}

	/*
	 * 获取当前时间
	 */
	public static String GetNowTime() {
		Date dt = new Date();
		String time = df.format(dt);
		return time.substring(11);
	}

	public static String getNowTime() {
		Date dt = new Date();
		String time = df.format(dt);
		return time;
	}

	public static String getXListViewTime(long s1, long e) {
		if (s1 == 0 || e == 0 || e - s1 == 0) {
			return "刚刚";
		}
		long l = e - s1;
		try {
			long day = l / (24 * 60 * 60 * 1000);
			if (day != 0) {
				return "很久以前";
			}
			long hour = (l / (60 * 60 * 1000) - day * 24);
			if (hour != 0) {
				return hour + "小时前";
			}
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			if (min != 0) {
				return min + "分钟前";
			}
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (s > 15) {
				return s + "秒前";
			}
		} catch (Exception ex) {
		}
		return "刚刚";
	}

	public static String distanceTimeByHour(String t1, String t2) {
		if (TextUtils.isEmpty(t1) || TextUtils.isEmpty(t2)) {
			return null;
		}
		String time = "";
		try {
			Date now = df.parse(t1);
			Date date = df.parse(t2);
			long l = now.getTime() - date.getTime();
			long hour = l / (60 * 60 * 1000);
			if (hour <= 0) {
				time = "1小时内";
			}
			else {
				time = hour + "小时前";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String distanceTimeByDay(String t1, String t2) {
		if (TextUtils.isEmpty(t1) || TextUtils.isEmpty(t2)) {
			return null;
		}
		String time = "";
		try {
			Date now = df.parse(t1);
			Date date = df.parse(t2);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (day != 0) {
				time = day + "天前";
			}
			else {
				time = "今天";
			}
//			if (hour != 0) {
//				time += hour + "小时";
//			}
//			if (day == 0 && hour == 0 && min < 10) {
//				return "一小时内";
//			}
//			else {
//				time += min + "分";
//			}

			/*if (s != 0) {
				time += s + "秒";
			}*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String distanceTime(String t1, String t2) {
		if (TextUtils.isEmpty(t1) || TextUtils.isEmpty(t2)) {
			return null;
		}
		String time = "";
		try {
			Date now = df.parse(t1);
			Date date = df.parse(t2);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (day != 0) {
				time = day + "天";
			}
			if (hour != 0) {
				time += hour + "小时";
			}
			if (day == 0 && hour == 0 && min < 10) {
				return "一小时内";
			}
			else {
				time += min + "分";
			}

			/*if (s != 0) {
				time += s + "秒";
			}*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time + "前";
	}

	public static String distanceTime(String t1, String t2,boolean isShowJustNow) {
		if (TextUtils.isEmpty(t1) || TextUtils.isEmpty(t2)) {
			return null;
		}
		String time = "";
		try {
			Date now = df.parse(t1);
			Date date = df.parse(t2);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (day != 0) {
				time = day + "天";
			}
			if (hour != 0) {
				time += hour + "小时";
			}
			if (day == 0 && hour == 0 && min < 10) {
				if (isShowJustNow) {
					return "刚刚";
				}
				time = min+"分";
			}
			else {
				time += min + "分";
			}

			/*if (s != 0) {
				time += s + "秒";
			}*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time + "前";
	}

	public static Calendar stringToDate(String str) {
		Calendar calendar = null;
		try {
			Date date = format.parse(str);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Calendar cal) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 屏蔽3s内连续点击
	 * @return
	 */
	public static boolean delayBtnClick(){
		if ((System.currentTimeMillis() - currentTimeMillis) < 3000) {
			return true;
		}
		currentTimeMillis = System.currentTimeMillis();
		return false;
	}

	/**
	 * 将日期转化为星期
	 * @param date
	 * @return
     */
	public static String getWeekOfDate(Date date) {
		String[] weekDaysName = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	public static String getMonthAndDayWithDate(String date) {
		if (TextUtils.isEmpty(date) || date.length() < 10) {
			return "";
		}
		return date.substring(5,7) + "月"+ date.substring(8,10) + "日";
	}

	public static String getMonth() {
		String date = GetNowDate();
		return date.substring(5,7);
	}

	public static String getMonthWithNo0(){
		String month = GetNowDate().substring(5,7);
		if (month.startsWith("0")) {
			return month.substring(1,month.length());
		}
		return month;
	}

	public static String getDay() {
		String date = GetNowDate();
		return date.substring(8,10);
	}

	public static String getDayWithNo0(){
		String day = GetNowDate().substring(8,10);
		if (day.startsWith("0")) {
			return day.substring(1,day.length());
		}
		return day;
	}


	public static String getMonthAndDayWithDateAndTime(String date) {
		if (TextUtils.isEmpty(date) || date.length() < 16) {
			return "";
		}
		return getMonthAndDayWithDate(date) + " " + date.substring(11,16);
	}

	public static String getMonthAndDayWithDateAndTime(Date sourceDate) {
		String date = getDateBy19(sourceDate);
		if (TextUtils.isEmpty(date) || date.length() < 16) {
			return "";
		}
		return getMonthAndDayWithDate(date) + " " + date.substring(11,16);
	}

}
