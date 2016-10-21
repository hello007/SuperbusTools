package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.utils.StringTools;

/**
 * 采集设置： 是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm
 * 
 * @author user
 * 
 */
public class GatherBean
{
	//"0,0,0,0,0,0,1,0,0,0,6"
	private boolean isGather = false;
	private boolean wood = false;
	private boolean food = false;
	private boolean iron = false;
	private boolean silver = false;
	
	private int alias = 0;
	private int num = 1;
	private int armyOutMethod = 0;
	private int gatherAlliasType = 0;
	private int minKm = 0;
	private int maxKm = 6;

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(StringTools.convertToString(isGather) + ",");
		sb.append(StringTools.convertToString(wood) + ",");
		sb.append(StringTools.convertToString(food) + ",");
		sb.append(StringTools.convertToString(iron) + ",");
		sb.append(StringTools.convertToString(silver) + ",");
		sb.append(alias + ",");
		sb.append(num + ",");
		sb.append(armyOutMethod + ",");
		sb.append(gatherAlliasType + ",");
		sb.append(minKm + ",");
		sb.append(maxKm);
		return sb.toString();
	}

	public boolean isGather()
	{
		return isGather;
	}

	public void setGather(boolean isGather)
	{
		this.isGather = isGather;
	}

	public boolean isWood()
	{
		return wood;
	}

	public void setWood(boolean wood)
	{
		this.wood = wood;
	}

	public boolean isFood()
	{
		return food;
	}

	public void setFood(boolean food)
	{
		this.food = food;
	}

	public boolean isIron()
	{
		return iron;
	}

	public void setIron(boolean iron)
	{
		this.iron = iron;
	}

	public boolean isSilver()
	{
		return silver;
	}

	public void setSilver(boolean silver)
	{
		this.silver = silver;
	}

	public int getAlias()
	{
		return alias;
	}

	public void setAlias(int alias)
	{
		this.alias = alias;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getArmyOutMethod()
	{
		return armyOutMethod;
	}

	public void setArmyOutMethod(int armyOutMethod)
	{
		this.armyOutMethod = armyOutMethod;
	}

	public int getGatherAlliasType()
	{
		return gatherAlliasType;
	}

	public void setGatherAlliasType(int gatherAlliasType)
	{
		this.gatherAlliasType = gatherAlliasType;
	}

	public int getMinKm()
	{
		return minKm;
	}

	public void setMinKm(int minKm)
	{
		this.minKm = minKm;
	}

	public int getMaxKm()
	{
		return maxKm;
	}

	public void setMaxKm(int maxKm)
	{
		this.maxKm = maxKm;
	}

}
