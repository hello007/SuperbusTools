package com.superbus.cok.tools.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CloudUtil
{
	static String token = "dc27b994292d83cda09a9695f7c719f5";
	static String key = "F941B4622B1C9A3EFF4D48FDCBA60DBE";
	
	static String testKey = "55A11A31E81A96EDD3D9E350974FFBA5";

	public static void main(String[] args) throws Exception
	{
		System.err.println(getBBYTime());
		if (key.trim().length() != 32)
		{
			System.err.println("key长度不是32");
			return;
		}
		// System.err.println(upload("这是个测试案例|第二个|三个|四个|33"));
//		System.err.println(download(key));
		System.err.println(downloadStatistics(testKey));
		// System.err.println(lockKey(key));

	}

	public static boolean checkKey(String key)
	{
		if (key != null && key.length() == 32)
		{
			return true;
		}
		return false;
	}
	
	public static String downloadStatistics(String key) throws MalformedURLException
	{
		URL url = new URL(
				"http://post.baibaoyun.com/cloudapi/cloudappapi?token=" + token
						+ "&method=queryAccount&regcode=" + key + "");
		String result = getReturnRslt(url);
		return result;
	}

	public static String download(String key) throws Exception
	{
		String result = HttpsUtil.download(key);//getReturnRslt(url);
		return result;
	}

	public static String upload(String key, String content) throws Exception
	{
		String result = HttpsUtil.upload(key, content);
		return result;
	}

	public static String getBBYTime() throws Exception
	{
		URL url = new URL(
				"http://post.baibaoyun.com/cloudapi/cloudappapi?token=" + token
						+ "&funparams=getTime");
		return getReturnRslt(url);
	}

	private static String getReturnRslt(URL url)
	{
		if(url == null)
		{
			return null;
		}
		HttpURLConnection connection = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			connection = (HttpURLConnection) url.openConnection();
			isr = new InputStreamReader(connection.getInputStream());
			reader = new BufferedReader(isr);
			String temp = "";
			while ((temp = reader.readLine()) != null)
			{
				sb.append(temp);
			}
		} catch (IOException e)
		{
			return "-1";
		} finally
		{
			try
			{
				if (reader != null)
					reader.close();
				if (isr != null)
					isr.close();
				if (connection != null)
					connection.disconnect();
			} catch (IOException e)
			{
				return "";
			}
		}
		return sb.toString();
	}
	
}
