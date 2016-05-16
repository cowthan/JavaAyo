package com.cowthan.cmd;

import java.io.File;

public class Cmd {

	////////常用命令
	public static String ipconfig(){
		return new CmdLineWindow().exec("ipconfig", null);
	}
	public static String dir(File dir){
		return new CmdLineWindow().exec("dir", dir);
	}
	public static String ping(String host){
		return new CmdLineWindow().exec("ping " + host, null);
	}
	public static String systeminfo(){
		return new CmdLineWindow().exec("systeminfo", null);
	}
	public static void cd(){
		throw new RuntimeException("没有cd命令，传入的dir参数就是工作目录");
	}
	
	///////常用命令：java
	public static String java(Class<?> klass, File dir, String s){
		//堆内存溢出测试：先设置堆大小为20M，好测试：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
		/*
		 Xms20m：堆的最小值，20M
		 Xmx20m：堆的最大值，20M
		 -XX:+HeapDumpOnOutOfMemoryError：出现内存溢出时，Dump除当前内存堆转储快照
		 */
		return new CmdLineWindow().exec("java " + " " + s + " " + klass.getName(), dir);
	}

	
}
