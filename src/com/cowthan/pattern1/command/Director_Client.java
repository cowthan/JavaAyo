package com.cowthan.pattern1.command;

public class Director_Client {
	public static void main(String[] args) {
		
		//===接头人，用户就知道它就行
		Invoker who = new Invoker();
		
		CMD cmd = new CMD_AddRequiement();
		who.setCMD(cmd);
		who.action();
		
		CMD cmd2 = new CMD_DeletePage();
		who.setCMD(cmd2);
		who.action();
	}
}
