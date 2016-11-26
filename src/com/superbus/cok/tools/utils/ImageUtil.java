package com.superbus.cok.tools.utils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

public class ImageUtil
{
	private static Image superbus = null;

	static
	{
		try
		{
			superbus = new Image(Display.getDefault(), "png/superbus.png");
		} catch (Exception e)
		{
			
		}
	}

	public static Image getSuperbus()
	{
		return superbus;
	}

	public static void dispose()
	{
		if (!isDisposed(superbus))
		{
			superbus.dispose();
		}
	}

	/**
	 * 判断资源是否销毁
	 * 
	 * @param resource
	 * @return true： 销毁 false：未销毁
	 */
	private static boolean isDisposed(Resource resource)
	{
		if (resource == null || resource.isDisposed())
		{
			return true;
		}
		return false;
	}
}
