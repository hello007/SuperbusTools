package com.superbus.cok.tools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class StatisticsShell extends Shell
{

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			Display display = Display.getDefault();
			StatisticsShell shell = new StatisticsShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public StatisticsShell(Display display)
	{
		super(display, SWT.SHELL_TRIM);

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("SWT Application");
		setSize(800, 600);

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(10, 10, 762, 533);
		tabFolder.setSelection(0);

		TabItem infoBasic = new TabItem(tabFolder, SWT.NONE);
		infoBasic.setText("基础信息");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		infoBasic.setControl(composite);
//		createBasic(composite);
//		createComposite(composite);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		composite.setLayout(gridLayout);

		clientPathText = new Text(composite, SWT.BORDER);
		clientPathText.setEnabled(false);
		clientPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		clientPathButton = new Button(composite, SWT.NONE);
		clientPathButton.setText("客户端日志");
		clientPathButton.setLayoutData(new GridData());

		TabItem infoResource = new TabItem(tabFolder, SWT.NONE);
		infoResource.setText("资源信息");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		infoResource.setControl(composite_1);
		createResource(composite_1);

		TabItem infoQueue = new TabItem(tabFolder, SWT.NONE);
		infoQueue.setText("队列信息");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		infoQueue.setControl(composite_2);
		createQueue(composite_2);
	}

	Button check顺序, check账号, check密码, check平台, check版本, check领主名, check领主等级,
			check城堡等级, check金币, checkVIP等级, checkVIP状态, check王国, check坐标,
			check战力, check兵数, check城防;

	String[] checkButtonNames = new String[] { "顺序", "账号", "密码", "平台", "版本", "领主名",
			"领主等级", "城堡等级", "金币", "vip等级", "vip状态", "王国", "坐标", "战力", "兵数",
			"城防" };

	Button[] checkButtons = new Button[] { check顺序, check账号, check密码, check平台, check版本,
			check领主名, check领主等级, check城堡等级, check金币, checkVIP等级, checkVIP状态,
			check王国, check坐标, check战力, check兵数, check城防 };
	
	Text clientPathText, serverPathText, exportPathText;
	Button clientPathButton, serverPathButton, exportPathButton;

	private void createComposite(Composite composite)
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		composite.setLayout(gridLayout);

		clientPathText = new Text(composite, SWT.BORDER);
		clientPathText.setEnabled(false);
		clientPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		clientPathButton = new Button(composite, SWT.NONE);
		clientPathButton.setText("客户端日志");
		clientPathButton.setLayoutData(new GridData());

		serverPathText = new Text(composite, SWT.BORDER);
		serverPathText.setEnabled(false);
		serverPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		serverPathButton = new Button(composite, SWT.NONE);
		serverPathButton.setText("服务端日志");
		serverPathButton.setLayoutData(new GridData());

		exportPathText = new Text(composite, SWT.BORDER);
		exportPathText.setEnabled(false);
		exportPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		exportPathButton = new Button(composite, SWT.NONE);
		exportPathButton.setText("导出的日志");
		exportPathButton.setLayoutData(new GridData());

		// 第二行
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		Composite parentComposite = new Composite(composite, SWT.NONE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 6;
		gridData.verticalSpan = 1;
		parentComposite.setLayoutData(gridData);
		parentComposite.setLayout(new FillLayout());


	}

	private void createBasic(Composite composite)
	{
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		gridLayout.makeColumnsEqualWidth = true;
		composite.setLayout(gridLayout);
		
//		Label toLabel = new Label(composite, SWT.NONE);
//		toLabel.setText("收件人");
//		GridData gridData = new GridData();
//		gridData.horizontalSpan = 1;
//		toLabel.setLayoutData(gridData);
		
		Button button = new Button(composite, SWT.NONE);
		button.setText("2222");
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		button.setSize(80, 20);
		
//		check顺序 = new Button(composite, SWT.CHECK);
//		check顺序.setText("111111");
//		check顺序.setLayoutData(new GridData(GridData.BEGINNING));

//		for (int i = 0; i < checkButtons.length; i++)
//		{
//			Button button = checkButtons[i];
//			button = new Button(composite, SWT.CHECK);
//			button.setText(checkButtonNames[i]);
//			button.setLayoutData(new GridData());
//		}

	}

	private void createResource(Composite composite)
	{

	}

	private void createQueue(Composite composite)
	{

	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
