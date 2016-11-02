package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.utils.StringTools;

/**
 * 10-建筑设置:是否自动建筑,建筑升级模式 如：0/1,0/1/2/3/4/5
 * 
 * @author liuyang
 * 
 */
public class BuildingBean
{
	private boolean isBuidling = false;

	private int buildingType = 0;

	@Override
	public String toString()
	{
		return StringTools.convertToString(isBuidling) + "," + buildingType;
	}

	public boolean isBuidling()
	{
		return isBuidling;
	}

	public void setBuidling(boolean isBuidling)
	{
		this.isBuidling = isBuidling;
	}

	public int getBuildingType()
	{
		return buildingType;
	}

	public void setBuildingType(int buildingType)
	{
		buildingType = buildingType < 0 ? 0 : buildingType;
		this.buildingType = buildingType;
	}

}
