package com.cowthan.pattern1.factory.method.simple;

public class BlackHuman implements IHuman{

	@Override
	public void say() {
		System.out.println("我是黑种人");
	}

	@Override
	public void getColor() {
		System.out.println("黑色皮肤");
	}

}
