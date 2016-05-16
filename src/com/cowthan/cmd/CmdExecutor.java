package com.cowthan.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Deprecated
public class CmdExecutor {
	public static String exeCmd(String commandStr, File workDir) {
		System.out.println("--------------------执行：" + commandStr);
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr, null, workDir); //exec(new String[]{"cmd.exe", "/c", "cd D:/ws0425-jee/Java-Ayo/bin"}, null, null);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			String str = sb.toString();
			//str = new String(str.getBytes("GBK"), "utf-8");
			
			int exitVal = p.waitFor();
			System.out.println(exitVal == 0 ? "成功" : "失败");
			
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "| no message is good message |";
	}

	public static void main(String[] args) {
		String commandStr = "dir";
		System.out.println(CmdExecutor.exeCmd("cmd.exe   /c   dir", null));
		Runtime.getRuntime().traceMethodCalls(true);
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(Runtime.getRuntime().freeMemory()/1024/1024 + "M");
		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024 + "M");
		System.out.println(Runtime.getRuntime().maxMemory()/1024/1024 + "M");
		
		System.out.println(CmdExecutor.exeCmd("cmd.exe   /c   dir", new File("D:\\ws0425-jee\\Java-Ayo\\bin")));
		System.out.println(CmdExecutor.exeCmd("cmd.exe   /c   java com.cowthan.cmd.test", new File("D:\\ws0425-jee\\Java-Ayo\\bin")));
		//System.out.println(CmdExecutor.exeCmd("cmd.exe   /c   ipconfig", new File("D:\\ws0425-jee\\Java-Ayo\\bin")));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
