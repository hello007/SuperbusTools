package com.superbus.cok.tools.version;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.superbus.cok.tools.utils.HttpsUtil;

public class VersionUtil
{
	private static Logger logger = Logger.getLogger(VersionUtil.class);

	public static void main(String[] args)
	{
		VersionUtil.getInstance().update();
	}

	private static VersionUtil version = new VersionUtil();

	public static VersionUtil getInstance()
	{
		return version;
	}

	private VersionBean versionBean = new VersionBean();

	private static String[] keys = { "currentVersion", "forceUpdate",
			"updateUrl", "content" };

	private static boolean sync = false;

	private VersionUtil()
	{

	}

	@SuppressWarnings("unchecked")
	private void needUpdate()
	{
		sync = false;
		Map<String, Object> data = (Map<String, Object>) JSON.parse(HttpsUtil
				.queryVersion());
		String ver = data.get(keys[0]).toString();
		versionBean.setVersion(ver);
		versionBean.setForceUpdate(Boolean.parseBoolean(data.get(keys[1])
				.toString()));
		versionBean.setUpdateUrl((String) data.get(keys[2]));
		versionBean.setContent((String) data.get(keys[3]));
		sync = true;
	}

	/**
	 * 是否需要更新
	 * 
	 * @return -1：不需要；0：可选更新；1：强制更新
	 */

	public int update()
	{
		VersionBean versionBean = getVersionBean();
		String newVersion = versionBean.getVersion();
		String curVersion = VersionInfo.currentVersion;
		logger.info("curVersion: " + curVersion + ";   versionBean: "
				+ versionBean);
		int result = update(newVersion, curVersion);
		logger.info("update result: " + result);
		return result;
	}

	private int update(String newVersion, String curVersion)
	{
		if (newVersion == null)
		{
			return -1;
		}
		if (curVersion == null)
		{
			return 1;
		}
		int curMax = 0, curMin = 0, newMax = 0, newMin = 0;
		String[] newStrings = newVersion.split(".");
		String[] curStrings = curVersion.split(".");
		if (newStrings.length >= 2)
		{
			newMax = parseInt(newStrings[0]);
			newMin = parseInt(newStrings[1]);
		} else if (newStrings.length == 1)
		{
			newMax = parseInt(newStrings[0]);
		} else
		{
			return -1;
		}
		if (curStrings.length >= 2)
		{
			curMax = parseInt(curStrings[0]);
			curMin = parseInt(curStrings[1]);
		} else if (newStrings.length == 1)
		{
			curMax = parseInt(curStrings[0]);
		} else
		{
			return 1;
		}
		if (newMax > curMax || newMin > curMin)
		{
			if (versionBean.isForceUpdate())
			{
				return 1;
			}
			return 0;
		}
		return -1;
	}

	private int parseInt(String input)
	{
		int result = 0;
		try
		{
			result = Integer.parseInt(input);
		} catch (Exception e)
		{

		}
		return result;
	}

	public VersionBean getVersionBean()
	{
		if (!sync)
		{
			needUpdate();
		}
		return versionBean;
	}

	public class VersionBean
	{
		private String version = VersionInfo.currentVersion;

		private boolean forceUpdate = false;

		private String updateUrl = null;

		private String content = null;

		@Override
		public String toString()
		{
			return "version:" + version + ";forceUpdate:" + forceUpdate
					+ ";url:" + updateUrl + ";content:" + content;
		}

		public String getVersion()
		{
			return version;
		}

		public void setVersion(String version)
		{
			this.version = version;
		}

		public boolean isForceUpdate()
		{
			return forceUpdate;
		}

		public void setForceUpdate(boolean forceUpdate)
		{
			this.forceUpdate = forceUpdate;
		}

		public String getUpdateUrl()
		{
			return updateUrl;
		}

		public void setUpdateUrl(String updateUrl)
		{
			this.updateUrl = updateUrl;
		}

		public String getContent()
		{
			return content;
		}

		public void setContent(String content)
		{
			this.content = content;
		}

	}
}
