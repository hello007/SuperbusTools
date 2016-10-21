package com.superbus.cok.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Link;

import com.superbus.cok.tools.bean.AccountInfoBean;
import com.superbus.cok.tools.bean.BuildingBean;
import com.superbus.cok.tools.bean.CastleInAliasBean;
import com.superbus.cok.tools.bean.GatherBean;
import com.superbus.cok.tools.bean.GoldenBean;
import com.superbus.cok.tools.bean.GroupBean;
import com.superbus.cok.tools.bean.SerialNumberBean;
import com.superbus.cok.tools.bean.SolderBean;
import com.superbus.cok.tools.bean.TechBean;
import com.superbus.cok.tools.bean.UpgradeBean;
import com.superbus.cok.tools.constant.InfoConstant;
import com.superbus.cok.tools.utils.CloudUtil;
import com.superbus.cok.tools.utils.DataConvertUtil;
import com.superbus.cok.tools.utils.MessageUtil;
import com.superbus.cok.tools.utils.PropertyUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.graphics.Point;

public class Main
{

	private Shell shell;

	private Composite userInfoComposite;

	private Composite normalComposite;

	private org.eclipse.swt.widgets.List accList;
	private Composite taobaoComposite;

	private DataConstant dataConstant = DataConstant.getInstance();

	// 配置的当前语言下的空间显示名
	private Properties properties;

	private String curSerialNum;

	private SerialNumberBean serialNumberBean;

	private static Main main = new Main();

