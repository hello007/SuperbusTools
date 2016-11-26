package com.superbus.cok.tools;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.superbus.cok.tools.bean.AccountInfoBean;
import com.superbus.cok.tools.bean.SerialNumberBean;
import com.superbus.cok.tools.utils.ImageUtil;

public class HideShell extends Shell
{
	private List to;
	private List from;

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
			HideShell shell = new HideShell(display, SWT.SHELL_TRIM);
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
	public HideShell(Display display, int style)
	{
		super(display, style);
		createContents();
	}

	public HideShell(Shell shell, int style)
	{
		super(shell, style);
		createContents();
	}

	public void initList(SerialNumberBean serialNumberBean)
	{
		if (serialNumberBean == null)
		{
			return;
		}
		this.serialNumberBean = serialNumberBean;
		Map<String, AccountInfoBean> map = serialNumberBean.getMap();
		java.util.List<String> showList = new ArrayList<String>();
		java.util.List<String> hideList = new ArrayList<String>();
		for (String key : serialNumberBean.getIdQueue())
		{
			AccountInfoBean bean = map.get(key);
			if (bean.isShow())
			{
				showList.add(key);
			} else
			{
				hideList.add(key);
			}
		}
		from.setItems(showList.toArray(new String[] {}));
		to.setItems(hideList.toArray(new String[] {}));

	}

	private SerialNumberBean serialNumberBean;

	/**
	 * Create contents of the window
	 */
	protected void createContents()
	{
		setText(NameConstant.HideShellName);
		setSize(522, 602);
		setLocation(Display.getDefault().getClientArea().width / 2
				- getSize().x / 2, Display.getDefault().getClientArea().height
				/ 2 - getSize().y / 2);
		final Image image = ImageUtil.getSuperbus();
		if (image != null)
		{
			setImage(image);
		}

		from = new List(this, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		from.setBounds(5, 36, 200, 464);
		from.setToolTipText("显示的账号");
		to = new List(this, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		to.setBounds(285, 36, 200, 464);
		to.setToolTipText("隐藏的账号");

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
		rrb.addSelectionListener(listener);

		Label label = new Label(this, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setBounds(54, 10, 76, 20);
		label.setText("显示的账号");

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(322, 10, 126, 20);
		label_1.setText("隐藏的账号");

		button保存 = new Button(this, SWT.NONE);
		button保存.setBounds(387, 517, 98, 30);
		button保存.setText("保存");
		button保存.addSelectionListener(selectionAdapter);

	}

	Button button保存;

	SelectionAdapter selectionAdapter = new SelectionAdapter()
	{

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			if (e.widget == button保存)
			{
				Map<String, AccountInfoBean> map = serialNumberBean.getMap();
				String[] hideAcc = to.getItems();
				for (int i = 0; i < hideAcc.length; i++)
				{
					AccountInfoBean bean = map.get(hideAcc[i]);
					bean.setShow(false);
				}
				String[] showAcc = from.getItems();
				for (int i = 0; i < showAcc.length; i++)
				{
					AccountInfoBean bean = map.get(showAcc[i]);
					bean.setShow(true);
				}
				AccountShell.getInstance().setSerialNumberBean(serialNumberBean);
				dispose();
			}
		}
	};

	@Override
	protected void checkSubclass()
	{
	}

}
