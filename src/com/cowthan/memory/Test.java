package com.cowthan.memory;

import java.io.File;

import com.cowthan.cmd.Cmd;

public class Test {

	public static void main(String[] args) {
		File dir = new File("D:\\ws0425-jee\\Java-Ayo\\bin");
		Cmd.java(HeapOOM.class, dir, "-Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError");
		
	}
	
}
