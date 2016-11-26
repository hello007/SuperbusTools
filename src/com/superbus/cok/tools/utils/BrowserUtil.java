package com.superbus.cok.tools.utils;

import java.io.IOException;

public class BrowserUtil
{
	public static void main(String[] args)
	{
		openUrl("www.baidu1.com");
	}

	public static void openUrl(String url)
	{
		try
		{
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e)
		{
			try
			{
				String tmpUrl = url;
				if (!url.startsWith("http://"))
				{
					tmpUrl = "http://" + url;
				}
				Runtime.getRuntime().exec("explorer.exe " + tmpUrl);
			} catch (IOException e1)
			{
				MessageUtil.showInfoDialog(null, "提示", "打开浏览器地址失败，请在浏览器手动输入地址："
						+ url);
			}
		}
	}
}
