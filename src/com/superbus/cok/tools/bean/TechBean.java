package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.utils.StringTools;

/**
 * 9-科技设置:是否自动科技,科技升级模式 如：0/1,0/1/2/3/4/5
 * 
 * @author liuyang
 * 
 */
public class TechBean
{
	private boolean isTech = false;

	private int techType = 0;

	@Override
	public String toString()
	{
		return StringTools.convertToString(isTech) + "," + techType;
	}

	public boolean isTech()
	{
		return isTech;
	}

	public void setTech(boolean isTech)
	{
		this.isTech = isTech;
	}

	public int getTechType()
	{
		return techType;
	}

	public void setTechType(int techType)
	{
		techType = techType < 0 ? 0 : techType;
		this.techType = techType;
	}

}
