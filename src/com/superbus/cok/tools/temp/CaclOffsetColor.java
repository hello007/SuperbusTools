package com.superbus.cok.tools.temp;

public class CaclOffsetColor
{
	private static final String FIRST_COLOR = "192|497";

	private static final String OFFSET_COLOR = "239|497|9E5C3B-161616,190|534|9E5C3B-161616,240|533|9E5C3B-161616";

	private static final String SPLIT = "\\|";

	public static void main(String[] args)
	{
		caclOffsetColor();
	}

	public static void caclOffsetColor()
	{
		String[] first = FIRST_COLOR.split(SPLIT);
		int firstX = Integer.valueOf(first[0]);
		int firstY = Integer.valueOf(first[1]);

		String[] allOffsetColor = OFFSET_COLOR.split(",");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < allOffsetColor.length; i++)
		{
			String aOffsetColor = allOffsetColor[i];
			String[] aContent = aOffsetColor.split(SPLIT);
			int x = Integer.valueOf(aContent[0]);
			int y = Integer.valueOf(aContent[1]);
			String other = aContent[2];

			sb.append((x - firstX) + "|" + (y - firstY) + "|" + other);
			if (i < allOffsetColor.length - 1)
			{
				sb.append(",");
			}
		}

		System.err.println(sb.toString());
	}
}
