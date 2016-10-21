package com.superbus.cok.tools.utils;

public class StringTools
{
	public static String convertToString(boolean b)
	{
		return b ? "1" : "0";
	}

	public static boolean convertToBoolean(String string)
	{
		if (string == null || "".equals(string))
		{
			return false;
		}
		string = string.trim();
		if ("1".equals(string) || "true".equals(string))
		{
			return true;
		} else if ("0".equals(string) || "false".equals(string))
		{
			return false;
		} else
		{
			try
			{
				int num = Integer.parseInt(string);
				if (num > 0)
				{
					return true;
				}
			} catch (NumberFormatException e)
			{

			}
		}
		return false;
	}
}
