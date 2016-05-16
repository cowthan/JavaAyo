package com.cowthan.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Properties;

import org.ayo.lang.OS;

/**
 * 代表一个命令行窗口，可以输入命令，拿到执行结果，也可以再输入
 * 
 * 有一个启动命令
 * 
 */
public class CmdLineWindow {

	public CmdLineWindow(){
		
	}
	
	public String exec(String cmd, File dir){
		if(OS.isWin()){
			return execInWin(cmd, dir);
		}else if(OS.isLinux()){
			return execInLinux(cmd, dir);
		}
		return "没找到平台";
	}
	
	private String execInWin(String cmd, File dir){
		return execWhatEver(cmd, dir, "cmd.exe   /c   ");
	}
	
	private String execInLinux(String cmd, File dir){
		return execWhatEver(cmd, dir, "/bin/bash");
	}
	
	private String execWhatEver(String cmd, File dir, String prefix){
		BufferedReader br = null;
		try {
			cmd = prefix + " " + cmd;
			System.out.println("--------------------执行：" + cmd);
			Process p = Runtime.getRuntime().exec(cmd, null, dir); //exec(new String[]{"cmd.exe", "/c", "cd D:/ws0425-jee/Java-Ayo/bin"}, null, null);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			String str = sb.toString();
			//str = new String(str.getBytes("GBK"), "utf-8");
			
			int exitVal = p.waitFor();
			//System.out.println(exitVal == 0 ? "成功" : "失败");
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
		String res = Cmd.java(test2.class, new File("D:\\ws0425-jee\\Java-Ayo\\bin"), "d");
		System.out.println(res);
		
		Cmd.systeminfo();
		
		//必须的，否则程序会退出
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
