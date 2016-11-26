package com.superbus.cok.tools.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageUtil
{
	private static void showDialog(Shell shell, int style, String text,
			String message)
	{
		if (shell == null)
		{
			shell = Display.getDefault().getShells()[0];
		}
		MessageBox box = new MessageBox(shell, style);
		box.setText(text);
		box.setMessage(message);
		box.open();
	}

	public static void showInfoDialog(Shell shell, String text, String message)
	{
		showDialog(shell, SWT.ICON_INFORMATION, text, message);
	}

	public static void showErrorDialog(Shell shell, String text, String message)
	{
		showDialog(shell, SWT.ICON_ERROR, text, message);
	}

	public static void showWarningDialog(Shell shell, String text,
			String message)
	{
		showDialog(shell, SWT.ICON_WARNING, text, message);
	}

	public static int showOptionalUpdate(String content)
	{
		Shell shell = Display.getDefault().getShells()[0];
		MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.YES
				| SWT.NO);
		box.setText("版本更新提示");
		box.setMessage("是否前往网盘更新当前版本到最新版？\n" + content);
		return box.open();
	}

	public static int showForceUpdate(String content)
	{
		Shell shell = Display.getDefault().getShells()[0];
		MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		box.setText("版本更新提示");
		box.setMessage("点击确定将前往网盘下载最新版\n" + content);
		return box.open();
	}
}

class MyDialog extends Dialog
{

	public MyDialog(Shell parent, int style)
	{
		super(parent, style);
	}

}
