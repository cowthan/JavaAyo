package com.cowthan.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CmdExecutor {
	public static String exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			String str = sb.toString();
			str = new String(str.getBytes("gbk"), "utf-8");
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
		// String commandStr = "ipconfig";
		System.out.println(CmdExecutor.exeCmd("ipconfig"));
		System.out.println(CmdExecutor.exeCmd("D:"));
		System.out.println(CmdExecutor.exeCmd("gradlew bintrayUpload"));
		
	}
}
