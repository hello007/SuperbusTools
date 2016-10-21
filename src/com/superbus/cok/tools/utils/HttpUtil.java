package com.superbus.cok.tools.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superbus.cok.tools.bean.StatisticsBean;

public class HttpUtil
{
	static String token = "dc27b994292d83cda09a9695f7c719f5";
	static String url = "http://post.baibaoyun.com/api/dc27b994292d83cda09a9695f7c719f5";
	static String key = "F941B4622B1C9A3EFF4D48FDCBA60DBE";

	static String testKey = "55A11A31E81A96EDD3D9E350974FFBA5";

	public static void main(String[] args) throws Exception
	{
		System.err.println(queryAccount(url));
	}

	public static List<StatisticsBean> queryAccount(String url)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("method", "queryAccount");
		map.put("regcode", testKey);
		// url =
		// "http://post.baibaoyun.com/api/dc27b994292d83cda09a9695f7c719f5";
		return transferStatisticsResult(http(url, map));
	}

	public static String http(String url, Map<String, String> params)
	{
		URL u = null;
		HttpURLConnection con = null;

		String paramResult = "";
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null)
		{
			for (Entry<String, String> e : params.entrySet())
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			paramResult = sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		System.out.println("send_data:" + paramResult);
		// paramResult =
		// "token=dc27b994292d83cda09a9695f7c719f5&funparams=getTime";

		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			// 设置通用的请求属性
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(paramResult);
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private static String transferDownloadResult(String result)
	{
		return result.replace("_", "%").replace("%30", "#").replace("%40", "*")
				.replace("%124", "|").replace("%20", " ");
	}

	private static List<StatisticsBean> transferStatisticsResult(String result)
	{
		List<StatisticsBean> accList = new ArrayList<StatisticsBean>();
		String[] accounts = result.split("[&]");
		for (String account : accounts)
		{
			account = account.replaceAll("~", ",");
			JSONObject jsonObject = JSONObject.parseObject(account);
			accList.add(DataConvertUtil.getStatisticsBean(jsonObject));
		}
		return accList;
	}
}
