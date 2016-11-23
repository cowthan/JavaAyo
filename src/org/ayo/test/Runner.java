package org.ayo.test;

import java.io.File;

import org.ayo.io.Files;

import com.cowthan.cmd.CmdLineWindow;

public class Runner {

	public static final String dir = "C:\\Users\\Administrator\\Desktop\\java-runner\\";
	
	public static Runner code(String javaCode){
		
		Runner r = new Runner();
		r.setCode(javaCode);
		
		return r;
	}
	
	
	private String code;
	
	private void setCode(String code){
		this.code = code;
	}
	
	public void run(){
		
		///生成java文件
		String path = dir + "Tester.java";
		Files.steam.output(code, new File(path), false);
		
		///javac命令
		CmdLineWindow win = new CmdLineWindow();
		String res = win.exec("javac " + "Tester.java", new File(dir));
		
		///java命令运行，拿到命令行返回结果
		res = win.exec("java Tester", new File(dir));
		
		///输出命令行结果
		System.out.println(res);
	}
	
	
	public static void main(String[] args) {
		
		String javaCode = Files.steam.string(new File(dir + "tmpl.java"), "\r\n");
		
		Runner.code(javaCode).run();
		
		
		
	}
	
}
