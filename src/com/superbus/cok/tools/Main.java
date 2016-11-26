package com.superbus.cok.tools;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.superbus.cok.tools.utils.BrowserUtil;
import com.superbus.cok.tools.utils.MessageUtil;
import com.superbus.cok.tools.version.VersionUtil;
import com.superbus.cok.tools.version.VersionUtil.VersionBean;

public class Main
{
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args)
	{
		logger.info("程序启动！");
		Display display = Display.getDefault();
		StatisticsShell shell = StatisticsShell.getInstance();
		shell.open();
		int update = VersionUtil.getInstance().update();
		VersionBean bean = VersionUtil.getInstance().getVersionBean();
		logger.info("程序更新结果：" + update);
		boolean forceUpdate = false;
		if (update == 0)
		{
			int result = MessageUtil.showOptionalUpdate(bean.getContent());
			if (result == SWT.YES)
			{
				forceUpdate = true;
			}
		} else if (update == 1)
		{
			MessageUtil.showForceUpdate(bean.getContent());
			forceUpdate = true;
		}
		if (forceUpdate)
		{
			// 以IE程序打开
			String url = bean.getUpdateUrl();
			logger.info("程序更新url：" + url);
			BrowserUtil.openUrl(url);
			shell.dispose();
		} else
		{
			shell.initSerialNum();
		}
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		logger.info("程序结束！=========================");
	}

}
