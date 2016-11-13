package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.superbus.cok.tools.bean.AccountInfoBean;
import com.superbus.cok.tools.bean.GatherBean;
import com.superbus.cok.tools.bean.GroupBean;
import com.superbus.cok.tools.bean.SerialNumberBean;
import com.superbus.cok.tools.utils.MessageUtil;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class GroupShell extends Shell
{
	private Table table;

	private static final int Account_INDEX = 1;
	private static final int Version_INDEX = 2;
	private static final int GroupNum_INDEX = 3;

	private static String GROUP_ZERO = "未分组";

	private static String GROUP_ONE = "分组1";

	private static String GROUP_TWO = "分组2";

	private static String GROUP_THREE = "分组3";

	private static String GROUP_FOUR = "分组4";

	String[] groupNumInfo = new String[] { GROUP_ZERO, GROUP_ONE, GROUP_TWO,
			GROUP_THREE, GROUP_FOUR };

	/**
	 * 记录分组1信息
	 */
	private GatherInfo oneGatherInfo;

	/**
	 * 记录分组2信息
	 */
	private GatherInfo twoGatherInfo;

	/**
	 * 记录分组3信息
	 */
	private GatherInfo threeGatherInfo;

	/**
	 * 记录分组4信息
	 */
	private GatherInfo fourGatherInfo;

	CheckboxTableViewer checkboxTableViewer;

	SerialNumberBean serialNumberBean;

	DataConstant dataConstant = DataConstant.getInstance();

	Combo comboOneMinKm, comboOneMaxKm, comboTwoMin, comboTwoMax,
			comboThreeMin, comboThreeMax, comboFourMin, comboFourMax;

	public static void main(String args[])
	{
		try
		{
			Display display = Display.getDefault();
			GroupShell shell = new GroupShell(display);
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
	 * @wbp.parser.constructor
	 */
	public GroupShell(Display display)
	{
		super(display, SWT.SHELL_TRIM);

		createContents();
	}

	public GroupShell(Shell shell, int style)
	{
		super(shell, style);

		createContents();

		initData();
	}

	public void init(SerialNumberBean serialNumberBean)
	{
		if (serialNumberBean == null)
		{
			return;
		}
		this.serialNumberBean = serialNumberBean;
		checkboxTableViewer.setInput(serialNumberBean);// 设置表格中的数据

		setInputGroupData();
	}

	/**
	 * 初始化group信息
	 */
	private void setInputGroupData()
	{
		if (oneGatherInfo != null)
		{
			comboOneMinKm.select(oneGatherInfo.getMinKm());
			comboOneMaxKm.select(oneGatherInfo.getMaxKm());
			textOneX.setText(oneGatherInfo.getX() + "");
			textOneY.setText(oneGatherInfo.getY() + "");
		}
		if (twoGatherInfo != null)
		{
			comboTwoMin.select(twoGatherInfo.getMinKm());
			comboTwoMax.select(twoGatherInfo.getMaxKm());
			textTwoX.setText(twoGatherInfo.getX() + "");
			textTwoY.setText(twoGatherInfo.getY() + "");
		}
		if (threeGatherInfo != null)
		{
			comboThreeMin.select(threeGatherInfo.getMinKm());
			comboThreeMax.select(threeGatherInfo.getMaxKm());
			textThreeX.setText(threeGatherInfo.getX() + "");
			textThreeY.setText(threeGatherInfo.getY() + "");
		}
		if (fourGatherInfo != null)
		{
			comboFourMin.select(fourGatherInfo.getMinKm());
			comboFourMax.select(fourGatherInfo.getMaxKm());
			textFourX.setText(fourGatherInfo.getX() + "");
			textFourY.setText(fourGatherInfo.getY() + "");
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("分组设定");
		setSize(947, 540);
		setLocation(Display.getDefault().getClientArea().width / 2
				- getSize().x / 2, Display.getDefault().getClientArea().height
				/ 2 - getSize().y / 2);

		checkboxTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.CHECK);
		table = checkboxTableViewer.getTable();
		table.setBounds(10, 10, 472, 473);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setAlignment(SWT.CENTER);
		tableColumn.setWidth(60);
		tableColumn.setText("行号");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_1.getColumn();
		tableColumn_1.setAlignment(SWT.CENTER);
		tableColumn_1.setWidth(182);
		tableColumn_1.setText("账号");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_2.getColumn();
		tableColumn_2.setAlignment(SWT.CENTER);
		tableColumn_2.setWidth(125);
		tableColumn_2.setText("版本");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_3.getColumn();
		tableColumn_3.setAlignment(SWT.CENTER);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("分组编号");

		button全选 = new Button(this, SWT.NONE);
		button全选.setBounds(488, 10, 98, 30);
		button全选.addSelectionListener(selectionAdapter);
		button全选.setText("全选");

		button全部取消 = new Button(this, SWT.NONE);
		button全部取消.setBounds(488, 46, 98, 30);
		button全部取消.addSelectionListener(selectionAdapter);
		button全部取消.setText("全部取消");

		buttonOne = new Button(this, SWT.NONE);
		buttonOne.setBounds(488, 119, 98, 30);
		buttonOne.addSelectionListener(selectionAdapter);
		buttonOne.setText("分组一");

		buttonTwo = new Button(this, SWT.NONE);
		buttonTwo.setBounds(488, 155, 98, 30);
		buttonTwo.addSelectionListener(selectionAdapter);
		buttonTwo.setText("分组二");

		buttonThree = new Button(this, SWT.NONE);
		buttonThree.setBounds(488, 191, 98, 30);
		buttonThree.addSelectionListener(selectionAdapter);
		buttonThree.setText("分组三");

		buttonFour = new Button(this, SWT.NONE);
		buttonFour.setBounds(488, 230, 98, 30);
		buttonFour.addSelectionListener(selectionAdapter);
		buttonFour.setText("分组四");

		button保存分组 = new Button(this, SWT.NONE);
		button保存分组.setBounds(805, 453, 98, 30);
		button保存分组.addSelectionListener(selectionAdapter);
		button保存分组.setText("保存");

		buttonZero = new Button(this, SWT.NONE);
		buttonZero.setBounds(488, 82, 98, 30);
		buttonZero.addSelectionListener(selectionAdapter);
		buttonZero.setText("未分组");

		Group group = new Group(this, SWT.NONE);
		group.setText("分组一信息");
		group.setBounds(592, 10, 311, 102);

		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 35, 76, 20);
		label.setText("采集距离");

		comboOneMinKm = new Combo(group, SWT.NONE);
		comboOneMinKm.setToolTipText("最近距离");
		comboOneMinKm.setBounds(92, 32, 92, 28);

		comboOneMaxKm = new Combo(group, SWT.NONE);
		comboOneMaxKm.setToolTipText("最远距离");
		comboOneMaxKm.setBounds(209, 32, 92, 28);

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(190, 40, 15, 20);
		label_1.setText("到");

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(10, 69, 76, 20);
		label_2.setText("搜索起点");

		textOneX = new Text(group, SWT.BORDER);
		textOneX.setToolTipText("x坐标");
		textOneX.setBounds(91, 66, 93, 26);

		textOneY = new Text(group, SWT.BORDER);
		textOneY.setToolTipText("y坐标");
		textOneY.setBounds(209, 66, 92, 26);

		Group group_1 = new Group(this, SWT.NONE);
		group_1.setText("分组一信息");
		group_1.setBounds(592, 119, 311, 102);

		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setText("采集距离");
		label_3.setBounds(10, 35, 76, 20);

		comboTwoMin = new Combo(group_1, SWT.NONE);
		comboTwoMin.setToolTipText("最近距离");
		comboTwoMin.setBounds(92, 32, 92, 28);

		comboTwoMax = new Combo(group_1, SWT.NONE);
		comboTwoMax.setToolTipText("最远距离");
		comboTwoMax.setBounds(209, 32, 92, 28);

		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setText("到");
		label_4.setBounds(190, 40, 15, 20);

		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setText("搜索起点");
		label_5.setBounds(10, 69, 76, 20);

		textTwoX = new Text(group_1, SWT.BORDER);
		textTwoX.setToolTipText("x坐标");
		textTwoX.setBounds(91, 66, 93, 26);

		textTwoY = new Text(group_1, SWT.BORDER);
		textTwoY.setToolTipText("y坐标");
		textTwoY.setBounds(209, 66, 92, 26);

		Group group_2 = new Group(this, SWT.NONE);
		group_2.setText("分组一信息");
		group_2.setBounds(592, 230, 311, 102);

		Label label_6 = new Label(group_2, SWT.NONE);
		label_6.setText("采集距离");
		label_6.setBounds(10, 35, 76, 20);

		comboThreeMin = new Combo(group_2, SWT.NONE);
		comboThreeMin.setToolTipText("最近距离");
		comboThreeMin.setBounds(92, 32, 92, 28);

		comboThreeMax = new Combo(group_2, SWT.NONE);
		comboThreeMax.setToolTipText("最远距离");
		comboThreeMax.setBounds(209, 32, 92, 28);

		Label label_7 = new Label(group_2, SWT.NONE);
		label_7.setText("到");
		label_7.setBounds(190, 40, 15, 20);

		Label label_8 = new Label(group_2, SWT.NONE);
		label_8.setText("搜索起点");
		label_8.setBounds(10, 69, 76, 20);

		textThreeX = new Text(group_2, SWT.BORDER);
		textThreeX.setToolTipText("x坐标");
		textThreeX.setBounds(91, 66, 93, 26);

		textThreeY = new Text(group_2, SWT.BORDER);
		textThreeY.setToolTipText("y坐标");
		textThreeY.setBounds(209, 66, 92, 26);

		Group group_3 = new Group(this, SWT.NONE);
		group_3.setText("分组一信息");
		group_3.setBounds(592, 338, 311, 102);

		Label label_9 = new Label(group_3, SWT.NONE);
		label_9.setText("采集距离");
		label_9.setBounds(10, 35, 76, 20);

		comboFourMin = new Combo(group_3, SWT.NONE);
		comboFourMin.setToolTipText("最近距离");
		comboFourMin.setBounds(92, 32, 92, 28);

		comboFourMax = new Combo(group_3, SWT.NONE);
		comboFourMax.setToolTipText("最远距离");
		comboFourMax.setBounds(209, 32, 92, 28);

		Label label_10 = new Label(group_3, SWT.NONE);
		label_10.setText("到");
		label_10.setBounds(190, 40, 15, 20);

		Label label_11 = new Label(group_3, SWT.NONE);
		label_11.setText("搜索起点");
		label_11.setBounds(10, 69, 76, 20);

		textFourX = new Text(group_3, SWT.BORDER);
		textFourX.setToolTipText("x坐标");
		textFourX.setBounds(91, 66, 93, 26);

		textFourY = new Text(group_3, SWT.BORDER);
		textFourY.setToolTipText("y坐标");
		textFourY.setBounds(209, 66, 92, 26);

		checkboxTableViewer.setContentProvider(new TableViewContentProvider()); // 内容器
		checkboxTableViewer.setLabelProvider(new GroupTableLabelProvider());// 标签器
	}

	private void initData()
	{
		Combo[] combos = new Combo[] { comboOneMinKm, comboOneMaxKm,
				comboTwoMin, comboTwoMax, comboThreeMin, comboThreeMax,
				comboFourMin, comboFourMax };
		String[] strings = dataConstant.getGatherDistanceData();
		for (int i = 0; i < combos.length; i++)
		{
			Combo combo = combos[i];
			combo.setItems(strings);
			if (i % 2 == 0)
			{
				combo.select(0);
			} else
			{
				combo.select(6);
			}
		}
	}

	Button button全选;
	Button button全部取消;
	Button buttonOne;
	Button buttonTwo;
	Button buttonThree;
	Button buttonFour;
	Button button保存分组;

	@Override
	protected void checkSubclass()
	{
	}

	SelectionAdapter selectionAdapter = new SelectionAdapter()
	{

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			TableItem[] items = table.getItems();
			if (e.widget == button保存分组)
			{
				Map<String, AccountInfoBean> map = serialNumberBean.getMap();
				for (int i = 0; i < items.length; i++)
				{
					TableItem item = items[i];
					String groupInfo = item.getText(GroupNum_INDEX);
					String account = item.getText(Account_INDEX);
					String version = item.getText(Version_INDEX).split("-")[0];
					int index = 0;
					try
					{
						index = Integer.valueOf(version);
						String typeData = dataConstant.getAccountTypeData()[index];
						version = typeData.split("-")[1];
					} catch (Exception e2)
					{
						version = "微博版";
					}
					
					AccountInfoBean bean = map.get(account + "-" + version);
					if(bean == null)
					{
						continue;
					}
					if (GROUP_ONE.equals(groupInfo))
					{
						try
						{
							int oneMin = comboOneMinKm.getSelectionIndex();
							int oneMax = comboOneMaxKm.getSelectionIndex();
							int oneX = getInt(textOneX.getText());
							int oneY = getInt(textOneY.getText());
							if (oneX <= 0 || oneY <= 0)
							{
								MessageUtil.showErrorDialog(GroupShell.this,
										"错误", "分组一设置坐标不合法，请检查后保存");
								return;
							}

							bean.getGatherBean().setMinKm(oneMin);
							bean.getGatherBean().setMaxKm(oneMax);
							bean.getGroupBean().setDefinReseach(true);
							bean.getGroupBean().setGroupNum(1);
							bean.getGroupBean().setX(oneX);
							bean.getGroupBean().setY(oneY);
						} catch (Exception e2)
						{
							MessageUtil.showErrorDialog(GroupShell.this, "错误",
									"分组一设置参数出错，请检查后保存");
							return;
						}
					} else if (GROUP_TWO.equals(groupInfo))
					{

						try
						{
							int twoMin = comboTwoMin.getSelectionIndex();
							int twoMax = comboTwoMax.getSelectionIndex();
							int twoX = getInt(textTwoX.getText());
							int twoY = getInt(textTwoY.getText());
							if (twoX <= 0 || twoY <= 0)
							{
								MessageUtil.showErrorDialog(GroupShell.this, "错误",
										"分组二设置坐标不合法，请检查后保存");
								return;
							}
							bean.getGatherBean().setMinKm(twoMin);
							bean.getGatherBean().setMaxKm(twoMax);
							bean.getGroupBean().setDefinReseach(true);
							bean.getGroupBean().setGroupNum(2);
							bean.getGroupBean().setX(twoX);
							bean.getGroupBean().setY(twoY);
						} catch (Exception e2)
						{
							MessageUtil.showErrorDialog(GroupShell.this, "错误",
									"分组二设置参数出错，请检查后保存");
							return;
						}
					} else if (GROUP_THREE.equals(groupInfo))
					{

						try
						{
							int threeMin = comboThreeMin.getSelectionIndex();
							int threeMax = comboThreeMax.getSelectionIndex();
							int threeX = getInt(textThreeX.getText());
							int threeY = getInt(textThreeY.getText());
							if (threeX <= 0 || threeY <= 0)
							{
								MessageUtil.showErrorDialog(GroupShell.this, "错误",
										"分组三设置坐标不合法，请检查后保存");
								return;
							}
							bean.getGatherBean().setMinKm(threeMin);
							bean.getGatherBean().setMaxKm(threeMax);
							bean.getGroupBean().setDefinReseach(true);
							bean.getGroupBean().setGroupNum(3);
							bean.getGroupBean().setX(threeX);
							bean.getGroupBean().setY(threeY);
						} catch (Exception e2)
						{
							MessageUtil.showErrorDialog(GroupShell.this, "错误",
									"分组三设置参数出错，请检查后保存");
							return;
						}
					} else if (GROUP_FOUR.equals(groupInfo))
					{

						try
						{
							int fourMin = comboFourMin.getSelectionIndex();
							int fourMax = comboFourMax.getSelectionIndex();
							int fourX = getInt(textFourX.getText());
							int fourY = getInt(textFourY.getText());
							if (fourX <= 0 || fourY <= 0)
							{
								MessageUtil.showErrorDialog(GroupShell.this, "错误",
										"分组四设置坐标不合法，请检查后保存");
								return;
							}
							bean.getGatherBean().setMinKm(fourMin);
							bean.getGatherBean().setMaxKm(fourMax);
							bean.getGroupBean().setDefinReseach(true);
							bean.getGroupBean().setGroupNum(4);
							bean.getGroupBean().setX(fourX);
							bean.getGroupBean().setY(fourY);
						} catch (Exception e2)
						{
							MessageUtil.showErrorDialog(GroupShell.this, "错误",
									"分组四设置参数出错，请检查后保存");
							return;
						}
					} else
					{
						bean.getGroupBean().setDefinReseach(false);
						bean.getGroupBean().setGroupNum(0);
					}
				}
				serialNumberBean.setMap(map);
				Main.getInstance().setSerialNumberBean(serialNumberBean);
				dispose();
				return;
			}
			if (items == null || items.length <= 0)
			{
				return;
			}
			if (e.widget == button全选)
			{
				for (int i = 0; i < items.length; i++)
				{
					items[i].setChecked(true);
				}
			} else if (e.widget == button全部取消)
			{
				for (int i = 0; i < items.length; i++)
				{
					items[i].setChecked(false);
				}
			} else if (e.widget == buttonZero)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i].getChecked())
						items[i].setText(GroupNum_INDEX, groupNumInfo[0]);
				}
			} else if (e.widget == buttonOne)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i].getChecked())
						items[i].setText(GroupNum_INDEX, groupNumInfo[1]);
				}
			} else if (e.widget == buttonTwo)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i].getChecked())
						items[i].setText(GroupNum_INDEX, groupNumInfo[2]);
				}
			} else if (e.widget == buttonThree)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i].getChecked())
						items[i].setText(GroupNum_INDEX, groupNumInfo[3]);
				}
			} else if (e.widget == buttonFour)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i].getChecked())
						items[i].setText(GroupNum_INDEX, groupNumInfo[4]);
				}
			}
		}
	};

	private Button buttonZero;
	private Text textOneX;
	private Text textOneY;
	private Text textTwoX;
	private Text textTwoY;
	private Text textThreeX;
	private Text textThreeY;
	private Text textFourX;
	private Text textFourY;

	private void initGroupData(int num, AccountInfoBean bean)
	{
		switch (num) {
		case 0:
			break;
		case 1:
			if (oneGatherInfo == null)
			{
				oneGatherInfo = new GatherInfo();
				GroupBean groupBean = bean.getGroupBean();
				GatherBean gatherBean = bean.getGatherBean();
				oneGatherInfo.setX(groupBean.getX());
				oneGatherInfo.setY(groupBean.getY());
				oneGatherInfo.setMinKm(gatherBean.getMinKm());
				oneGatherInfo.setMaxKm(gatherBean.getMaxKm());
			}
			break;
		case 2:
			if (twoGatherInfo == null)
			{
				twoGatherInfo = new GatherInfo();
				GroupBean groupBean = bean.getGroupBean();
				GatherBean gatherBean = bean.getGatherBean();
				twoGatherInfo.setX(groupBean.getX());
				twoGatherInfo.setY(groupBean.getY());
				twoGatherInfo.setMinKm(gatherBean.getMinKm());
				twoGatherInfo.setMaxKm(gatherBean.getMaxKm());
			}
			break;
		case 3:
			if (threeGatherInfo == null)
			{
				threeGatherInfo = new GatherInfo();
				GroupBean groupBean = bean.getGroupBean();
				GatherBean gatherBean = bean.getGatherBean();
				threeGatherInfo.setX(groupBean.getX());
				threeGatherInfo.setY(groupBean.getY());
				threeGatherInfo.setMinKm(gatherBean.getMinKm());
				threeGatherInfo.setMaxKm(gatherBean.getMaxKm());
			}
			break;

		case 4:
			if (fourGatherInfo == null)
			{
				fourGatherInfo = new GatherInfo();
				GroupBean groupBean = bean.getGroupBean();
				GatherBean gatherBean = bean.getGatherBean();
				fourGatherInfo.setX(groupBean.getX());
				fourGatherInfo.setY(groupBean.getY());
				fourGatherInfo.setMinKm(gatherBean.getMinKm());
				fourGatherInfo.setMaxKm(gatherBean.getMaxKm());
			}
			break;
		default:
			break;
		}
	}

	// 内容器 实现IStructuredContentProvider接口
	public class TableViewContentProvider implements IStructuredContentProvider
	{

		@Override
		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof SerialNumberBean)
			{
				SerialNumberBean serialNumberBean = (SerialNumberBean) inputElement;
				Map<String, AccountInfoBean> map = serialNumberBean.getMap();
				List<Object[]> list = new ArrayList<Object[]>();
				int line = 1;
				for (String key : serialNumberBean.getIdQueue())
				{
					Object[] objects = new Object[4];
					AccountInfoBean bean = map.get(key);
					objects[0] = line;
					line++;
					objects[1] = bean.getAccount();
					objects[2] = dataConstant.getAccountTypeData()[bean
							.getVersion()];
					int num = bean.getGroupBean().getGroupNum();
					initGroupData(num, bean);
					objects[3] = groupNumInfo[num];
					list.add(objects);
				}
				return list.toArray();
			} else
			{
				return new Object[] { -1, "NULL", "NULL", "0" };
			}

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2)
		{

		}

	}

	public class GroupTableLabelProvider implements ITableLabelProvider
	{

		@Override
		public void addListener(ILabelProviderListener listener)
		{

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public boolean isLabelProperty(Object element, String property)
		{
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener)
		{

		}

		@Override
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof Object[])
			{
				Object[] objects = (Object[]) element;
				switch (columnIndex) {
				case 0:
					return objects[0].toString();
				case 1:
					return objects[1].toString();
				case 2:
					return objects[2].toString();
				case 3:
					return objects[3].toString();
				default:
					return "--";
				}
			}
			return "";
		}
	}

	private int getInt(String text)
	{
		int num = 0;
		try
		{
			num = Integer.valueOf(text);
			if (num > 0)
			{
				return num;
			}
		} catch (NumberFormatException e)
		{
			throw e;
		}

		return num;
	}
}

class GatherInfo
{
	private int minKm;

	private int maxKm;

	private int x;

	private int y;

	public GatherInfo()
	{

	}

	public int getMinKm()
	{
		return minKm;
	}

	public void setMinKm(int minKm)
	{
		this.minKm = minKm;
	}

	public int getMaxKm()
	{
		return maxKm;
	}

	public void setMaxKm(int maxKm)
	{
		this.maxKm = maxKm;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

}
