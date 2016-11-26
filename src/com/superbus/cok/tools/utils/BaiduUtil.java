package com.superbus.cok.tools.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.baidu.api.utils.HttpUtil;

public class BaiduUtil
{
	public static void main(String[] args)
	{
		download("");
	}
	
	public static void download(String fileName)
	{
		String url = "https://d.pcs.baidu.com/rest/2.0/pcs/file";
		Map<String,String> params  = new HashMap<String, String>();
        params.put("access_token", "24.c7a7604e8b861b50de53d3c3926a659a.2592000.1482336259.282335-8929809");
        params.put("method", "download");
        params.put("path", "apps");
        try
		{
			String result = HttpUtil.doGet(url, params);
			System.err.println(result);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
