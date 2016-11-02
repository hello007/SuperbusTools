package com.superbus.cok.tools.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.superbus.cok.tools.utils.DataConvertUtil;

/**
 * 序列号，里面维护了当前所有的账号信息
 * 
 * @author liuyang
 * 
 */
public class SerialNumberBean
{
	private String serialNum;
	private Map<String, AccountInfoBean> map = new HashMap<String, AccountInfoBean>();
	
	/**
	 * 维系了账号的顺序，因为map是无序的
	 */
	private List<String> idQueue = new ArrayList<String>();

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (String id : idQueue)
		{
			AccountInfoBean bean = map.get(id);
			sb.append(bean + DataConvertUtil.SPACE_SPLIT);
		}
		return sb.toString().trim();
	}

	public String getSerialNum()
	{
		return serialNum;
	}

	public void setSerialNum(String serialNum)
	{
		this.serialNum = serialNum;
	}

	public Map<String, AccountInfoBean> getMap()
	{
		return map;
	}

	public void addMap(AccountInfoBean bean)
	{
		map.put(bean.getId(), bean);
		if (!idQueue.contains(bean.getId()))
		{
			idQueue.add(bean.getId());
		}
	}
	
	public void setMap(Map<String, AccountInfoBean> map)
	{
		this.map = map;
	}

	public List<String> getIdQueue()
	{
		return idQueue;
	}

}
