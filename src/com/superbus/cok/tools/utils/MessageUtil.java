package com.superbus.cok.tools.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageUtil
{
	private static void showDialog(Shell shell, int style, String text,
			String message)
	{
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
}
