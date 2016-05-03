package com.cowthan.pattern1.command;

public class PG extends Group{

	@Override
	public void find() {
		System.out.println("找到美工组");
	}

	@Override
	public void add() {
		System.out.println("美工组：增加需求");
	}

	@Override
	public void delete() {
		System.out.println("美工组：删除需求");
	}

	@Override
	public void change() {
		System.out.println("美工组：更改需求");
	}

	@Override
	public void plan() {
		System.out.println("美工组：给出变更计划");
	}

}
