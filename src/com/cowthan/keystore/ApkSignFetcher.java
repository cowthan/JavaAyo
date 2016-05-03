package com.cowthan.keystore;

import com.cowthan.cmd.CmdExecutor;

public class ApkSignFetcher {
	
	public static void main(String[] args) {
		String pkg = "com.beautyz.buyer";
		String alia = "buyer";
		String keystorePath = "C:\\Users\\Administrator\\Desktop\\公司工作内容\\buyer.keystore";
		String keystorePassword = "iwo2015beautybuyer$";
		String commandStr = "keytool -list -alias {alia} -keystore {keystore-path} -storepass {keystore-password} -keypass {keystore-password}";
		// String commandStr = "ipconfig";
		commandStr = commandStr.replace("{alia}", alia).replace("{keystore-path}", keystorePath).replace("{keystore-password}", keystorePassword);
		String res = CmdExecutor.exeCmd(commandStr);
		System.out.println("执行结果：\r\n" + res);
		int index = res.indexOf("(SHA1): ");
		index = index += "(SHA1): ".length();
		res = res.substring(index);
		System.out.println(res);
		System.out.println("提取结果：");
		System.out.println("包名：" + pkg + "\r\n签名：" + res.replace(":", "").trim());
	}
	
}
