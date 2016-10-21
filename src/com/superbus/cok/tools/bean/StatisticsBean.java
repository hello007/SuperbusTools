package com.superbus.cok.tools.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.superbus.cok.tools.utils.DateUtil;

/**
 * {"第5队":"","城堡等级":"","第3队":"","第1队":"","金币":"110","安全木":"","坐标":"","领主名":
 * "Empire0a0951433"
 * ,"有效队列":"","平台":"微博游戏中心","VIP等级":"2","战力":"1104","顺序":2,"兵数":
 * "","安全铁":"","领主等级"
 * :"2","总铁":"","第2队":"","第4队":"","总粮":"35.1K","安全银":"","世界":"1433"
 * ,"总银":"","版本":
 * "2.11.0","密码":"","城防":0,"总木":"27.5K","安全粮":"","账号":"zuosuohui299@163.com"
 * ,"VIP状态":"未激活","本地时间":1476372389}
 * 
 * @author liuyang
 * 
 */
public class StatisticsBean
{
	private String refreshTime;

	private long localTime;

	private int sequence;

	private String version;

	private String versionType;
	
	private String account;

	private String password;

	private String lordName;

	private String lordLevel;

	private String castleLevel;
	
	private String goldNum;
	
	private String vipLevel;
	
	private String vipState;
	
	private String kingdom;
	
	private String location;
	
	private String woodAll;
	
	private String woodSafe;
	
	private String foodAll;
	
	private String foodSafe;
	
	private String ironAll;
	
	private String ironSafe;
	
	private String silverAll;
	
	private String silverSafe;
	
	private String force;//战力
	
	private String soldierNum;
	
	private String cityValue;//城防值
	
	private String soldierQueue;
	
	private QueueBean oneQueueBean;
	
	private QueueBean twoQueueBean;
	
	private QueueBean threeQueueBean;
	
	private QueueBean fourQueueBean;
	
	private QueueBean fiveQueueBean;
	
	private List<String> chinese;
	
	private List<String> result;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	private List<QueueBean> queueList = new ArrayList<QueueBean>();

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getVersionType()
	{
		return versionType;
	}

	public void setVersionType(String versionType)
	{
		this.versionType = versionType;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getLordName()
	{
		return lordName;
	}

	public void setLordName(String lordName)
	{
		this.lordName = lordName;
	}

	public String getLordLevel()
	{
		return lordLevel;
	}

	public void setLordLevel(String lordLevel)
	{
		this.lordLevel = lordLevel;
	}

	public String getRefreshTime()
	{
		return refreshTime;
	}

	public void setLocalTime(long localTime)
	{
		this.localTime = localTime;
		this.refreshTime = DateUtil.formatUnixTime(localTime);
	}

	public String getCastleLevel()
	{
		return castleLevel;
	}

	public void setCastleLevel(String castleLevel)
	{
		this.castleLevel = castleLevel;
	}

	public String getGoldNum()
	{
		return goldNum;
	}

	public void setGoldNum(String goldNum)
	{
		this.goldNum = goldNum;
	}

	public String getVipLevel()
	{
		return vipLevel;
	}

	public void setVipLevel(String vipLevel)
	{
		this.vipLevel = vipLevel;
	}

	public String getVipState()
	{
		return vipState;
	}

	public void setVipState(String vipState)
	{
		this.vipState = vipState;
	}

	public String getKingdom()
	{
		return kingdom;
	}

	public void setKingdom(String kingdom)
	{
		this.kingdom = kingdom;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getWoodAll()
	{
		return woodAll;
	}

	public void setWoodAll(String woodAll)
	{
		this.woodAll = woodAll;
	}

	public String getWoodSafe()
	{
		return woodSafe;
	}

	public void setWoodSafe(String woodSafe)
	{
		this.woodSafe = woodSafe;
	}

	public String getFoodAll()
	{
		return foodAll;
	}

	public void setFoodAll(String foodAll)
	{
		this.foodAll = foodAll;
	}

	public String getFoodSafe()
	{
		return foodSafe;
	}

	public void setFoodSafe(String foodSafe)
	{
		this.foodSafe = foodSafe;
	}

	public String getIronAll()
	{
		return ironAll;
	}

	public void setIronAll(String ironAll)
	{
		this.ironAll = ironAll;
	}

	public String getIronSafe()
	{
		return ironSafe;
	}

	public void setIronSafe(String ironSafe)
	{
		this.ironSafe = ironSafe;
	}

	public String getSilverAll()
	{
		return silverAll;
	}

	public void setSilverAll(String silverAll)
	{
		this.silverAll = silverAll;
	}

	public String getSilverSafe()
	{
		return silverSafe;
	}

	public void setSilverSafe(String silverSafe)
	{
		this.silverSafe = silverSafe;
	}

	public String getForce()
	{
		return force;
	}

	public void setForce(String force)
	{
		this.force = force;
	}

	public String getSoldierNum()
	{
		return soldierNum;
	}

	public void setSoldierNum(String soldierNum)
	{
		this.soldierNum = soldierNum;
	}

	public String getCityValue()
	{
		return cityValue;
	}

	public void setCityValue(String cityValue)
	{
		this.cityValue = cityValue;
	}

	public String getSoldierQueue()
	{
		return soldierQueue;
	}

	public void setSoldierQueue(String soldierQueue)
	{
		this.soldierQueue = soldierQueue;
	}

	public QueueBean getOneQueueBean()
	{
		return oneQueueBean;
	}

	public void setOneQueueBean(QueueBean oneQueueBean)
	{
		this.oneQueueBean = oneQueueBean;
	}

	public QueueBean getTwoQueueBean()
	{
		return twoQueueBean;
	}

	public void setTwoQueueBean(QueueBean twoQueueBean)
	{
		this.twoQueueBean = twoQueueBean;
	}

	public QueueBean getThreeQueueBean()
	{
		return threeQueueBean;
	}

	public void setThreeQueueBean(QueueBean threeQueueBean)
	{
		this.threeQueueBean = threeQueueBean;
	}

	public QueueBean getFourQueueBean()
	{
		return fourQueueBean;
	}

	public void setFourQueueBean(QueueBean fourQueueBean)
	{
		this.fourQueueBean = fourQueueBean;
	}

	public QueueBean getFiveQueueBean()
	{
		return fiveQueueBean;
	}

	public void setFiveQueueBean(QueueBean fiveQueueBean)
	{
		this.fiveQueueBean = fiveQueueBean;
	}

	public List<String> getChinese()
	{
		return chinese;
	}

	public void setChinese(List<String> chinese)
	{
		this.chinese = chinese;
	}

	public List<String> getResult()
	{
		return result;
	}

	public void setResult(List<String> result)
	{
		this.result = result;
	}

	public List<QueueBean> getQueueList()
	{
		return queueList;
	}

	public void addQueueList(QueueBean queue)
	{
		this.queueList.add(queue);
	}

	public Map<String, String> getMap()
	{
		return map;
	}

	public void addMap(String key, String value)
	{
		this.map.put(key, value);
	}
	
}

