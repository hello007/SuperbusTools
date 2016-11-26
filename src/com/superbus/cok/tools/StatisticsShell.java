package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.superbus.cok.tools.bean.StatisticsBean;
import com.superbus.cok.tools.utils.DateUtil;
import com.superbus.cok.tools.utils.ImageUtil;
import com.superbus.cok.tools.utils.MessageUtil;
import com.superbus.cok.tools.utils.NumberUtil;
import com.superbus.cok.tools.utils.Result;
import com.superbus.cok.tools.utils.ShowProcessDialog;
import com.superbus.cok.tools.utils.ShowTimeDialog;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class StatisticsShell extends Shell
{
	private static Logger logger = Logger.getLogger(StatisticsShell.class);

	private static StatisticsShell mainShell = new StatisticsShell(
			Display.getDefault());

	public static StatisticsShell getInstance()
	{
		return mainShell;
	}

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
		super(display, ShellStyle.FixedSize);

		createContents();

		// initData("55A11A31E81A96EDD3D9E350974FFBA5");
		setCompositeVisible();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public StatisticsShell(Shell shell, int style)
	{
		super(shell, style);
		createContents();
		setCompositeVisible();
	}

	private void setCompositeVisible()
	{
		gotoSettingButton.setVisible(false);
	}

	private String currentKey = null;
	Composite resourceComposite;
	TabItem infoQueue;

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		logger.info("创建StatisticsShell！");
		setText(NameConstant.statisticsName);
		setSize(800, 863);
		setLocation(Display.getDefault().getClientArea().width / 2
				- getSize().x / 2, Display.getDefault().getClientArea().height
				/ 2 - getSize().y / 2);
		final Image image = ImageUtil.getSuperbus();
		if (image != null)
		{
			setImage(image);
		}

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(10, 119, 762, 697);
		tabFolder.setSelection(0);

		TabItem infoBasic = new TabItem(tabFolder, SWT.NONE);
		infoBasic.setText("基础信息");

		Composite composite = new Composite(tabFolder, SWT.BORDER);
		infoBasic.setControl(composite);
		createBasic(composite);

		TabItem infoResource = new TabItem(tabFolder, SWT.NONE);
		infoResource.setText("资源信息");

		resourceComposite = new Composite(tabFolder, SWT.NONE);
		infoResource.setControl(resourceComposite);
		createResource(resourceComposite);

		// infoQueue = new TabItem(tabFolder, SWT.NONE);
		// infoQueue.setText("队列信息");
		//
		// Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		// infoQueue.setControl(composite_2);
		// createQueue(composite_2);

		cardText = new Text(this, SWT.BORDER);
		cardText.setBounds(10, 19, 381, 30);
		cardText.setMessage("其他序列号信息查询");

		searchButton = new Button(this, SWT.NONE);
		searchButton.addMouseListener(searchListener);
		searchButton.setBounds(411, 19, 98, 30);
		searchButton.setText("查询");

		gotoSettingButton = new Button(this, SWT.NONE);
		gotoSettingButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				AccountShell accountShell = AccountShell.getInstance();
				accountShell.open(StatisticsShell.this, cardText.getText());
			}
		});
		gotoSettingButton.setBounds(515, 10, 111, 30);
		gotoSettingButton.setText("跳转到设置页面");

		Group group = new Group(this, SWT.NONE);
		group.setText("历史卡号展示及编辑");
		group.setBounds(10, 53, 762, 60);

		historyNumCombo = new Combo(group, SWT.READ_ONLY);
		historyNumCombo.setBounds(10, 25, 381, 28);
		historyNumCombo.addModifyListener(new ModifyListener()
		{

			@Override
			public void modifyText(ModifyEvent arg0)
			{
				cardText.setText(historyNumCombo.getText());

			}
		});

		Button button = new Button(group, SWT.NONE);
		button.setBounds(407, 23, 169, 30);
		button.setText("从当前列表中删除该卡号");

		Label lbln = new Label(this, SWT.NONE);
		lbln.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbln.setBounds(558, 10, 214, 20);
		lbln.setText("列王巴士辅助1群 217769463");

		Label label = new Label(this, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label.setText("列王巴士辅助2群 239967066");
		label.setBounds(558, 32, 214, 20);

		button.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				String curNum = historyNumCombo.getText();
				PropertyMethod.removeSerialNum(curNum);
				comboRemoveKey();
			}

		});
	}

	public void initSerialNum()
	{
		String[] allNum = PropertyMethod.getAllKeyNum();
		if (allNum != null && allNum.length > 0)
		{
			currentKey = allNum[allNum.length - 1];
			historyNumCombo.removeAll();
			historyNumCombo.setItems(allNum);
			historyNumCombo.select(allNum.length - 1);
		}
		if (currentKey != null && currentKey.length() == 32)
		{
			cardText.setText(currentKey);
		}
	}

	Button check顺序, check账号, check密码, check平台, check版本, check领主名, check领主等级,
			check城堡等级, check金币, checkVIP等级, checkVIP状态, check王国, check坐标,
			check战力, check兵数, check城防, check刷新时间;

	String[] checkButtonNames = new String[] { "顺序", "账号", "密码", "平台", "版本",
			"领主名", "领主等级", "城堡等级", "金币", "vip等级", "vip状态", "王国", "坐标", "战力",
			"兵数", "城防", "刷新时间" };

	Button[] checkButtons = new Button[] { check顺序, check账号, check密码, check平台,
			check版本, check领主名, check领主等级, check城堡等级, check金币, checkVIP等级,
			checkVIP状态, check王国, check坐标, check战力, check兵数, check城防, check刷新时间 };

	int[] widths = new int[] { 50, 150, 150, 100, 100, 100, 100, 100, 100, 100,
			100, 50, 100, 100, 100, 100, 200 };

	Text clientPathText, serverPathText, exportPathText;
	Button clientPathButton, serverPathButton, exportPathButton;
	Composite tableComposite;
	List<StatisticsBean> listBeans = null;

	private void createBasic(Composite composite)
	{
		saveButton = new Button(composite, SWT.NONE);
		saveButton.setBounds(661, 77, 84, 30);
		saveButton.setText("保存信息");
		saveButton.addMouseListener(saveListener);

		allUnselectButton = new Button(composite, SWT.NONE);
		allUnselectButton.setBounds(661, 44, 84, 30);
		allUnselectButton.setText("反向选择");
		allUnselectButton.addMouseListener(selectListener);

		allSelectButton = new Button(composite, SWT.NONE);
		allSelectButton.setBounds(661, 10, 84, 30);
		allSelectButton.setText("全部选中");
		allSelectButton.addMouseListener(selectListener);

		Group group = new Group(composite, SWT.NONE);
		group.setText("显示信息");
		group.setBounds(5, 0, 650, 107);

		int x = 10;
		int y = 20;
		int width = 80, height = 20;

		for (int i = 0; i < checkButtons.length; i++)
		{
			Button button = checkButtons[i];
			button = new Button(group, SWT.CHECK);
			checkButtons[i] = button;
			button.setText(checkButtonNames[i]);
			button.setBounds(x, y, width, height);
			button.setSelection(true);
			if (i > 0 && (i + 1) % 7 == 0)
			{
				x = 10;
				y += 30;
			} else
			{
				x += 90;
			}
		}

		tableComposite = new Composite(composite, SWT.NONE);
		tableComposite.setBounds(5, 113, 740, 537);
		createTableComposite(tableComposite);
	}

	private TableViewer basicTableViewer, resourceTableViewer,
			queueTableViewer;
	private Table basicTable, resourceTable, queueTable;
	int tableRow = 0, resourceRow = 0;
	private Text cardText;
	private Button searchButton, saveButton, allSelectButton,
			gotoSettingButton;
	private Combo historyNumCombo;
	private long lastClickSearchTime = 0;
	/**
	 * 反向选择
	 */
	Button allUnselectButton;

	private void createTableComposite(Composite tableComposite)
	{
		tableComposite.setLayout(new FillLayout());
		basicTableViewer = new TableViewer(tableComposite, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		basicTable = basicTableViewer.getTable();
		basicTable.setLinesVisible(true);
		basicTable.setHeaderVisible(true);
		basicTable.setSize(tableComposite.getSize());

		TableColumn newColumnTableColumn = new TableColumn(basicTable,
				SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("行号");

		for (int i = 0; i < checkButtons.length; i++)
		{
			Button button = checkButtons[i];
			if (button.getSelection())
			{
				TableColumn newColumnTableColumn_1 = new TableColumn(
						basicTable, SWT.CENTER);
				newColumnTableColumn_1.setWidth(widths[i]);
				newColumnTableColumn_1.setText(checkButtonNames[i]);
			}
		}

		// 设置内容器
		basicTableViewer.setContentProvider(new BasicProvider());
		// // 设置标签器
		basicTableViewer.setLabelProvider(new BasicLabelProvider());
	}

	public void initData(String key)
	{
		if (key == null || key.trim().length() != 32)
		{
			MessageUtil.showErrorDialog(StatisticsShell.this, "提示",
					"卡号不合法，请检查后重试");
			return;
		}
		currentKey = key.trim();
		cardText.setText(currentKey);
		// List<StatisticsBean> beans = HttpsUtil.queryAccount(key);
		ShowProcessDialog dialog = new ShowProcessDialog(this, currentKey);
		dialog.setTypeResult(Result.queryAccount);
		List<StatisticsBean> beans = (List<StatisticsBean>) dialog.open();

		// 下载成功的将key保存到userInfo中
		PropertyMethod.saveSerialNum(currentKey);
		// 将historyCombo刷新
		// comboRemoveKey();
		String[] strings = historyNumCombo.getItems();
		historyNumCombo.removeAll();
		Set<String> set = new HashSet<String>();
		for (String string : strings)
		{
			set.add(string);
			if (string != null && string.length() == 32)
			{
				historyNumCombo.add(string);
			}
		}
		if (!set.contains(currentKey))
		{
			historyNumCombo.add(currentKey);
		}
		historyNumCombo.setText(currentKey);
		initData(beans);
	}

	private void comboRemoveKey()
	{
		String[] allNum = PropertyMethod.getAllKeyNum();
		if (allNum == null)
		{
			return;
		}
		historyNumCombo.removeAll();
		historyNumCombo.setItems(allNum);
		if (allNum.length > 0)
		{
			historyNumCombo.select(allNum.length - 1);
			cardText.setText(allNum[allNum.length - 1]);
		} else
		{
			cardText.setText("");
		}
	}

	private void initData(List<StatisticsBean> listBeans)
	{
		this.listBeans = listBeans;
		changeBasicTableData();
	}

	String[] resourceNames = new String[] { "账号", "总木/安全木", "总粮/安全粮", "总铁/安全铁",
			"总银/安全银", "刷新时间" };

	private void createResource(Composite composite)
	{
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		resourceTableViewer = new TableViewer(composite, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		resourceTable = resourceTableViewer.getTable();
		resourceTable.setLinesVisible(true);
		resourceTable.setHeaderVisible(true);
		resourceTable.setSize(composite.getSize());

		TableColumn newColumnTableColumn = new TableColumn(resourceTable,
				SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("行号");

		for (int i = 0; i < resourceNames.length; i++)
		{
			TableColumn newColumnTableColumn_1 = new TableColumn(resourceTable,
					SWT.CENTER);
			newColumnTableColumn_1.setWidth(115);
			newColumnTableColumn_1.setText(resourceNames[i]);
		}

		// 设置内容器
		resourceTableViewer.setContentProvider(new ResourceProvider());
		// // 设置标签器
		resourceTableViewer.setLabelProvider(new ResourceLabelProvider());
	}

	private void createQueue(Composite composite)
	{

	}

	@Override
	protected void checkSubclass()
	{
	}

	public class BasicProvider implements IStructuredContentProvider
	{
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof List)
			{
				List<StatisticsBean> listBeans = (List<StatisticsBean>) inputElement;
				List<List<String>> beanList = new ArrayList<List<String>>();
				for (StatisticsBean bean : listBeans)
				{
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < checkButtons.length; i++)
					{
						Button button = checkButtons[i];
						if (button.getSelection())
						{
							String key = checkButtonNames[i];
							if (key != null && key.trim().equals("刷新时间"))
							{
								String value = bean.getMap().get("本地时间");
								if (value != null && !value.trim().equals(""))
								{
									value = DateUtil.formatUnixTime(Long
											.parseLong(value));
								} else
								{
									value = "暂无";
								}
								list.add(value);
								continue;
							} else if (key != null && key.trim().equals("战力"))
							{
								String value = bean.getMap().get("战力");
								value = NumberUtil.formatToPoint(value);
								list.add(value);
								continue;
							}
							String value = bean.getMap().get(key);
							if (value == null || value.trim().equals(""))
							{
								value = "暂无";
							}
							if ("城防".equals(key))
							{
								if (value != null)
								{
									value = value.replaceAll(",", "");
								}
							}
							list.add(value);
						}
					}
					beanList.add(list);
				}
				return beanList.toArray();
			} else
			{
				return new Object[0];
			}
		}

		public void dispose()
		{
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
	}

	public class ResourceProvider implements IStructuredContentProvider
	{
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof List)
			{
				List<StatisticsBean> listBeans = (List<StatisticsBean>) inputElement;
				List<List<String>> beanList = new ArrayList<List<String>>();

				for (StatisticsBean bean : listBeans)
				{
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < resourceNames.length; i++)
					{
						String name = resourceNames[i];
						if (name.indexOf("/") > 0)
						{
							String[] two = name.split("/");
							StringBuffer result = new StringBuffer();
							for (int j = 0; j < two.length; j++)
							{
								String value = bean.getMap().get(two[j]);
								if (value == null || value.trim().equals(""))
								{
									value = "暂无";
								}
								result.append(value);
								if (j != two.length - 1)
								{
									result.append("/");
								}
							}
							list.add(result.toString());
						} else
						{
							if ("刷新时间".equals(name))
							{
								name = "本地时间";
								String value = bean.getMap().get(name);
								if (value == null || value.trim().equals(""))
								{
									value = "暂无";
								} else
								{
									value = DateUtil.formatUnixTime(Long
											.parseLong(value));
								}
								list.add(value);
								continue;
							}
							String value = bean.getMap().get(name);
							if (value == null || value.trim().equals(""))
							{
								value = "暂无";
							}
							list.add(value);
						}
					}
					beanList.add(list);
				}
				return beanList.toArray();
			} else
			{
				return new Object[0];
			}
		}

		public void dispose()
		{
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
	}

	public class QueueProvider implements IStructuredContentProvider
	{
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof List)
			{
				List<StatisticsBean> listBeans = (List<StatisticsBean>) inputElement;
				List<List<String>> beanList = new ArrayList<List<String>>();
				for (StatisticsBean bean : listBeans)
				{
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < checkButtons.length; i++)
					{
						Button button = checkButtons[i];
						if (button.getSelection())
						{
							String key = checkButtonNames[i];
							if (bean == null)
							{
								System.err.println();
							}
							String value = bean.getMap().get(key);
							if (value == null || value.trim().equals(""))
							{
								value = "暂无";
							}
							list.add(value);
						}
					}
					beanList.add(list);
				}
				return beanList.toArray();
			} else
			{
				return new Object[0];
			}
		}

		public void dispose()
		{
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
	}

	public class QueueLabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<String> bean = (List<String>) element;
				if (bean != null && bean.size() <= 0)
				{
					return null;
				}
				if (columnIndex == 0)
				{
					tableRow++;
					return tableRow + "";
				} else
				{
					return bean.get(columnIndex - 1);
				}
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener arg0)
		{

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public boolean isLabelProperty(Object arg0, String arg1)
		{
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener arg0)
		{

		}
	}

	public class ResourceLabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<String> bean = (List<String>) element;
				if (bean != null && bean.size() <= 0)
				{
					return null;
				}
				if (columnIndex == 0)
				{
					resourceRow++;
					return resourceRow + "";
				} else
				{
					return bean.get(columnIndex - 1);
				}
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener arg0)
		{

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public boolean isLabelProperty(Object arg0, String arg1)
		{
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener arg0)
		{

		}
	}

	public class BasicLabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<String> bean = (List<String>) element;
				if (bean != null && bean.size() <= 0)
				{
					return null;
				}
				if (columnIndex == 0)
				{
					tableRow++;
					return tableRow + "";
				} else
				{
					return bean.get(columnIndex - 1);
				}
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener arg0)
		{

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public boolean isLabelProperty(Object arg0, String arg1)
		{
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener arg0)
		{

		}
	}

	private void changeBasicTableData()
	{
		disposeChildControl(tableComposite);
		disposeChildControl(resourceComposite);
		createTableComposite(tableComposite);
		createResource(resourceComposite);
		tableRow = 0;
		resourceRow = 0;
		if (listBeans != null)
		{
			basicTableViewer.setInput(listBeans);
			resourceTableViewer.setInput(listBeans);
		}
	}

	private void disposeChildControl(Composite composite)
	{
		for (Control control : composite.getChildren())
		{
			control.dispose();
		}
	}

	// 查询
	MouseListener searchListener = new MouseAdapter()
	{
		@Override
		public void mouseUp(MouseEvent e)
		{
			if (e.button != 1)
			{
				return;
			}
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastClickSearchTime < 5000)
			{
				ShowTimeDialog dialog = new ShowTimeDialog(StatisticsShell.this);
				long delta = currentTime - lastClickSearchTime;
				int delayTime = (int) (delta / 1000);
				dialog.open(delayTime);
				return;
			}
			lastClickSearchTime = System.currentTimeMillis();
			String card = cardText.getText();
			if (card == null || card.trim().equals("")
					|| card.trim().length() != 32)
			{
				MessageUtil.showErrorDialog(StatisticsShell.this, "提示",
						"卡号不合法，请检查后重试");
				return;
			}
			// if (card.trim().equals(currentKey))
			// {
			// changeBasicTableData();
			// return;
			// }
			card = card.toUpperCase();
			initData(card);
		}
	};

	// 保存更改
	MouseListener saveListener = new MouseAdapter()
	{
		@Override
		public void mouseUp(MouseEvent e)
		{
			if (e.button == 1)
			{
				changeBasicTableData();
			}
		}
	};

	MouseListener selectListener = new MouseAdapter()
	{
		@Override
		public void mouseUp(MouseEvent e)
		{
			if (e.widget == allSelectButton)
			{
				for (Button button : checkButtons)
				{
					button.setSelection(true);
				}
			} else if (e.widget == allUnselectButton)
			{
				for (Button button : checkButtons)
				{
					button.setSelection(!button.getSelection());
				}
			}
		}

	};
	private Text txtn;
}
