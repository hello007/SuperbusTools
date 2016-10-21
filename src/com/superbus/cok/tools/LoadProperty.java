package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.superbus.cok.tools.constant.InfoConstant;
import com.superbus.cok.tools.utils.PropertyUtil;

public class LoadProperty
{
	private static LoadProperty property = new LoadProperty();

	public static LoadProperty getInstance()
	{
		return property;
	}

	private List<String> langList = new ArrayList<String>();

	private String language;

	private Properties properties = new Properties();

	public LoadProperty()
	{
		initLangList();
		language = getLanguage();
		// 根据语言加载Properties
		Properties tmpProperties = PropertyUtil.loadProperty("property/" + language
				+ InfoConstant.DOT_PROPERTIES);
		if(tmpProperties != null)
		{
			properties = tmpProperties;
		}
	}

	private void initLangList()
	{
		langList.add(InfoConstant.EN_US);
		langList.add(InfoConstant.ZH_CN);
		langList.add(InfoConstant.ZH_TW);
	}

	private String getLanguage()
	{
		String language = "zh_CN";
		Properties properties = PropertyUtil
				.loadProperty(InfoConstant.PROPERTY_LANGUAGE);
		if (properties != null)
		{
			String temp = properties.getProperty(InfoConstant.KEY_LANGUAGE);
			if (langList.contains(temp))
			{
				return temp;
			}
		}
		return language;
	}
	
	public Properties getProperties()
	{
		return properties;
	}
}
