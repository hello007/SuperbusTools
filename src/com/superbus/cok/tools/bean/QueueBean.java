package com.superbus.cok.tools.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QueueBean
{
	private List<String> chinese = new ArrayList<String>();
	
	private List<String> result = new ArrayList<String>();
	
	private long arriveTime;
	
	private long endTime;
	
	private long backTime;

	public List<String> getChinese()
	{
		return chinese;
	}

	public void addChinese(String ch)
	{
		this.chinese.add(ch);
	}

	public List<String> getResult()
	{
		return result;
	}

	public void addResult(String ch)
	{
		this.result.add(ch);
	}

	public long getArriveTime()
	{
		return arriveTime;
	}

	public void setArriveTime(long arriveTime)
	{
		this.arriveTime = arriveTime;
	}

	public long getEndTime()
	{
		return endTime;
	}

	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
		new Timer().schedule(new TimerTask()
		{
			
			@Override
			public void run()
			{
				
			}
		}, 0, 5000);
	}

	public long getBackTime()
	{
		return backTime;
	}

	public void setBackTime(long backTime)
	{
		this.backTime = backTime;
	}
	
}
