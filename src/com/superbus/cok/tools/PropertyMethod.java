package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.superbus.cok.tools.constant.InfoConstant;
import com.superbus.cok.tools.utils.HttpsUtil;
import com.superbus.cok.tools.utils.PropertyUtil;

public class PropertyMethod
{
	public static void main(String[] args)
	{

	}

	public static void saveSerialNum(String serialNum)
	{
		if (serialNum == null || serialNum.length() != 32)
		{
			return;
		}
		if (!HttpsUtil.queryRegcode(serialNum))
		{
			return;
		}
		String num = PropertyUtil.loadProperty(InfoConstant.PROPERTY_USER_INFO)
				.getProperty(InfoConstant.keySerialNum);
		Set<String> set = new HashSet<String>();
		if (num != null)
		{
			String[] nums = num.split(",");
			for (String tmpNum : nums)
			{
				if (tmpNum != null && tmpNum.length() == 32
						&& !tmpNum.equals(serialNum))
				{
					set.add(tmpNum);
				}
			}
		}
		set.add(serialNum);
		StringBuffer sb = new StringBuffer();
		String[] strings = set.toArray(new String[] {});
		for (int i = 0; i < strings.length; i++)
		{
			sb.append(strings[i]);
			if (i != strings.length - 1)
			{
				sb.append(",");
			}
		}
		// 下载成功的将key保存到userInfo中
		Properties properties = new Properties();
		properties.setProperty(InfoConstant.keySerialNum, sb.toString());
		PropertyUtil.update(InfoConstant.PROPERTY_USER_INFO, properties);
	}

	public static String[] getAllKeyNum()
	{
		String num = PropertyUtil.loadProperty(InfoConstant.PROPERTY_USER_INFO)
				.getProperty(InfoConstant.keySerialNum);
		if (num == null)
		{
			return null;
		}
		return num.split(",");
	}

	public static String getCurrentNum()
	{
		String[] allNum = getAllKeyNum();
		if (allNum != null && allNum.length > 0)
		{
			return allNum[allNum.length - 1];
		}
		return "";
	}

	public static void removeSerialNum(String serialNum)
	{
		if (serialNum == null || serialNum.length() != 32)
		{
			return;
		}
		String num = PropertyUtil.loadProperty(InfoConstant.PROPERTY_USER_INFO)
				.getProperty(InfoConstant.keySerialNum);
		num = num == null ? "" : num;
		String[] nums = num.split(",");
		List<String> list = new ArrayList<String>();
		for (String tmpNum : nums)
		{
			if (tmpNum != null && tmpNum.length() == 32
					&& !tmpNum.equals(serialNum) && !list.contains(tmpNum))
			{
				list.add(tmpNum);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i));
			if (i != list.size() - 1)
			{
				sb.append(",");
			}
		}
		// 下载成功的将key保存到userInfo中
		Properties properties = new Properties();
		properties.setProperty(InfoConstant.keySerialNum, sb.toString());
		PropertyUtil.update(InfoConstant.PROPERTY_USER_INFO, properties);
	}
}
