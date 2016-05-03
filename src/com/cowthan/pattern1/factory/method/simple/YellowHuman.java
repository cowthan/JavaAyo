package com.cowthan.pattern1.factory.method.simple;

public class YellowHuman implements IHuman{

	@Override
	public void say() {
		System.out.println("我是黄种人");
	}

	@Override
	public void getColor() {
		System.out.println("黄色皮肤");
	}

}