	public static Main getInstance()
	{
		return main;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Main window = getInstance();
			window.open();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open()
	{
		Display display = Display.getDefault();
		createContents();

		initProperties();
		initGlobalData();
		initData();

		shell.setLocation(display.getClientArea().width / 2 - shell.getSize().x
				/ 2, display.getClientArea().height / 2 - shell.getSize().y / 2);
		shell.open();
		shell.layout();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}

	/**
	 * 加载property
	 */
	private void initProperties()
	{
		properties = LoadProperty.getInstance().getProperties();
	}

	/**
	 * 初始化数据，包括list初始化等
	 */
	private void initData()
	{
		initSerialNum();
		initList();
		initUserInfoComposite();
		initTaobaoComposite();
	}

	private void initSerialNum()
	{
		curSerialNum = PropertyUtil.loadProperty(
				InfoConstant.PROPERTY_USER_INFO).getProperty(
				InfoConstant.keySerialNum);
	}

	private void initList()
	{
		accList.removeAll();
		if (CloudUtil.checkKey(curSerialNum) && serialNumberBean != null)
		{
			List<String> list = serialNumberBean.getIdQueue();
			Map<String, AccountInfoBean> map = serialNumberBean.getMap();
			int num = 1;
			for (String id : list)
			{
				AccountInfoBean bean = map.get(id);
				if (checkBtn显示所有账号.getSelection() || bean.isShow())
				{
					accList.add("(行" + num + ")" + bean.getId());
					num++;
				}
			}
		}
		accList.addSelectionListener(selectionAdapter);
	}

	String selectId = "";

	private void setInput(String select)
	{
		Map<String, AccountInfoBean> map = serialNumberBean.getMap();
		if (map.containsKey(select))
		{
			AccountInfoBean selectBean = map.get(select);
			selectId = select;
			setValue(selectBean);
		} else
		{
			selectId = "";
			setValue(getDefaultInfoBean());
		}
	}

	private AccountInfoBean getDefaultInfoBean()
	{
		// 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置|8-金手指|9-科技设置|10-建筑设置|11-城堡是否在盟区
		AccountInfoBean bean = new AccountInfoBean();
		bean.setSolderBean(new SolderBean());
		bean.setGatherBean(new GatherBean());
		bean.setGroupBean(new GroupBean());
		bean.setUpgradeBean(new UpgradeBean());
		bean.setGoldenBean(new GoldenBean());
		bean.setTechBean(new TechBean());
		bean.setBuildingBean(new BuildingBean());
		bean.setCastleInAliasBean(new CastleInAliasBean());
		return bean;
	}

	private void setValue(AccountInfoBean bean)
	{
		// 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置|8-金手指|9-科技设置|10-建筑设置|11-城堡是否在盟区
		text账号.setText(bean.getAccount());
		text密码.setText(bean.getPassword());
		combo版本.select(bean.getVersion());
		combo版本号.select(bean.getVersionType());

		SolderBean solderBean = bean.getSolderBean();
		checkBtn造兵.setSelection(solderBean.isTrainSolder());

		GatherBean gatherBean = bean.getGatherBean();
		checkBtn采集.setSelection(gatherBean.isGather());
		checkBtn木.setSelection(gatherBean.isWood());
		checkBtn粮.setSelection(gatherBean.isFood());
		checkBtn铁.setSelection(gatherBean.isIron());
		checkBtn银.setSelection(gatherBean.isSilver());
		combo采集联盟矿.select(gatherBean.getAlias());
		combo队伍数量.select(gatherBean.getNum());
		combo野矿出兵方式.select(gatherBean.getArmyOutMethod());
		combo盟矿出兵方式.select(gatherBean.getGatherAlliasType());
		comboMinKm.select(gatherBean.getMinKm());
		comboMaxKm.select(gatherBean.getMaxKm());

		GroupBean groupBean = bean.getGroupBean();
		int groupNum = groupBean.getGroupNum();

		combo分组编号.select(groupNum);
		checkBtn搜索起点.setSelection(groupBean.isDefinReseach());
		textX.setText(groupBean.getX() + "");
		textY.setText(groupBean.getY() + "");
		if (groupNum > 0)
		{
			combo分组编号.setEnabled(false);
			checkBtn搜索起点.setEnabled(false);
			textX.setEnabled(false);
			textY.setEnabled(false);
		}

		UpgradeBean upgradeBean = bean.getUpgradeBean();
		checkBtn升堡.setSelection(upgradeBean.isUpgrade());

		GoldenBean goldenBean = bean.getGoldenBean();
		checkBtn金手指.setSelection(goldenBean.isGolden());

		TechBean techBean = bean.getTechBean();
		checkBtn科技.setSelection(techBean.isTech());
		combo科技.select(techBean.getTechType());

		BuildingBean buildingBean = bean.getBuildingBean();
		checkBtn建筑.setSelection(buildingBean.isBuidling());
		combo建筑.select(buildingBean.getBuildingType());

		CastleInAliasBean castleInAliasBean = bean.getCastleInAliasBean();
		combo在盟区.select(castleInAliasBean.getIn());

	}

	private void initUserInfoComposite()
	{
		init差异性Composite();
	}

	private void init差异性Composite()
	{
		combo版本.setItems(dataConstant.getAccountTypeData());
		combo版本号.setItems(dataConstant.getVersionData());
		combo科技.setItems(dataConstant.getUpgradeTechData());
		combo建筑.setItems(dataConstant.getUpgradeBuildingData());
		combo队伍数量.setItems(dataConstant.getArmyOutNumData());
		combo野矿出兵方式.setItems(dataConstant.getArmyOutMethodData());
		combo在盟区.setItems(dataConstant.getCastleInAlliasData());
		combo盟矿出兵方式.setItems(dataConstant.getGatherAlliasMethodData());
		combo采集联盟矿.setItems(dataConstant.getGatherAlliasTypeData());
		comboMinKm.setItems(dataConstant.getGatherDistanceData());
		comboMaxKm.setItems(dataConstant.getGatherDistanceData());
		combo分组编号.setItems(dataConstant.getGroupInfoData());

		combo分组编号.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				combo分组编号.select(0);
			}

		});
	}

	private void initTaobaoComposite()
	{

	}

	/**
	 * 国际化，对label、button、combo进行显示字符的处理
	 */
	private void initGlobalData()
	{
		initGlobalData(taobaoComposite, "taobaoComposite.");
		initGlobalData(userInfoComposite, "userInfoComposite.");
		initGlobalData(normalComposite, "normalComposite.");
	}

	private void initGlobalData(Composite composite, String prefix)
	{
		List<Control> list = getChildren(composite);
		for (Control control : list)
		{
			if (control instanceof Label)
			{
				Label label = (Label) control;
				String text = properties.getProperty(prefix + label.getText());
				if (text != null && text.length() > 0)
				{
					label.setText(text);
				}
			} else if (control instanceof Button)
			{
				Button button = (Button) control;
				String text = properties.getProperty(prefix + button.getText());
				if (text != null && text.length() > 0)
				{
					button.setText(text);
				}
			} else if (control instanceof Link)
			{
				Link link = (Link) control;
				String text = properties.getProperty(prefix + link.getText());
				if (text != null && text.length() > 0)
				{
					link.setText(text);
				}
			}
		}
	}

	/**
	 * 将界面控件导出到
	 */
	@SuppressWarnings("unused")
	private void exportCNPropertyData()
	{
		Properties prop = new Properties();
		List<Control> list = getChildren(taobaoComposite);
		saveProperty(prop, "taobaoComposite.", list);
		list = getChildren(userInfoComposite);
		saveProperty(prop, "userInfoComposite.", list);

		PropertyUtil.saveProperty(InfoConstant.PROPERTY_ZH_CN, prop);
	}

	@SuppressWarnings("unused")
	private void updateCNPropertyData(Composite composite, String prefix)
	{
		Properties prop = new Properties();
		List<Control> list = getChildren(composite);
		saveProperty(prop, prefix, list);

		PropertyUtil.update("property/zh_CN.properties", prop);
	}

	private void saveProperty(Properties prop, String prefix, List<Control> list)
	{
		for (Control control : list)
		{
			if (control instanceof Label)
			{
				Label label = (Label) control;
				String text = label.getText();
				prop.setProperty(prefix + text, text);
			} else if (control instanceof Button)
			{
				Button button = (Button) control;
				String text = button.getText();
				prop.setProperty(prefix + text, text);
			} else if (control instanceof Link)
			{
				Link link = (Link) control;
				String text = link.getText();
				prop.setProperty(prefix + text, text);
			}
		}
	}

	private List<Control> getChildren(Composite composite)
	{
		List<Control> list = new ArrayList<Control>();
		for (Control control : composite.getChildren())
		{
			list.add(control);
			if (control instanceof Composite)
			{
				list.addAll(getChildren((Composite) control));
			}
		}
		return list;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(855, 610);
		shell.setText("巴士辅助window配置器");

		accList = new org.eclipse.swt.widgets.List(shell, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		accList.setBounds(10, 63, 203, 490);

		userInfoComposite = new Composite(shell, SWT.NONE);
		userInfoComposite.setBounds(219, 63, 616, 418);

		normalComposite = new Composite(shell, SWT.NONE);
		normalComposite.setBounds(219, 489, 616, 64);

		taobaoComposite = new Composite(shell, SWT.BORDER);
		taobaoComposite.setFont(SWTResourceManager.getFont(
				"Microsoft YaHei UI", 12, SWT.NORMAL));
		taobaoComposite.setBounds(10, 10, 825, 46);

		createTaoBaoComposite();
		createUserInfoComposite();
		createNormalComposite();

	}

	Button checkBtn显示所有账号;
	Button button账号隐藏;
	Button button设置分组;

	private void createNormalComposite()
	{
		checkBtn显示所有账号 = new Button(normalComposite, SWT.CHECK);
		checkBtn显示所有账号.setBounds(3, 1, 140, 20);
		checkBtn显示所有账号.setText("显示所有账号");
		checkBtn显示所有账号.addSelectionListener(selectionAdapter);

		button账号隐藏 = new Button(normalComposite, SWT.NONE);
		button账号隐藏.setBounds(0, 27, 98, 30);
		button账号隐藏.setText("账号隐藏");
		button账号隐藏.addSelectionListener(selectionAdapter);

		button账号编辑 = new Button(normalComposite, SWT.NONE);
		button账号编辑.setBounds(114, 27, 98, 30);
		button账号编辑.setText("一键修改");
		button账号编辑.addSelectionListener(selectionAdapter);

		button设置分组 = new Button(normalComposite, SWT.NONE);
		button设置分组.setBounds(229, 27, 98, 30);
		button设置分组.setText("设置分组");
		button设置分组.addSelectionListener(selectionAdapter);
	}

	// TODO 用户信息栏
	TabFolder tabFolder;
	private TabItem tabItem通用性;
	private TabItem tabItem差异性;
	private Composite composite通用性;
	private Composite composite差异性;
	private Button button差异性保存;
	private Label label账号;
	private Text text账号;
	private Label label密码;
	private Text text密码;
	private Label label版本;
	private Combo combo版本;
	private Label label版本号;
	private Combo combo版本号;
	private Button checkBtn金手指;
	private Button checkBtn科技;
	private Button checkBtn银;
	private Combo combo野矿出兵方式;
	private Combo combo盟矿出兵方式;
	private Combo combo队伍数量;
	private Combo combo在盟区;
	private Combo combo采集联盟矿;
	private Label label;
	private Combo comboMinKm;
	private Label label_1;
	private Combo comboMaxKm;
	private Group group_1;
	private Combo combo分组编号;
	private Button checkBtn搜索起点;
	private Text textX;
	private Text textY;

	private void createUserInfoComposite()
	{
		tabFolder = new TabFolder(userInfoComposite, SWT.NONE);
		tabFolder.setLocation(0, 73);
		tabFolder.setSize(new Point(616, 345));
		tabFolder.setSelection(0);

		label账号 = new Label(userInfoComposite, SWT.RIGHT);
		label账号.setBounds(10, 10, 65, 20);
		label账号.setText("账号");

		text账号 = new Text(userInfoComposite, SWT.BORDER);
		text账号.setBounds(82, 7, 210, 26);

		label密码 = new Label(userInfoComposite, SWT.RIGHT);
		label密码.setBounds(298, 10, 92, 20);
		label密码.setText("密码");

		text密码 = new Text(userInfoComposite, SWT.BORDER);
		text密码.setBounds(396, 7, 210, 26);

		label版本 = new Label(userInfoComposite, SWT.RIGHT);
		label版本.setBounds(10, 42, 65, 20);
		label版本.setText("版本");

		combo版本 = new Combo(userInfoComposite, SWT.READ_ONLY);
		combo版本.setBounds(82, 39, 210, 28);

		label版本号 = new Label(userInfoComposite, SWT.RIGHT);
		label版本号.setBounds(298, 42, 92, 20);
		label版本号.setText("版本号");

		combo版本号 = new Combo(userInfoComposite, SWT.READ_ONLY);
		combo版本号.setBounds(396, 39, 210, 28);
		
		//将版本号置为不可见
		label版本号.setVisible(false);
		combo版本号.setVisible(false);

		create差异性Composite();
		create通用性Composite();

	}

	private void create通用性Composite()
	{
		// TODO 通用性
		tabItem通用性 = new TabItem(tabFolder, SWT.NONE);
		tabItem通用性.setText("通用性设置");

		composite通用性 = new Composite(tabFolder, SWT.NONE);
		tabItem通用性.setControl(composite通用性);

	}

	Button checkBtn造兵;
	Button checkBtn升堡;
	Combo combo科技;
	Button checkBtn建筑;
	Combo combo建筑;
	Button checkBtn采集;
	Button checkBtn木;
	Button checkBtn粮;
	Button checkBtn铁;

	private void create差异性Composite()
	{
		// TODO 差异性
		tabItem差异性 = new TabItem(tabFolder, SWT.NONE);
		tabItem差异性.setText("差异性设置");

		composite差异性 = new Composite(tabFolder, SWT.NONE);
		tabItem差异性.setControl(composite差异性);

		checkBtn造兵 = new Button(composite差异性, SWT.CHECK);
		checkBtn造兵.setBounds(0, 10, 121, 20);
		checkBtn造兵.setText("是否造兵");

		checkBtn升堡 = new Button(composite差异性, SWT.CHECK);
		checkBtn升堡.setBounds(127, 10, 121, 20);
		checkBtn升堡.setText("是否升堡");

		checkBtn金手指 = new Button(composite差异性, SWT.CHECK);
		checkBtn金手指.setBounds(254, 10, 152, 20);
		checkBtn金手指.setText("跟随金手指升级");

		checkBtn科技 = new Button(composite差异性, SWT.CHECK);
		checkBtn科技.setBounds(0, 41, 121, 20);
		checkBtn科技.setText("升级科技");

		combo科技 = new Combo(composite差异性, SWT.READ_ONLY);
		combo科技.setBounds(127, 36, 150, 28);

		checkBtn建筑 = new Button(composite差异性, SWT.CHECK);
		checkBtn建筑.setBounds(321, 41, 121, 20);
		checkBtn建筑.setText("升级建筑");

		combo建筑 = new Combo(composite差异性, SWT.READ_ONLY);
		combo建筑.setBounds(448, 38, 150, 28);

		Group group = new Group(composite差异性, SWT.NONE);
		group.setText("采集设定");
		group.setBounds(0, 77, 322, 225);

		checkBtn采集 = new Button(group, SWT.CHECK);
		checkBtn采集.setBounds(0, 24, 121, 20);
		checkBtn采集.setText("是否采集");

		checkBtn木 = new Button(group, SWT.CHECK);
		checkBtn木.setBounds(0, 50, 48, 20);
		checkBtn木.setText("木");

		checkBtn粮 = new Button(group, SWT.CHECK);
		checkBtn粮.setBounds(54, 50, 48, 20);
		checkBtn粮.setText("粮");

		checkBtn铁 = new Button(group, SWT.CHECK);
		checkBtn铁.setBounds(108, 50, 48, 20);
		checkBtn铁.setText("铁");

		checkBtn银 = new Button(group, SWT.CHECK);
		checkBtn银.setBounds(162, 50, 62, 20);
		checkBtn银.setText("银");

		combo野矿出兵方式 = new Combo(group, SWT.READ_ONLY);
		combo野矿出兵方式.setBounds(0, 76, 159, 28);

		combo盟矿出兵方式 = new Combo(group, SWT.READ_ONLY);
		combo盟矿出兵方式.setBounds(0, 110, 159, 28);

		combo队伍数量 = new Combo(group, SWT.READ_ONLY);
		combo队伍数量.setBounds(230, 47, 92, 28);

		combo在盟区 = new Combo(group, SWT.READ_ONLY);
		combo在盟区.setBounds(162, 76, 160, 28);

		combo采集联盟矿 = new Combo(group, SWT.READ_ONLY);
		combo采集联盟矿.setBounds(162, 110, 160, 28);

		label = new Label(group, SWT.NONE);
		label.setBounds(0, 144, 131, 20);
		label.setText("采集距离设定");

		comboMinKm = new Combo(group, SWT.READ_ONLY);
		comboMinKm.setBounds(0, 170, 102, 28);

		label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(106, 175, 25, 20);
		label_1.setText("到");

		comboMaxKm = new Combo(group, SWT.READ_ONLY);
		comboMaxKm.setBounds(137, 170, 102, 28);

		group_1 = new Group(composite差异性, SWT.NONE);
		group_1.setText("分组设定");
		group_1.setBounds(328, 77, 270, 128);

		combo分组编号 = new Combo(group_1, SWT.READ_ONLY);
		combo分组编号.setBounds(10, 24, 160, 28);

		checkBtn搜索起点 = new Button(group_1, SWT.CHECK);
		checkBtn搜索起点.setBounds(10, 58, 250, 20);
		checkBtn搜索起点.setText("自定义搜索起点");

		textX = new Text(group_1, SWT.BORDER);
		textX.setBounds(32, 84, 84, 26);

		Label lblX = new Label(group_1, SWT.WRAP | SWT.CENTER);
		lblX.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10,
				SWT.NORMAL));
		lblX.setBounds(10, 84, 20, 20);
		lblX.setText("X");

		Label lblY = new Label(group_1, SWT.WRAP | SWT.CENTER);
		lblY.setText("Y");
		lblY.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10,
				SWT.NORMAL));
		lblY.setBounds(131, 84, 20, 20);

		textY = new Text(group_1, SWT.BORDER);
		textY.setBounds(157, 84, 84, 26);

		button差异性保存 = new Button(composite差异性, SWT.NONE);
		button差异性保存.setBounds(500, 272, 98, 30);
		button差异性保存.setText("保存");
		button差异性保存.addSelectionListener(selectionAdapter);
	}

	// TODO 淘宝composite控件定义区 start
	private Label label序列号;
	private Text text序列号;
	private Link link淘宝;
	private Button buttonQQ群;
	private Button button设置;
	private Button button上传;
	private Button button下载;

	private void createTaoBaoComposite()
	{
		label序列号 = new Label(taobaoComposite, SWT.WRAP | SWT.CENTER);
		label序列号.setBounds(10, 13, 66, 20);
		label序列号.setText("序列号");

		text序列号 = new Text(taobaoComposite, SWT.BORDER);
		text序列号.setText("3A9B4AAF57E9EA6258F6B9F07BE94025");
		text序列号.setBounds(82, 10, 249, 26);

		link淘宝 = new Link(taobaoComposite, SWT.NONE);
		link淘宝.setBounds(497, 12, 86, 26);
		// 用A来标记超链接区域
		link淘宝.setText("<A>淘宝地址</A>");

		buttonQQ群 = new Button(taobaoComposite, SWT.NONE);
		buttonQQ群.setBounds(589, 8, 80, 30);
		buttonQQ群.setText("QQ群");

		button设置 = new Button(taobaoComposite, SWT.NONE);
		button设置.setBounds(675, 8, 80, 30);
		button设置.setText("设置");

		button上传 = new Button(taobaoComposite, SWT.NONE);
		button上传.setBounds(337, 8, 66, 30);
		button上传.setText("上传");

		button下载 = new Button(taobaoComposite, SWT.NONE);
		button下载.setBounds(409, 8, 66, 30);
		button下载.setText("下载");

		text序列号.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				text序列号.setToolTipText(text序列号.getText());
			}
		});

		link淘宝.addSelectionListener(selectionAdapter);
		buttonQQ群.addSelectionListener(selectionAdapter);
		button上传.addSelectionListener(selectionAdapter);
		button下载.addSelectionListener(selectionAdapter);

	}

	// TODO selection事件
	SelectionAdapter selectionAdapter = new SelectionAdapter()
	{

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			if (e.widget == buttonQQ群)
			{
				MessageUtil
						.showInfoDialog(shell, "QQ群地址", InfoConstant.QQGroup);
			} else if (e.widget == link淘宝)
			{
				// 以IE程序打开
				try
				{
					Runtime.getRuntime().exec(
							"explorer.exe " + InfoConstant.TaoBaoLink);
				} catch (IOException e1)
				{
				}
			} else if (e.widget == button上传)
			{
				String key = text序列号.getText();
				if (!CloudUtil.checkKey(key))
				{
					MessageUtil.showErrorDialog(shell, "序列号错误",
							"序列号格式不正确，请检查后重试");
					return;
				}
				try
				{
					CloudUtil.upload(key, getContent());
				} catch (Exception e1)
				{
					MessageUtil.showErrorDialog(shell, "上传出错", "上传失败，请稍后重试");
				}
			} else if (e.widget == button下载)
			{
				String key = text序列号.getText();
				if (!CloudUtil.checkKey(key))
				{
					MessageUtil.showErrorDialog(shell, "序列号错误",
							"序列号格式不正确，请检查后重试");
					return;
				}
				try
				{
					String content = CloudUtil.download(key);
					// 下载成功的将key保存到userInfo中
					Properties properties = new Properties();
					properties.setProperty(InfoConstant.keySerialNum, key);
					PropertyUtil.update(InfoConstant.PROPERTY_USER_INFO,
							properties);
					curSerialNum = key;
					serialNumberBean = DataConvertUtil.syncAndFilter(
							curSerialNum, content);
					initList();
				} catch (Exception e1)
				{
					MessageUtil.showErrorDialog(shell, "下载出错", "下载失败，请稍后重试");
				}
			} else if (e.widget == accList)
			{
				if (accList.getSelectionCount() <= 0)
				{
					return;
				}
				String select = accList.getSelection()[0];
				setInput(select.substring(select.indexOf(")") + 1));
			} else if (e.widget == checkBtn显示所有账号)
			{
				initList();
				setInput("");
			} else if (e.widget == button账号隐藏)
			{
				if (serialNumberBean == null)
				{
					MessageUtil.showErrorDialog(shell, "错误", "账号信息为空，请先下载同步账号");
					return;
				}
				HideShell hideShell = new HideShell(shell, SWT.SHELL_TRIM
						| SWT.PRIMARY_MODAL);
				hideShell.initList(serialNumberBean);
				hideShell.open();
				hideShell.layout();
				hideShell.addDisposeListener(new DisposeListener()
				{
					@Override
					public void widgetDisposed(DisposeEvent e)
					{
						initList();
						setInput("");
					}
				});
			} else if (e.widget == button账号编辑)
			{
				if (serialNumberBean == null)
				{
					MessageUtil.showErrorDialog(shell, "错误", "账号信息为空，请先下载同步账号");
					return;
				}
				ModifyShell modifyShell = new ModifyShell(shell, SWT.SHELL_TRIM
						| SWT.PRIMARY_MODAL);
				modifyShell.initList(serialNumberBean);
				modifyShell.open();
				modifyShell.layout();
				modifyShell.addDisposeListener(new DisposeListener()
				{
					@Override
					public void widgetDisposed(DisposeEvent e)
					{
						initList();
						setInput("");
					}
				});
			} else if (e.widget == button差异性保存)
			{
				if (selectId == null || selectId.equals(""))
				{
					return;
				}
				if (!serialNumberBean.getMap().containsKey(selectId))
				{
					return;
				}
				List<String> list = serialNumberBean.getIdQueue();
				int index = list.indexOf(selectId);
				AccountInfoBean bean = getAccInfoBeanFromView();

				list.set(index, bean.getId());
				serialNumberBean.getMap().put(bean.getId(), bean);

				MessageUtil.showInfoDialog(shell, "提示", "修改保存成功！");
			} else if (e.widget == button设置分组)
			{
				if (serialNumberBean == null)
				{
					MessageUtil.showErrorDialog(shell, "错误", "账号信息为空，请先下载同步账号");
					return;
				}
				GroupShell groupShell = new GroupShell(shell, SWT.SHELL_TRIM
						| SWT.PRIMARY_MODAL);
				groupShell.init(serialNumberBean);
				groupShell.open();
				groupShell.layout();
				groupShell.addDisposeListener(new DisposeListener()
				{
					@Override
					public void widgetDisposed(DisposeEvent e)
					{
						initList();
						setInput("");
					}
				});
			}
		}
	};
	private Button button账号编辑;

	private String getContent()
	{

		return "";
	}

	private AccountInfoBean getAccInfoBeanFromView()
	{
		AccountInfoBean bean = new AccountInfoBean();
		String account = text账号.getText();
		bean.setAccount(account);
		String password = text密码.getText();
		bean.setPassword(password);
		bean.setVersion(combo版本.getSelectionIndex());
		bean.setVersionType(combo版本号.getSelectionIndex());
		// 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置|8-金手指|9-科技设置|10-建筑设置|11-城堡是否在盟区
		SolderBean solderBean = new SolderBean();
		solderBean.setTrainSolder(checkBtn造兵.getSelection());
		bean.setSolderBean(solderBean);
		// 采集设置： 是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm
		GatherBean gatherBean = new GatherBean();
		gatherBean.setGather(checkBtn采集.getSelection());
		gatherBean.setWood(checkBtn木.getSelection());
		gatherBean.setFood(checkBtn粮.getSelection());
		gatherBean.setIron(checkBtn铁.getSelection());
		gatherBean.setSilver(checkBtn银.getSelection());
		gatherBean.setNum(combo队伍数量.getSelectionIndex());
		gatherBean.setArmyOutMethod(combo野矿出兵方式.getSelectionIndex());
		gatherBean.setGatherAlliasType(combo盟矿出兵方式.getSelectionIndex());
		gatherBean.setMinKm(comboMinKm.getSelectionIndex());
		gatherBean.setMaxKm(comboMaxKm.getSelectionIndex());
		bean.setGatherBean(gatherBean);

		GroupBean groupBean = new GroupBean();
		groupBean.setDefinReseach(checkBtn搜索起点.getSelection());
		groupBean.setGroupNum(combo分组编号.getSelectionIndex());
		groupBean.setX(getInt(textX.getText()));
		groupBean.setY(getInt(textY.getText()));
		bean.setGroupBean(groupBean);

		UpgradeBean upgradeBean = new UpgradeBean();
		upgradeBean.setUpgrade(checkBtn升堡.getSelection());
		bean.setUpgradeBean(upgradeBean);

		GoldenBean goldenBean = new GoldenBean();
		goldenBean.setGolden(checkBtn金手指.getSelection());
		bean.setGoldenBean(goldenBean);

		TechBean techBean = new TechBean();
		techBean.setTech(checkBtn科技.getSelection());
		techBean.setTechType(combo科技.getSelectionIndex());
		bean.setTechBean(techBean);

		BuildingBean buildingBean = new BuildingBean();
		buildingBean.setBuidling(checkBtn建筑.getSelection());
		buildingBean.setBuildingType(combo建筑.getSelectionIndex());
		bean.setBuildingBean(buildingBean);

		CastleInAliasBean castleInAliasBean = new CastleInAliasBean();
		castleInAliasBean.setIn(combo在盟区.getSelectionIndex());
		bean.setCastleInAliasBean(castleInAliasBean);

		return bean;
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
			// TODO: handle exception
		}

		return num;
	}

	public SerialNumberBean getSerialNumberBean()
	{
		return serialNumberBean;
	}

	public void setSerialNumberBean(SerialNumberBean serialNumberBean)
	{
		this.serialNumberBean = serialNumberBean;
		initList();
	}
}
