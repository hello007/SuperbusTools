package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.utils.StringTools;

/**
 * 升级设置： 是否升级城堡 //0表示false，1表示true，如 0/1
 * 
 * @author liuyang
 * 
 */
public class UpgradeBean
{
	private boolean upgrade = false;

	@Override
	public String toString()
	{
		return StringTools.convertToString(upgrade);
	}

	public boolean isUpgrade()
	{
		return upgrade;
	}

	public void setUpgrade(boolean upgrade)
	{
		this.upgrade = upgrade;
	}

}
