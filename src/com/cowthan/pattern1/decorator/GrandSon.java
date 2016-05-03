package com.cowthan.pattern1.decorator;

public class GrandSon extends Son{

	@Override
	public void speak() {
		System.out.println("GrandSon: 说00年代的话");
	}

	@Override
	public void walk() {
		System.out.println("GrandSon: 走00年代的路");
	}

}
