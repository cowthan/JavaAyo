package com.cowthan.pattern1.command;

public abstract class Group {
	public abstract void find(); //让别人能够找到你这个组
	public abstract void add();  //添加功能
	public abstract void delete(); //删除功能
	public abstract void change(); //更改功能
	public abstract void plan(); //给出所有变更计划
}
