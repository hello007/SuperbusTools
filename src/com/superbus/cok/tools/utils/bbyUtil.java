package com.superbus.cok.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@SuppressWarnings("deprecation")
public class bbyUtil
{
	static String token = "dc27b994292d83cda09a9695f7c719f5";
	static String url = "http://post.baibaoyun.com/cloudapi/cloudappapi";
	static String key = "F941B4622B1C9A3EFF4D48FDCBA60DBE";

	static String testKey = "55A11A31E81A96EDD3D9E350974FFBA5";
	
	public static void main(String[] args)
	{
		url = "http://post.baibaoyun.com/api/dc27b994292d83cda09a9695f7c719f5";
        String download = bbyUtil.getResult(url,"queryAccount",testKey);
        System.err.println(download);
	}
	
	public static final String EXCEPTION_CODE = "-1";

	public final static String bbynetUrlUpload = "http://apicloudupload.baibaoyun.com/cloudupload.php?access_token=dc27b994292d83cda09a9695f7c719f5&method=upload&";

	public final static String access_token = "dc27b994292d83cda09a9695f7c719f5";

	public final static String method = "upload";

	@SuppressWarnings("resource")
	public static String getResult(final String url, final String... data)
	{
		final StringBuffer sb = new StringBuffer();
		Thread t = new Thread()
		{
			public void run()
			{
				// 生成请求对象
				HttpPost httpRequst = new HttpPost(url);

				HttpClient httpClient = new DefaultHttpClient();
				// 发送请求
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("method", data[0]));
				params.add(new BasicNameValuePair("regcode", data[1]));
				try
				{
					httpRequst.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse response = httpClient.execute(httpRequst);
					// 显示响应
					String result = showResponseResult(response);// 一个私有方法，将响应结果显示出来
					sb.append(result);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		t.start();
		try
		{
			t.join();
			return sb.toString();
		} catch (InterruptedException e)
		{

		}
		return EXCEPTION_CODE;
	}

	public static String getResultPost(final String url, final String context)
	{
		final StringBuffer sb = new StringBuffer();

		Thread t = new Thread()
		{
			public void run()
			{
				// 生成请求对象
				try
				{
					FileBody fileBody = new FileBody(new File(context));
					MultipartEntityBuilder builder = MultipartEntityBuilder
							.create();
					builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
					builder.addPart("file", fileBody);
					HttpPost httpPost = new HttpPost(url);
					HttpClient httpClient = new DefaultHttpClient();
					httpPost.setEntity(builder.build());
					HttpResponse response = httpClient.execute(httpPost);
					// 显示响应
					HttpEntity resEntity = response.getEntity();
					System.out.println(EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
					String result = showResponseResult(response);// 一个私有方法，将响应结果显示出来
					sb.append(result);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		t.start();
		try
		{
			t.join();
			return sb.toString();
		} catch (InterruptedException e)
		{

		}
		return EXCEPTION_CODE;
	}

	/**
	 * 显示响应结果到命令行和TextView
	 * 
	 * @param response
	 */
	public static String showResponseResult(HttpResponse response)
	{
		if (null == response)
		{
			return EXCEPTION_CODE;
		}

		HttpEntity httpEntity = response.getEntity();
		try
		{
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String result = "";
			String line = "";
			while (null != (line = reader.readLine()))
			{
				result += line;

			}

			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return EXCEPTION_CODE;
	}

	public static String saveBbyFile(String filename, String content)
	{
		try
		{
			// MultipartEntity Entity = new MultipartEntity();
			String result = bbyUtil.getResultPost(bbynetUrlUpload, content);
			return result;
		} catch (Exception e)
		{
			return "";
		}

	}
}
