package com.superbus.cok.tools.bean;

import com.superbus.cok.tools.utils.StringTools;

/**
 * 8-金手指: //0表示false，1表示true，如 0/1
 * 
 * @author liuyang
 * 
 */
public class GoldenBean
{
	private boolean golden = false;

	@Override
	public String toString()
	{
		return StringTools.convertToString(golden);
	}

	public boolean isGolden()
	{
		return golden;
	}

	public void setGolden(boolean golden)
	{
		this.golden = golden;
	}

}
