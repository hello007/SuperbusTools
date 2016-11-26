package com.superbus.cok.tools.utils;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.superbus.cok.tools.ShellStyle;

/**
 * ShowProcessDialog implements the Runnable to read the check echo result in
 * other thread.
 */
public class ShowProcessDialog extends Dialog implements Runnable
{
	

	/**
	 * reads the socket to get the echo result
	 */
	private Thread tRead;
	protected Shell shell;
	protected Display display = getParent().getDisplay();
	private String key;
	private Object result;
	private Result typeResult;
	private String content;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public ShowProcessDialog(Shell parent, String key)
	{
		super(parent, ShellStyle.FixedSizePrimaryModal);
		this.key = key;
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open()
	{
		createContents();
		// start the thread to read the Agent echo result
		tRead = new Thread(this);
		tRead.start();
		shell.open();
		shell.layout();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents()
	{
		shell = new Shell(getParent(), SWT.TITLE | SWT.BORDER
				| SWT.APPLICATION_MODAL);
		shell.setSize(345, 181);
		shell.setLocation(
				Display.getDefault().getClientArea().width / 2
						- shell.getSize().x / 2,
				Display.getDefault().getClientArea().height / 2
						- shell.getSize().y / 2);
		shell.setText("运行提示");
		final Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setText("正在同服务器交互数据，请稍候...");
		label.setBounds(55, 70, 204, 44);
	}

	/**
	 * closes the dialog, because read Thread do this, it's other thread, so
	 * should close dialog through dialog thread using syncExec.
	 */
	public void close()
	{
		display.syncExec(new Runnable()
		{
			public void run()
			{
				shell.close();
				shell.dispose();
			}
		});
	}

	public void run()
	{
		switch (typeResult) {
		case queryAccount:
			result = HttpsUtil.queryAccount(key);
			break;
		case download:
			result = HttpsUtil.download(key);
			break;
		case upload:
			result = HttpsUtil.upload(key, content);
			break;
		default:
			break;
		}
		close();
	}

	public Result getTypeResult()
	{
		return typeResult;
	}

	public void setTypeResult(Result typeResult)
	{
		this.typeResult = typeResult;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
