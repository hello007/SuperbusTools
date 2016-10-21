package com.superbus.cok.tools.bean;

/**
 * 造兵设置： 是否造兵 //0表示false，1表示true，如; 0/1
 */
public class SolderBean
{
	private boolean trainSolder = false;

	public boolean isTrainSolder()
	{
		return trainSolder;
	}

	public void setTrainSolder(boolean trainSolder)
	{
		this.trainSolder = trainSolder;
	}

	@Override
	public String toString()
	{
		return trainSolder ? "1" : "0";
	}
}
