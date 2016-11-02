package com.superbus.cok.tools.utils;

import com.alibaba.fastjson.JSONObject;
import com.superbus.cok.tools.bean.AccountInfoBean;
import com.superbus.cok.tools.bean.BuildingBean;
import com.superbus.cok.tools.bean.CastleInAliasBean;
import com.superbus.cok.tools.bean.GatherBean;
import com.superbus.cok.tools.bean.GoldenBean;
import com.superbus.cok.tools.bean.GroupBean;
import com.superbus.cok.tools.bean.QueueBean;
import com.superbus.cok.tools.bean.SerialNumberBean;
import com.superbus.cok.tools.bean.SolderBean;
import com.superbus.cok.tools.bean.StatisticsBean;
import com.superbus.cok.tools.bean.TechBean;
import com.superbus.cok.tools.bean.UpgradeBean;

public class DataConvertUtil
{
	/**
	 * 逗号分隔符
	 */
	public static final String DOT_SPILT = ",";

	/**
	 * 空格分隔符
	 */
	public static String SPACE_SPLIT = " ";

	/**
	 * 竖线分割线
	 */
	public static String VERTICAL_SPLIT = "\\|";

	public static SerialNumberBean syncAndFilter(String serialNum,
			String content)
	{
		/**
		 * 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置 4造兵设置： 是否造兵 //0表示false，1表示true，如;
		 * 0/1 5采集设置： 是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm 6分组设置：
		 * 分组编号，是否自定义搜索，x，y //如： 0/1/2,true/false,x,y 7升级设置： 是否升级城堡
		 * //0表示false，1表示true，如 0/1 8-金手指: //0表示false，1表示true，如 0/1
		 * 9-科技设置:是否自动科技,科技升级模式 如：0/1,0/1/2/3/4/5 10-建筑设置:是否自动建筑,建筑升级模式
		 * 如：0/1,0/1/2/3/4/5 11-城堡是否在盟区 如：0/1
		 */

		SerialNumberBean serialNumberBean = new SerialNumberBean();
		serialNumberBean.setSerialNum(serialNum);

		String[] allAccount = content.split(" ");
		for (String acc : allAccount)
		{
			if (acc == null || "".equals(acc.trim()))
			{
				continue;
			}
			AccountInfoBean infoBean = new AccountInfoBean();

			String[] data = acc.trim().split(VERTICAL_SPLIT);// 11
			int length = data.length;

			infoBean.setVersion(getNum(data[0], 20));
			infoBean.setVersionType(getNum(data[1], 5));
			infoBean.setAccount(data[2]);
			infoBean.setPassword(data[3]);

			// 4造兵设置
			infoBean.setSolderBean(getSolderBean(data[4]));
			// 5采集设置
			infoBean.setGatherBean(getGather(data[5]));
			// 6.分组设置
			infoBean.setGroupBean(getGroup(data[6]));
			// 7.升级设置
			infoBean.setUpgradeBean(getUpgradeBean(data[7]));

			// 过滤
			if (length == 8)
			{
				/**
				 * 8-金手指: //0表示false，1表示true，如 0/1 9-科技设置:是否自动科技,科技升级模式
				 * 如：0/1,0/1/2/3/4/5 10-建筑设置:是否自动建筑,建筑升级模式 如：0/1,0/1/2/3/4/5
				 * 11-城堡是否在盟区 如：0/1
				 */
				infoBean.setGoldenBean(new GoldenBean());
				infoBean.setTechBean(new TechBean());
				infoBean.setBuildingBean(new BuildingBean());
				infoBean.setCastleInAliasBean(new CastleInAliasBean());
				infoBean.setShow(true);
			} else if (length == 9)
			{
				infoBean.setGoldenBean(new GoldenBean());
				infoBean.setTechBean(new TechBean());
				infoBean.setBuildingBean(new BuildingBean());
				infoBean.setCastleInAliasBean(new CastleInAliasBean());
				infoBean.setShow(StringTools.convertToBoolean(data[length - 1]));
			} else if (length == 11 || length == 12 || length == 13)
			{
				// 8.金手指
				GoldenBean goldenBean = new GoldenBean();
				goldenBean.setGolden(StringTools
						.convertToBoolean(getNum(data[8]) + ""));
				infoBean.setGoldenBean(goldenBean);
				// 9.科技设置
				infoBean.setTechBean(getTech(data[9]));
				// 10.建筑设置
				infoBean.setBuildingBean(getBuilding(data[10]));
				if (length == 11)
				{
					infoBean.setCastleInAliasBean(new CastleInAliasBean());
					infoBean.setShow(true);
				} else if (length == 12)
				{
					infoBean.setCastleInAliasBean(new CastleInAliasBean());
					infoBean.setShow(StringTools
							.convertToBoolean(data[length - 1]));
				} else
				{
					CastleInAliasBean bean = new CastleInAliasBean();
					bean.setIn(getNum(data[11]));
					infoBean.setCastleInAliasBean(bean);
					infoBean.setShow(StringTools
							.convertToBoolean(data[length - 1]));
				}
			}
			serialNumberBean.addMap(infoBean);
		}

		return serialNumberBean;
	}

	private static SolderBean getSolderBean(String string)
	{
		SolderBean solderBean = new SolderBean();
		boolean solder = StringTools.convertToBoolean(getNum(string) + "");
		solderBean.setTrainSolder(solder);
		return solderBean;
	}

