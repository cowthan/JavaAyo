package com.cowthan.pattern1.command;

public class RG extends Group{

	@Override
	public void find() {
		System.out.println("找到需求组");
	}

	@Override
	public void add() {
		System.out.println("需求组：增加需求");
	}

	@Override
	public void delete() {
		System.out.println("需求组：删除需求");
	}

	@Override
	public void change() {
		System.out.println("需求组：更改需求");
	}

	@Override
	public void plan() {
		System.out.println("需求组：给出变更计划");
	}

}
