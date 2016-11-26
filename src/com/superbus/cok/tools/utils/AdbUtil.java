package com.superbus.cok.tools.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AdbUtil
{
	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_EXIT = "exit\n";
	public static final String COMMAND_LINE_END = "\n";
	
	public static void main(String[] args)
	{
		runCommand("adb devices");
	}
	
	/**
     * @param command  command
     * @return null
     */
    public static String runCommand(String command) {
        BufferedReader br2 = null;
        String line = null;
        InputStream is = null;
        InputStreamReader isReader = null;
        try {
            Process proc = Runtime.getRuntime().exec(command);
            is = proc.getInputStream();
            isReader = new InputStreamReader(is, "utf-8");
            br2 = new BufferedReader(isReader);
            while ((line = br2.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            return line;
        } finally {
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }

            if (br2 != null) {
                try {
                    br2.close();
                } catch (IOException e) {
                    // TODO
                }
            }
        }
        return line;
    }

	private AdbUtil()
	{
		throw new AssertionError();
	}

	/**
	 * check whether has root permission
	 * 
	 * @return
	 */
	public static boolean checkRootPermission()
	{
		return execCommand("echo root", true, false).result == 0;
	}

	/**
	 * execute shell command, default return result msg
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String command, boolean isRoot)
	{
		return execCommand(new String[] { command }, isRoot, true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(List<String> commands, boolean isRoot)
	{
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}),
				isRoot, true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String[] commands, boolean isRoot)
	{
		return execCommand(commands, isRoot, true);
	}

	/**
	 * execute shell command
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String command, boolean isRoot,
			boolean isNeedResultMsg)
	{
		return execCommand(new String[] { command }, isRoot, isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(List<String> commands, boolean isRoot,
			boolean isNeedResultMsg)
	{
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}),
				isRoot, isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * 
	 * 
	 *         if isNeedResultMsg is false, {@link CommandResult#successMsg} is
	 *         null and {@link CommandResult#errorMsg} is null.
	 * 
	 * 
	 *         if {@link CommandResult#result} is -1, there maybe some
	 *         excepiton.
	 * 
	 * 
	 */
	public static CommandResult execCommand(String[] commands, boolean isRoot,
			boolean isNeedResultMsg)
	{
		int result = -1;
		if (commands == null || commands.length == 0)
		{
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try
		{
			process = Runtime.getRuntime().exec(
					isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands)
			{
				if (command == null)
				{
					continue;
				}

				// donnot use os.writeBytes(commmand), avoid chinese charset
				// error
				os.write(command.getBytes());
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();

			result = process.waitFor();
			// get command result
			if (isNeedResultMsg)
			{
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null)
				{
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null)
				{
					errorMsg.append(s);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (os != null)
				{
					os.close();
				}
				if (successResult != null)
				{
					successResult.close();
				}
				if (errorResult != null)
				{
					errorResult.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			if (process != null)
			{
				process.destroy();
			}
		}
		return new CommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	/**
	 * result of command
	 * 
	 * 
	 * 
	 * {@link CommandResult#result} means result of command, 0 means normal,
	 * else means error, same to excute in linux shell
	 * 
	 * 
	 * {@link CommandResult#successMsg} means success message of command result
	 * 
	 * 
	 * {@link CommandResult#errorMsg} means error message of command result
	 * 
	 * 
	 * 
	 * 
	 * @author Trinea 2013-5-16
	 */
	public static class CommandResult
	{

		/** result of command **/
		public int result;
		/** success message of command result **/
		public String successMsg;
		/** error message of command result **/
		public String errorMsg;

		public CommandResult(int result)
		{
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg)
		{
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}

	/**
	 * 其中commands表示依次执行的shell命令数组 isRoot表示是否以su用户执行(需要手机已经root)
	 * isNeedResultMsg表示是否存储命令执行成功及失败后的信息。
	 */

	/**
	 * result of command
	 * 
	 * @author Trinea 2013-5-16
	 */
}
