package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
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
import com.superbus.cok.tools.utils.HttpUtil;
import com.superbus.cok.tools.utils.MessageUtil;

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

		//initData("55A11A31E81A96EDD3D9E350974FFBA5");
	}
	
	private String currentKey = null;

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("统计信息预览");
		setSize(800, 800);
		setLocation(Display.getDefault().getClientArea().width / 2
				- getSize().x / 2, Display.getDefault().getClientArea().height
				/ 2 - getSize().y / 2);

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(10, 46, 762, 697);
		tabFolder.setSelection(0);

		TabItem infoBasic = new TabItem(tabFolder, SWT.NONE);
		infoBasic.setText("基础信息");

		Composite composite = new Composite(tabFolder, SWT.BORDER);
		infoBasic.setControl(composite);
		createBasic(composite);

		saveButton = new Button(composite, SWT.NONE);
		saveButton.setBounds(661, 77, 84, 30);
		saveButton.setText("保存展示");
		saveButton.addMouseListener(saveListener);

		allUnselectButton = new Button(composite, SWT.NONE);
		allUnselectButton.setBounds(661, 44, 84, 30);
		allUnselectButton.setText("反向选择");
		allUnselectButton.addMouseListener(selectListener);

		allSelectButton = new Button(composite, SWT.NONE);
		allSelectButton.setBounds(661, 10, 84, 30);
		allSelectButton.setText("全部选中");
		allSelectButton.addMouseListener(selectListener);

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

		cardText = new Text(this, SWT.BORDER);
		cardText.setBounds(10, 10, 381, 30);
		cardText.setMessage("其他序列号信息查询");

		searchButton = new Button(this, SWT.NONE);
		searchButton.setBounds(404, 10, 98, 30);
		searchButton.setText("查询");
		searchButton.addMouseListener(searchListener);
	}

	Button check顺序, check账号, check密码, check平台, check版本, check领主名, check领主等级,
			check城堡等级, check金币, checkVIP等级, checkVIP状态, check王国, check坐标,
			check战力, check兵数, check城防;

	String[] checkButtonNames = new String[] { "顺序", "账号", "密码", "平台", "版本",
			"领主名", "领主等级", "城堡等级", "金币", "vip等级", "vip状态", "王国", "坐标", "战力",
			"兵数", "城防" };

	Button[] checkButtons = new Button[] { check顺序, check账号, check密码, check平台,
			check版本, check领主名, check领主等级, check城堡等级, check金币, checkVIP等级,
			checkVIP状态, check王国, check坐标, check战力, check兵数, check城防 };

	int[] widths = new int[] { 50, 150, 150, 100, 100, 100, 100, 100, 100, 100,
			100, 50, 100, 100, 100, 100 };

	Text clientPathText, serverPathText, exportPathText;
	Button clientPathButton, serverPathButton, exportPathButton;
	Composite tableComposite;
	List<StatisticsBean> listBeans = null;

	private void createBasic(Composite composite)
	{
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

	private TableViewer tableViewer;
	private Table table;
	int tableRow = 0;
	private Text cardText;
	private Button searchButton, saveButton, allSelectButton;
	/**
	 * 反向选择
	 */
	Button allUnselectButton;

	private void createTableComposite(Composite tableComposite)
	{
		tableComposite.setLayout(new FillLayout());
		tableViewer = new TableViewer(tableComposite, SWT.MULTI
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setSize(tableComposite.getSize());

		TableColumn newColumnTableColumn = new TableColumn(table, SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("行号");

		for (int i = 0; i < checkButtons.length; i++)
		{
			Button button = checkButtons[i];
			if (button.getSelection())
			{
				TableColumn newColumnTableColumn_1 = new TableColumn(table,
						SWT.CENTER);
				newColumnTableColumn_1.setWidth(widths[i]);
				newColumnTableColumn_1.setText(checkButtonNames[i]);
			}
		}

		// 设置内容器
		tableViewer.setContentProvider(new ContentProvider());
		// // 设置标签器
		tableViewer.setLabelProvider(new TableLabelProvider());
		// 把数据集合给tableView
	}
	
	public void initData(String key)
	{
		if(key == null || key.trim().length() != 32)
		{
			MessageUtil.showErrorDialog(StatisticsShell.this, "提示",
					"卡号不合法，请检查后重试");
			return;
		}
		currentKey = key.trim();
		cardText.setText(key);
		initData(HttpUtil.queryAccount(key));
	}

	private void initData(List<StatisticsBean> listBeans)
	{
		this.listBeans = listBeans;
		changeTableData();
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
	}

	public class ContentProvider implements IStructuredContentProvider
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

	public class TableLabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<String> bean = (List<String>) element;
				if(bean != null && bean.size() <= 0)
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
	
	private void changeTableData()
	{
		for (Control control : tableComposite.getChildren())
		{
			control.dispose();
		}
		createTableComposite(tableComposite);
		tableRow = 0;
		if (listBeans != null)
		{
			tableViewer.setInput(listBeans);
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
			String card = cardText.getText();
			if (card == null || card.trim().equals("")
					|| card.trim().length() != 32)
			{
				MessageUtil.showErrorDialog(StatisticsShell.this, "提示",
						"卡号不合法，请检查后重试");
				return;
			}
			if(card.trim().equals(currentKey))
			{
				changeTableData();
				return;
			}
			card = card.toUpperCase();
			initData(card);;
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
				changeTableData();
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
}
