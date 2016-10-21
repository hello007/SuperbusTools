package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.DataConstant;

/**
 * 每个账号维护一个总的javaBean
 * 
 * @author user
 * 
 */
public class AccountInfoBean
{
	/**
	 * 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置 4造兵设置： 是否造兵 //0表示false，1表示true，如; 0/1
	 * 5采集设置： 是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm 6分组设置：
	 * 分组编号，是否自定义搜索，x，y //如： 0/1/2,true/false,x,y 7升级设置： 是否升级城堡
	 * //0表示false，1表示true，如 0/1 8-金手指: //0表示false，1表示true，如 0/1
	 * 9-科技设置:是否自动科技,科技升级模式 如：0/1,0/1/2/3/4/5 10-建筑设置:是否自动建筑,建筑升级模式
	 * 如：0/1,0/1/2/3/4/5 11-城堡是否在盟区 如：0/1
	 */

	private int version = 0;
	private int versionType = 0;
	private String account = "";
	private String password = "";
	private SolderBean solderBean;// 4
	private GatherBean gatherBean;// 5
	private GroupBean groupBean;// 6
	private UpgradeBean upgradeBean;
	private GoldenBean goldenBean;// 8
	private TechBean techBean;
	private BuildingBean buildingBean;
	private CastleInAliasBean castleInAliasBean;// 11

	private boolean show = true;

	@Override
	public String toString()
	{
		// 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置|8-金手指|9-科技设置|10-建筑设置|11-城堡是否在盟区
		Object[] objects = new Object[] { version, versionType, account,
				password, solderBean, gatherBean, groupBean, upgradeBean,
				goldenBean, techBean, buildingBean, castleInAliasBean };
		StringBuffer sb = new StringBuffer();
		for (Object object : objects)
		{
			sb.append(object + "|");
		}
		sb.append(show ? "1" : "0");
		return sb.toString();
	}

	/**
	 * 当前账号的唯一标识
	 */
	public String getId()
	{
		String tmp = DataConstant.getInstance().getAccountTypeData()[version];
		return account + tmp.substring(tmp.indexOf("-"));
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public int getVersionType()
	{
		return versionType;
	}

	public void setVersionType(int versionType)
	{
		this.versionType = versionType;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public SolderBean getSolderBean()
	{
		return solderBean;
	}

	public void setSolderBean(SolderBean solderBean)
	{
		this.solderBean = solderBean;
	}

	public GatherBean getGatherBean()
	{
		return gatherBean;
	}

	public void setGatherBean(GatherBean gatherBean)
	{
		this.gatherBean = gatherBean;
	}

	public GroupBean getGroupBean()
	{
		return groupBean;
	}

	public void setGroupBean(GroupBean groupBean)
	{
		this.groupBean = groupBean;
	}

	public UpgradeBean getUpgradeBean()
	{
		return upgradeBean;
	}

	public void setUpgradeBean(UpgradeBean upgradeBean)
	{
		this.upgradeBean = upgradeBean;
	}

	public GoldenBean getGoldenBean()
	{
		return goldenBean;
	}

	public void setGoldenBean(GoldenBean goldenBean)
	{
		this.goldenBean = goldenBean;
	}

	public TechBean getTechBean()
	{
		return techBean;
	}

	public void setTechBean(TechBean techBean)
	{
		this.techBean = techBean;
	}

	public BuildingBean getBuildingBean()
	{
		return buildingBean;
	}

	public void setBuildingBean(BuildingBean buildingBean)
	{
		this.buildingBean = buildingBean;
	}

	public CastleInAliasBean getCastleInAliasBean()
	{
		return castleInAliasBean;
	}

	public void setCastleInAliasBean(CastleInAliasBean castleInAliasBean)
	{
		this.castleInAliasBean = castleInAliasBean;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

}
