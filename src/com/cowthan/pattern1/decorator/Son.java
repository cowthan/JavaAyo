package com.cowthan.pattern1.decorator;

public class Son extends Father{

	@Override
	public void speak() {
		System.out.println("Son: 说80年代的话");
	}

	@Override
	public void walk() {
		System.out.println("Son: 走80年代的路");
	}

}
