package org.ayo.lang;

import java.util.Properties;

public class OS {

	public static boolean isWin(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isLinux(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.startsWith("linux") || os.startsWith("unix")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isMac(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.startsWith("mac") || os.startsWith("o")){
			return true;
		}else{
			return false;
		}
	}
	
}
