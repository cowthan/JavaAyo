package com.cowthan.pattern1.command;

public class Invoker {
	private CMD cmd;
	
	/**
	 * 接受用户命令
	 * @param cmd
	 */
	public void setCMD(CMD cmd){this.cmd = cmd;}
	
	/**
	 * 接收到用户命令后，开始行动
	 */
	public void action(){
		cmd.execute();
	}
}
