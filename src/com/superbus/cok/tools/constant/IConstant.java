package com.superbus.cok.tools.constant;

public interface IConstant
{
	/**
     * 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置
     * 4造兵设置：  是否造兵  //0表示false，1表示true，如; 0/1
     * 5采集设置：  是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm
     * 6分组设置：  分组编号，是否自定义搜索，x，y  //如：    0/1/2,true/false,x,y
     * 7升级设置：  是否升级城堡    //0表示false，1表示true，如 0/1
     * 8-金手指:   //0表示false，1表示true，如 0/1
     * 9-科技设置:是否自动科技,科技升级模式     如：0/1,0/1/2/3/4/5
     * 10-建筑设置:是否自动建筑,建筑升级模式    如：0/1,0/1/2/3/4/5
     * 11-城堡是否在盟区                       如：0/1
     */
	
	public static final String version版本 = "version版本";
	public static final String verType版本号 = "verType版本号";
	public static final String account账号 = "account账号";
	public static final String password密码 = "password密码";
	public static final String solder造兵 = "solder造兵";
	public static final String gather采集 = "gather采集";
	public static final String group分组 = "group分组";
	public static final String upgrade升级 = "upgrade升级";
	public static final String golden金手指 = "golden金手指";
	public static final String tech科技 = "tech科技";
	public static final String building建筑 = "building建筑";
	public static final String meng盟区 = "meng盟区";
	
}
