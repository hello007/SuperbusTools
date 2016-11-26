package com.superbus.cok.tools.utils;

import java.util.concurrent.CountDownLatch;

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
public class ShowTimeDialog extends Dialog implements Runnable
{

	/**
	 * reads the socket to get the echo result
	 */
	private Thread tRead;
	protected Shell shell;
	private Label label;
	protected Display display = getParent().getDisplay();
	private Object result;
	private int second;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public ShowTimeDialog(Shell parent)
	{
		super(parent, ShellStyle.FixedSizePrimaryModal);
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open(int second)
	{
		this.second = second;
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
		shell.setSize(300, 100);
		shell.setLocation(
				Display.getDefault().getClientArea().width / 2
						- shell.getSize().x / 2,
				Display.getDefault().getClientArea().height / 2
						- shell.getSize().y / 2);
		shell.setText("运行提示");
		label = new Label(shell, SWT.WRAP);
		label.setAlignment(SWT.CENTER);
		label.setForeground(display.getSystemColor(SWT.COLOR_RED));
		label.setBounds(50, 20, 200, 80);
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
		final CountDownLatch latch = new CountDownLatch(1);

		for (int i = second; i > 0; i--)
		{
			final int tmp = i;
			display.syncExec(new Runnable()
			{

				@Override
				public void run()
				{
					label.setText("亲，操作太频繁了，请休息一下，" + tmp + "秒后再试");
				}
			});
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (i == 1)
			{
				latch.countDown();
			}
		}
		try
		{
			latch.await();
		} catch (InterruptedException e)
		{
		}
		close();
	}

}
