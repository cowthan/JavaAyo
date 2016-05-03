package com.cowthan.keystore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KeyStoreWriter {
	//jarsigner -verbose -keystore C:\Users\cowthan\Desktop\MEI家园\meiuser -signedjar C:\Users\cowthan\Desktop\b15a7b22a6b2ac6ebfd47791ff11f40f\Qihusign.apk C:\Users\cowthan\Desktop\b15a7b22a6b2ac6ebfd47791ff11f40f\Qihuunsign.apk mei用户端
	
	public static void main(String[] args) {
		
		List<String> cmds = getKeystoreCmds("C:/Users/Administrator/Desktop/sign_verification", 
				"C:/Users/Administrator/Desktop/sign_verification/iwo_zhigu.key", 
				"iwo_daogou");
		for(String cmd: cmds){
			System.out.println("\n" + cmd + "\n");
		}
	}

	/**
	 * 根据指定目录，生成下面所有apk文件对应的keystore命令
	 * @param dirPath
	 * @return
	 */
	public static List<String> getKeystoreCmds(String dirPath, String keystorePath, String alias){
		List<String> apks = listFiles(dirPath);
		if(apks == null || apks.size() == 0){
			System.out.println(dirPath + "下没有文件");
			return new ArrayList<String>();
		}else{
			List<String> list = new ArrayList<String>();
			for(String apk: apks){
				list.add(getKeystoreCmd(keystorePath, alias, apk));
			}
			return list;
		}
	}
	
	/**
	 * 生成一个keystore命令
	 * @param keystorePath
	 * @param alias
	 * @param unsignedApkPath
	 * @return
	 */
	public static String getKeystoreCmd(String keystorePath, String alias, String unsignedApkPath){
		String cmdTpml = "jarsigner -verbose -keystore {kestore-path} -signedjar {signed-apk-path} {unsigned-apk-path} {alias}";
		//String signedPath = unsignedApkPath.replace(".apk", "_s.apk");
		String signedPath = processOutPutFilename(unsignedApkPath);
		String cmd = cmdTpml
				.replace("{kestore-path}", keystorePath)
				.replace("{signed-apk-path}", signedPath)
				.replace("{unsigned-apk-path}", unsignedApkPath)
				.replace("{alias}", alias);
		//System.out.println(cmd);
		return cmd;
	}
	
	/**
	 * 遍历指定目录，获取所有文件，不包括子目录，也不递归，就第一层
	 * @param dirPath
	 * @return
	 */
	public static List<String> listFiles(String dirPath)
	{
		List<String> list = new ArrayList<String>();
		File dir = new File(dirPath);
		if(dir.exists()){
			if(dir.isDirectory()){
				File[] files = dir.listFiles();
				if(files == null || files.length == 0){
					System.out.println(dirPath + "下没有文件");
				}else{
					for(File f: files){
						if(f.isFile()){
							list.add(f.getAbsolutePath());
						}
					}
				}
			}else{
				System.out.println(dirPath + "不是目录");
			}
		}else{
			System.out.println(dirPath + "不存在");
		}
		return list;
	}
	
	public static String processOutPutFilename(String origName){
		return origName.replace("com.iwomedia.zhaoyang.modify.encrypted.", "daogou_") + "___out";
	}
}
