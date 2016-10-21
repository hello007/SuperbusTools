package com.superbus.cok.tools.bean;

/**
 * 分组设置： 分组编号，是否自定义搜索，x，y //如： 0/1/2,true/false,x,y
 * 
 * @author liuyang
 * 
 */
public class GroupBean
{
	private int groupNum = 0;
	private boolean definReseach = false;
	private int x = 0;
	private int y = 0;

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(groupNum + ",");
		sb.append(definReseach + ",");
		sb.append(x + ",");
		sb.append(y);
		return sb.toString();
	}

	public int getGroupNum()
	{
		return groupNum;
	}

	public void setGroupNum(int groupNum)
	{
		this.groupNum = groupNum;
	}

	public boolean isDefinReseach()
	{
		return definReseach;
	}

	public void setDefinReseach(boolean definReseach)
	{
		this.definReseach = definReseach;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

}
