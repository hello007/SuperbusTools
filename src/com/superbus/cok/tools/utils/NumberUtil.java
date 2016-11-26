package com.superbus.cok.tools.utils;

import java.text.NumberFormat;

public class NumberUtil
{
	public static String formatToPoint(String basic)
	{
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		String result = basic;
		try
		{
			int num = Integer.parseInt(basic);
			result = numberFormat.format(num);
		} catch (Exception e)
		{
		}
		return result;
	}
}
