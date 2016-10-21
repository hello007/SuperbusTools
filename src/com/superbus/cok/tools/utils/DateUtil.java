package com.superbus.cok.tools.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil
{
	public static String getCurDate()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	public static String getDate(String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
	
	public static String formatUnixTime(long time)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date(time * 1000));
		return date;
	}
}
