package com.superbus.cok.tools.bean;

/**
 * 11-城堡是否在盟区 如：0/1
 * 
 * @author liuyang
 * 
 */
public class CastleInAliasBean
{
	private int in = 0;

	@Override
	public String toString()
	{
		return in + "";
	}

	public int getIn()
	{
		return in;
	}

	public void setIn(int in)
	{
		this.in = in;
	}

}
