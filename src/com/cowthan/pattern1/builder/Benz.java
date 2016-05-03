package com.cowthan.pattern1.builder;

public class Benz extends ICar{
	
	@Override
	public void start() {
		System.out.println("奔驰--发动");
	}

	@Override
	public void stop() {
		System.out.println("奔驰--停止");
	}

	@Override
	public void alarm() {
		System.out.println("奔驰--喇叭响");
	}

	@Override
	public void engineBoom() {
		System.out.println("奔驰--引擎声音");
	}

}
