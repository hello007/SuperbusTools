package com.superbus.cok.tools.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil
{
	public static void main(String[] args)
	{
		Properties pro = loadProperty("property/language.properties");
		System.err.println(pro.getProperty("age"));
	}

	/**
	 * 加载配置文件
	 * 
	 * @param fileName
	 *            文件路径，如property/a.properties
	 */
	public static Properties loadProperty(String fileName)
	{
		Properties prop = new Properties();
		try
		{
			InputStream in = new FileInputStream(fileName);
			prop.load(in);
			return prop;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * 保存修改后的配置文件
	 */
	public static void saveProperty(String fileName, Map<String, String> map)
	{
		try
		{
			OutputStream fos = new FileOutputStream(fileName);
			Properties prop = new Properties();
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				prop.setProperty(key, value);
			}
			prop.store(fos, DateUtil.getCurDate() + "保存配置文件");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveProperty(String fileName, Properties prop)
	{
		try
		{
			OutputStream fos = new FileOutputStream(fileName);
			prop.store(fos, DateUtil.getCurDate() + "保存配置文件");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 对配置文件的修改
	 * 
	 * @throws IOException
	 */
	public static void update(String fileName, Map<String, String> map)
	{
		try
		{
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(fileName);
			FileOutputStream fos = new FileOutputStream(fileName);
			properties.load(fis);
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				properties.setProperty(key, value);
			}
			properties.store(fos, DateUtil.getCurDate() + "更新配置文件");

			fos.close();

			fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 对配置文件的修改
	 * 
	 * @throws IOException
	 */
	public static void update(String fileName, Properties prop)
	{
		try
		{
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(fileName);
			FileOutputStream fos = new FileOutputStream(fileName);
			properties.load(fis);
			properties.putAll(prop);
			properties.store(fos, DateUtil.getCurDate() + "更新配置文件");

			fos.close();

			fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
