package com.superbus.cok.tools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.superbus.cok.tools.bean.AccountInfoBean;
import com.superbus.cok.tools.bean.BuildingBean;
import com.superbus.cok.tools.bean.CastleInAliasBean;
import com.superbus.cok.tools.bean.GatherBean;
import com.superbus.cok.tools.bean.GoldenBean;
import com.superbus.cok.tools.bean.SerialNumberBean;
import com.superbus.cok.tools.bean.SolderBean;
import com.superbus.cok.tools.bean.TechBean;
import com.superbus.cok.tools.bean.UpgradeBean;
import com.superbus.cok.tools.utils.ImageUtil;

public class ModifyShell extends Shell
{
	private List to;
	private List from;
	private DataConstant dataConstant = DataConstant.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			Display display = Display.getDefault();
			ModifyShell shell = new ModifyShell(display, SWT.SHELL_TRIM);
			shell.open();
			shell.layout();
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell
	 * 
	 * @param display
	 * @param style
	 * @wbp.parser.constructor
	 */
	public ModifyShell(Display display, int style)
	{
		super(display, style);
		createContents();
	}

	public ModifyShell(Shell shell, int style)
	{
		super(shell, style);
		createContents();
	}

	private SerialNumberBean serialNumberBean;

	/**
	 * Create contents of the window
	 */
	protected void createContents()
	{
		setText(NameConstant.ModifyShellName);
		setSize(861, 554);
		setLocation(Display.getDefault().getClientArea().width / 2
				- getSize().x / 2, Display.getDefault().getClientArea().height
				/ 2 - getSize().y / 2);
		final Image image = ImageUtil.getSuperbus();
		if (image != null)
		{
			setImage(image);
		}

		String[] to_ = new String[0];

		from = new List(this, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		from.setBounds(5, 36, 200, 464);
		from.setToolTipText("账号信息");
		to = new List(this, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		to.setBounds(285, 36, 200, 464);
		to.setItems(to_);
		to.setToolTipText("需要一键修改的账号");

		//
		SelectionAdapter listener = new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				Button selected = (Button) e.widget;
				// 通过widget上的text进行匹配。
				if (selected.getText().equalsIgnoreCase("<"))
				{
					move(to.getSelection(), to, from);
				} else if (selected.getText().equalsIgnoreCase("<<"))
				{
					move(to.getItems(), to, from);
				} else if (selected.getText().equalsIgnoreCase(">"))
				{
					move(from.getSelection(), from, to);
				} else if (selected.getText().equalsIgnoreCase(">>"))
				{
					move(from.getItems(), from, to);
				} else if (selected.getText().equalsIgnoreCase("^"))
				{
					int index = to.getSelectionIndex();
					if (index <= 0)
					{
						return;
					} else
					{
						String currentValue = to.getItem(index);
						to.setItem(index, to.getItem(index - 1));
						to.setItem(index - 1, currentValue);
						to.setSelection(index - 1);
					}
				} else if (selected.getText().equalsIgnoreCase("v"))
				{
					int index = to.getSelectionIndex();
					if (index >= to.getItemCount() - 1)
					{
						return;
					} else
					{
						String currentValue = to.getItem(index);
						to.setItem(index, to.getItem(index + 1));
						to.setItem(index + 1, currentValue);
						to.setSelection(index + 1);
					}
				}
			}

			public void move(String[] items, List from, List to)
			{
				for (int i = 0; i < items.length; i++)
				{
					from.remove(items[i]);
					to.add(items[i]);
				}
			}
		};
		final Button lb = new Button(this, SWT.NONE);
		lb.setText("<");
		lb.setBounds(217, 115, 50, 27);
		lb.addSelectionListener(listener);
		final Button llb = new Button(this, SWT.NONE);
		llb.setBounds(217, 167, 50, 27);
		llb.setText("<<");
		llb.addSelectionListener(listener);
		final Button rb = new Button(this, SWT.NONE);
		rb.setBounds(217, 253, 50, 27);
		rb.setText(">");
		rb.addSelectionListener(listener);
		final Button rrb = new Button(this, SWT.NONE);
		rrb.setBounds(217, 305, 50, 27);
		rrb.setText(">>");

		Label label = new Label(this, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setBounds(54, 10, 76, 20);
		label.setText("账号");

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(322, 10, 126, 20);
		label_1.setText("要编辑的账号");

		userInfoComposite = new Composite(this, SWT.NONE);
		userInfoComposite.setBounds(491, 36, 351, 464);
		rrb.addSelectionListener(listener);

		createUserInfoComposite();
	}

	public void initList(SerialNumberBean serialNumberBean)
	{
		if (serialNumberBean == null)
		{
			return;
		}
		this.serialNumberBean = serialNumberBean;
		java.util.List<String> allAcc = serialNumberBean.getIdQueue();
		from.setItems(allAcc.toArray(new String[] {}));
	}

	Composite userInfoComposite;
	// TODO 用户信息栏
	TabFolder tabFolder;
	private TabItem tabItem差异性;
	private Composite composite差异性;
	private Button button差异性保存;
	private Button checkBtn金手指;
	private Button checkBtn科技;
	private Button checkBtn银;
	private Combo combo野矿出兵方式;
	private Combo combo盟矿出兵方式;
	private Combo combo队伍数量;
	private Combo combo在盟区;
	private Combo combo采集联盟矿;
	Button checkBtn造兵;
	Button checkBtn升堡;
	Combo combo科技;
	Button checkBtn建筑;
	Combo combo建筑;
	Button checkBtn采集;
	Button checkBtn木;
	Button checkBtn粮;
	Button checkBtn铁;

	private void createUserInfoComposite()
	{
		tabFolder = new TabFolder(userInfoComposite, SWT.NONE);
		tabFolder.setLocation(0, 10);
		tabFolder.setSize(new Point(351, 454));
		tabFolder.setSelection(0);

		create差异性Composite();

		init差异性Composite();

	}

	private void init差异性Composite()
	{
		combo科技.setItems(dataConstant.getUpgradeTechData());
		combo建筑.setItems(dataConstant.getUpgradeBuildingData());
		combo队伍数量.setItems(dataConstant.getArmyOutNumData());
		combo野矿出兵方式.setItems(dataConstant.getArmyOutMethodData());
		combo在盟区.setItems(dataConstant.getCastleInAlliasData());
		combo盟矿出兵方式.setItems(dataConstant.getGatherAlliasMethodData());
		combo采集联盟矿.setItems(dataConstant.getGatherAlliasTypeData());
	}

	private void create差异性Composite()
	{
		// TODO 差异性
		tabItem差异性 = new TabItem(tabFolder, SWT.NONE);
		tabItem差异性.setText("差异性设置");

		composite差异性 = new Composite(tabFolder, SWT.NONE);
		tabItem差异性.setControl(composite差异性);

		checkBtn造兵 = new Button(composite差异性, SWT.CHECK);
		checkBtn造兵.setBounds(5, 10, 121, 20);
		checkBtn造兵.setText("是否造兵");

		checkBtn升堡 = new Button(composite差异性, SWT.CHECK);
		checkBtn升堡.setBounds(135, 10, 121, 20);
		checkBtn升堡.setText("是否升堡");

		checkBtn金手指 = new Button(composite差异性, SWT.CHECK);
		checkBtn金手指.setBounds(5, 36, 152, 20);
		checkBtn金手指.setText("跟随金手指升级");

		checkBtn科技 = new Button(composite差异性, SWT.CHECK);
		checkBtn科技.setBounds(5, 67, 121, 20);
		checkBtn科技.setText("升级科技");

		combo科技 = new Combo(composite差异性, SWT.READ_ONLY);
		combo科技.setBounds(135, 62, 150, 28);

		checkBtn建筑 = new Button(composite差异性, SWT.CHECK);
		checkBtn建筑.setBounds(5, 99, 121, 20);
		checkBtn建筑.setText("升级建筑");

		combo建筑 = new Combo(composite差异性, SWT.READ_ONLY);
		combo建筑.setBounds(135, 96, 150, 28);

		Group group = new Group(composite差异性, SWT.NONE);
		group.setText("采集设定");
		group.setBounds(0, 133, 336, 152);

		checkBtn采集 = new Button(group, SWT.CHECK);
		checkBtn采集.setBounds(5, 24, 121, 20);
		checkBtn采集.setText("是否采集");

		checkBtn木 = new Button(group, SWT.CHECK);
		checkBtn木.setBounds(5, 50, 48, 20);
		checkBtn木.setText("木");

		checkBtn粮 = new Button(group, SWT.CHECK);
		checkBtn粮.setBounds(59, 50, 48, 20);
		checkBtn粮.setText("粮");

		checkBtn铁 = new Button(group, SWT.CHECK);
		checkBtn铁.setBounds(113, 50, 48, 20);
		checkBtn铁.setText("铁");

		checkBtn银 = new Button(group, SWT.CHECK);
		checkBtn银.setBounds(167, 50, 62, 20);
		checkBtn银.setText("银");

		combo野矿出兵方式 = new Combo(group, SWT.READ_ONLY);
		combo野矿出兵方式.setBounds(5, 76, 159, 28);

		combo盟矿出兵方式 = new Combo(group, SWT.READ_ONLY);
		combo盟矿出兵方式.setBounds(5, 110, 159, 28);

		combo队伍数量 = new Combo(group, SWT.READ_ONLY);
		combo队伍数量.setBounds(235, 47, 92, 28);

		combo在盟区 = new Combo(group, SWT.READ_ONLY);
		combo在盟区.setBounds(167, 76, 160, 28);

		combo采集联盟矿 = new Combo(group, SWT.READ_ONLY);
		combo采集联盟矿.setBounds(167, 110, 160, 28);

		button差异性保存 = new Button(composite差异性, SWT.NONE);
		button差异性保存.setBounds(235, 308, 98, 30);
		button差异性保存.setText("保存");
		button差异性保存.addSelectionListener(selectionAdapter);
	}

	SelectionAdapter selectionAdapter = new SelectionAdapter()
	{

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			if (e.widget == button差异性保存)
			{
				String[] needModify = to.getItems();
				System.err.println(needModify.length);
				for (int i = 0; i < needModify.length; i++)
				{
					AccountInfoBean bean = serialNumberBean.getMap().get(
							needModify[i]);
					// 版本|版本号|账号|密码|造兵设置|采集设置|分组设置|升级设置|8-金手指|9-科技设置|10-建筑设置|11-城堡是否在盟区
					SolderBean solderBean = new SolderBean();
					solderBean.setTrainSolder(checkBtn造兵.getSelection());
					bean.setSolderBean(solderBean);
					// 采集设置：
					// 是否采集，木，粮，铁，银，联盟矿，队伍数量，野矿出兵方式，盟矿出兵方式（新增），minKm，maxKm
					GatherBean gatherBean = new GatherBean();
					gatherBean.setGather(checkBtn采集.getSelection());
					gatherBean.setWood(checkBtn木.getSelection());
					gatherBean.setFood(checkBtn粮.getSelection());
					gatherBean.setIron(checkBtn铁.getSelection());
					gatherBean.setSilver(checkBtn银.getSelection());
					gatherBean.setNum(combo队伍数量.getSelectionIndex());
					gatherBean
							.setArmyOutMethod(combo野矿出兵方式.getSelectionIndex());
					gatherBean.setGatherAlliasType(combo盟矿出兵方式
							.getSelectionIndex());
					gatherBean.setMinKm(bean.getGatherBean().getMinKm());
					gatherBean.setMaxKm(bean.getGatherBean().getMaxKm());
					bean.setGatherBean(gatherBean);

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

					serialNumberBean.getMap().put(needModify[i], bean);
				}
				AccountShell.getInstance().setSerialNumberBean(serialNumberBean);
				dispose();
			}
		}
	};

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

}