	// 建筑
	private static BuildingBean getBuilding(String in)
	{
		// 9-科技设置:是否自动科技,科技升级模式 如：0/1,0/1/2/3/4/5
		String[] data = in.split(DOT_SPILT);
		BuildingBean bean = new BuildingBean();
		int length = data.length;
		if (length == 2)
		{
			bean.setBuidling(StringTools.convertToBoolean(getNum(data[0]) + ""));
			bean.setBuildingType(getNum(data[1], 9));
		}
		return bean;
	}

	// 科技
	private static TechBean getTech(String in)
	{
		// 9-科技设置:是否自动科技,科技升级模式 如：0/1,0/1/2/3/4/5
		String[] data = in.split(DOT_SPILT);
		TechBean techBean = new TechBean();
		int length = data.length;
		if (length == 2)
		{
			techBean.setTech(StringTools.convertToBoolean(getNum(data[0]) + ""));
			techBean.setTechType(getNum(data[1], 9));
		}
		return techBean;
	}

	private static UpgradeBean getUpgradeBean(String in)
	{
		UpgradeBean upgradeBean = new UpgradeBean();
		upgradeBean.setUpgrade(StringTools.convertToBoolean(getNum(in) + ""));
		return upgradeBean;
	}

	private static GroupBean getGroup(String in)
	{
		// 分组编号，是否自定义搜索，x，y //如： 0/1/2,true/false,x,y
		String[] data = in.split(DOT_SPILT);
		GroupBean groupBean = new GroupBean();
		int length = data.length;
		if (length == 4)
		{
			groupBean.setGroupNum(getNum(data[0], 4));
			if ("true".equals(data[1]))
			{
				groupBean.setDefinReseach(true);
			} else
			{
				groupBean.setDefinReseach(false);
			}
			groupBean.setX(getNum(data[2], 1200));
			groupBean.setY(getNum(data[3], 1200));
		}
		return groupBean;
	}

	private static GatherBean getGather(String in)
	{
		// 0是否采集，1木，2粮，3铁，4银，5联盟矿，6队伍数量，7野矿出兵方式，8盟矿出兵方式（新增），9minKm，10maxKm
		// length:10--->11
		GatherBean gatherBean = new GatherBean();
		String[] data = in.split(DOT_SPILT);// 10 or 11
		int length = data.length;
		if (length == 10 || length == 11)
		{
			gatherBean.setGather(StringTools.convertToBoolean(getNum(data[0])
					+ ""));
			gatherBean.setWood(StringTools.convertToBoolean(getNum(data[1])
					+ ""));
			gatherBean.setFood(StringTools.convertToBoolean(getNum(data[2])
					+ ""));
			gatherBean.setIron(StringTools.convertToBoolean(getNum(data[3])
					+ ""));
			gatherBean.setSilver(StringTools.convertToBoolean(getNum(data[4])
					+ ""));
			gatherBean.setAlias(getNum(data[5], 2));
			gatherBean.setNum(getNum(data[6], 6));
			gatherBean.setArmyOutMethod(getNum(data[7], 4));
			// 8开始注意区分即可
			if (length == 10)
			{
				gatherBean.setGatherAlliasType(0);
				gatherBean.setMinKm(getNum(data[8], 30));
				gatherBean.setMaxKm(getNum(data[9], 30));
			} else if (length == 11)
			{
				gatherBean.setGatherAlliasType(getNum(data[8], 2));
				gatherBean.setMinKm(getNum(data[9], 30));
				gatherBean.setMaxKm(getNum(data[10], 30));
			}
			return gatherBean;
		}
		return gatherBean;
	}

	private static int getNum(String in)
	{
		return getNum(in, 1);
	}

	private static int getNum(String in, int max)
	{
		try
		{
			int i = Integer.valueOf(in);
			if (i >= 0 && i <= max)
			{
				return i;
			}
		} catch (NumberFormatException e)
		{
		}
		return 0;
	}

	private static String[] address = new String[] { "账号", "领主名", "领主等级",
			"城堡等级", "战力", "金币", "城防", "总木", "安全木", "总粮", "安全粮", "总铁", "安全铁",
			"总银", "安全银", "兵数", "VIP状态", "VIP等级", "平台", "版本", "本地时间", "有效队列",
			"世界", "坐标" };

	private static String[] blockMeaing = new String[] { "盟矿或野矿", "采集种类",
			"采集数量", "采集速率", "采集速度" };

	public static StatisticsBean getStatisticsBean(JSONObject object)
	{
		StatisticsBean bean = new StatisticsBean();
		if(object == null)
		{
			return null;
		}

		for (int i = 0; i < address.length; i++)
		{
			String zh = address[i];
			String zhResult = object.getString(zh);
			if ("有效队列".equals(zh))
			{
				if (zhResult == null || zhResult.equals(""))
				{
					continue;
				}
				int totle = Integer.parseInt(zhResult);
				for (int j = 0; j < totle; j++)
				{
					zhResult = object.getString("第" + (1 + j) + "队");
					if (zhResult != null && !zhResult.equals(""))
					{
						QueueBean queueBean = new QueueBean();
						String[] queueInfo = zhResult.split("[|]");
						if (queueInfo.length == 8)
						{
							for (int k = 0; k < blockMeaing.length; k++)
							{
								queueBean.addChinese(blockMeaing[k]);
								queueBean.addResult(queueInfo[k]);
							}
						}
						bean.addQueueList(queueBean);
					}
				}
				continue;
			}
			bean.addMap(zh, zhResult);
		}

		return bean;
	}
}
