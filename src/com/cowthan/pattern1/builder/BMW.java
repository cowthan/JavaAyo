package com.cowthan.pattern1.builder;

public class BMW extends ICar{

	@Override
	public void start() {
		System.out.println("宝马--发动");
	}

	@Override
	public void stop() {
		System.out.println("宝马--停止");
	}

	@Override
	public void alarm() {
		System.out.println("宝马--喇叭响");
	}

	@Override
	public void engineBoom() {
		System.out.println("宝马--引擎声音");
	}
}
