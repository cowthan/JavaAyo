package com.cowthan.pattern1.factory.method.simple;

public class WhiteHuman implements IHuman{

	@Override
	public void say() {
		System.out.println("我是白种人");
	}

	@Override
	public void getColor() {
		System.out.println("白色皮肤");
	}

}
