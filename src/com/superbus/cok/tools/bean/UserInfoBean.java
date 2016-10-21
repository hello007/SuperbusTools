package com.superbus.cok.tools.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * every one can use many serial numbers,every number have many accounts
 * 
 * @author liuyang
 * 
 */
public class UserInfoBean
{
	private String user;
	private String password;
	private Map<String, SerialNumberBean> map = new HashMap<String, SerialNumberBean>();

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Map<String, SerialNumberBean> getMap()
	{
		return map;
	}

	public void addMap(SerialNumberBean bean)
	{
		this.map.put(bean.getSerialNum(), bean);
	}

}
