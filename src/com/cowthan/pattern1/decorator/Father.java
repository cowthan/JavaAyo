package com.cowthan.pattern1.decorator;

public class Father implements IPerson{

	@Override
	public void speak() {
		System.out.println("father: 说60年代的话");
	}

	@Override
	public void walk() {
		System.out.println("father: 走60年代的路");
	}

}
