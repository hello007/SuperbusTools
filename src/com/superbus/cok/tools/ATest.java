package com.superbus.cok.tools;

public class ATest
{
	public static void main(String[] args)
	{
		String string = "12|23|45";
		String[] strings = string.split("[|]");
		System.err.println(strings.length);
	}

}
