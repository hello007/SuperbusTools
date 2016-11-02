package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.List;

public class DataConstant
{
	private static DataConstant dataConstant = new DataConstant();

	public static DataConstant getInstance()
	{
		return dataConstant;
	}

	public DataConstant()
	{
		initData();
	}

	// 下拉框初始化数据
	private List<String> accountTypeData = new ArrayList<String>();
	private List<String> armyOutMethodData = new ArrayList<String>();
	private List<String> gatherAlliasMethodData = new ArrayList<String>();
	private List<String> gatherAlliasTypeData = new ArrayList<String>();
	private List<String> armyOutNumData = new ArrayList<String>();
	private List<String> gatherDistanceData = new ArrayList<String>();
	private List<String> versionData = new ArrayList<String>();
	private List<String> groupInfoData = new ArrayList<String>();
	private List<String> upgradeTechData = new ArrayList<String>();
	private List<String> upgradeBuildingData = new ArrayList<String>();
	private List<String> castleInAlliasData = new ArrayList<String>();

	private void initData()
	{
		accountTypeData.add("0-微博版");
		accountTypeData.add("1-益玩版");
		accountTypeData.add("2-九游版");
		accountTypeData.add("3-百度版");
		accountTypeData.add("4-猎宝版");
		accountTypeData.add("5-果盘版");
		accountTypeData.add("6-安峰版");
		accountTypeData.add("7-拇指玩版");
		accountTypeData.add("8-微博游戏中心版");
		accountTypeData.add("9-51wan版");
		accountTypeData.add("10-小米互娱版");
		accountTypeData.add("11-搜狗版");
		accountTypeData.add("12-Facebook(微博版)");
		accountTypeData.add("13-Facebook(国际版)");
		accountTypeData.add("14-豌豆荚版");

		armyOutMethodData.add("默认出兵采集野矿");
		armyOutMethodData.add("最后一队最大出兵");
		armyOutMethodData.add("每队都最大出兵");
		armyOutMethodData.add("每队都使用编队1");
		armyOutMethodData.add("每队都使用编队2");

		gatherAlliasMethodData.add("默认出兵采集盟矿");
		gatherAlliasMethodData.add("编队1采集盟矿");
		gatherAlliasMethodData.add("编队2采集盟矿");

		gatherAlliasTypeData.add("不采集联盟矿");
		gatherAlliasTypeData.add("优先采集联盟矿");
		gatherAlliasTypeData.add("最后一队采集联盟矿");

		armyOutNumData.add("1队");
		armyOutNumData.add("2队");
		armyOutNumData.add("3队");
		armyOutNumData.add("4队");
		armyOutNumData.add("5队");

		for (int i = 0; i < 270; i = i + 10)
		{
			gatherDistanceData.add(i + " km");
		}

		versionData.add("版本号2.0.12");
		versionData.add("版本号2.0.13--2.0.17");
		versionData.add("版本号2.0.18--2.5.0");

		groupInfoData.add("0-不分组");
		groupInfoData.add("1-归属分组1");
		groupInfoData.add("2-归属分组2");
		groupInfoData.add("3-归属分组3");
		groupInfoData.add("4-归属分组4");

		upgradeTechData.add("小号科技一条龙");
		upgradeTechData.add("专升资源");
		upgradeTechData.add("专升发展");
		upgradeTechData.add("专升城防");
		upgradeTechData.add("专升军事");
		upgradeTechData.add("专升装备");
		upgradeTechData.add("专升高级军事");
		upgradeTechData.add("专升医疗");
		upgradeTechData.add("专升精锐部队");

		upgradeBuildingData.add("学院");
		upgradeBuildingData.add("校场");
		upgradeBuildingData.add("行军帐篷");
		upgradeBuildingData.add("急救帐篷");
		upgradeBuildingData.add("木粮铁银");
		upgradeBuildingData.add("大使馆");
		upgradeBuildingData.add("许愿池");
		upgradeBuildingData.add("车兵营");

		castleInAlliasData.add("城堡不在盟区内");
		castleInAlliasData.add("城堡在盟区内");
	}

	public String[] getAccountTypeData()
	{
		return accountTypeData.toArray(new String[] {});
	}

	public String[] getArmyOutMethodData()
	{
		return armyOutMethodData.toArray(new String[] {});
	}

	public String[] getSubAccountTypeData()
	{
		String[] result = new String[accountTypeData.size()];
		for (int i = 0; i < accountTypeData.size(); i++)
		{
			String data = accountTypeData.get(i);
			String type = data.split("-")[1];
			result[i] = type;
		}
		return result;
	}

	public String[] getGatherAlliasMethodData()
	{
		return gatherAlliasMethodData.toArray(new String[] {});
	}

	public String[] getGatherAlliasTypeData()
	{
		return gatherAlliasTypeData.toArray(new String[] {});
	}

	public String[] getArmyOutNumData()
	{
		return armyOutNumData.toArray(new String[] {});
	}

	public String[] getGatherDistanceData()
	{
		return gatherDistanceData.toArray(new String[] {});
	}

	public String[] getVersionData()
	{
		return versionData.toArray(new String[] {});
	}

	public String[] getGroupInfoData()
	{
		return groupInfoData.toArray(new String[] {});
	}

	public String[] getUpgradeTechData()
	{
		return upgradeTechData.toArray(new String[] {});
	}

	public String[] getUpgradeBuildingData()
	{
		return upgradeBuildingData.toArray(new String[] {});
	}

	public String[] getCastleInAlliasData()
	{
		return castleInAlliasData.toArray(new String[] {});
	}

}
