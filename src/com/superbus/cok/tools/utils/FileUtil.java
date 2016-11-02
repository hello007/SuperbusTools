package com.superbus.cok.tools.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FileUtil
{
	public static void main(String[] args)
	{
		String fileName = "F:\\cok_列王的纷争\\配置工具\\exe\\class_syncdb.txt";
		readFileByBuffer(fileName);
	}

	public static void readFileByBuffer(String fileName)
	{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String line;
		Set<String> set = new HashSet<String>();
		try
		{
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null)
			{
				if(line.contains("superbus"))
				{
					continue;
				}
				String result = line + "\n";
				String[] strings = result.split(" from ");
				if (strings.length == 2)
				{
					set.add(strings[1]);
				} else
				{
					set.add(result);
				}
				sb.append(result);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (fis != null)
					fis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.err.println(set);

	}
}
