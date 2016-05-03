package com.cowthan.pattern1.decorator;

public class Client {
	public static void main(String[] args) {
		IPerson person = new Son();  //此对象会被装饰
		
		//==装饰1：
		System.out.println("--------------");
		IPerson dec1 = new SpeakEngDecorator(person);
		dec1.speak();
		
		//==装饰2：
		System.out.println("--------------");
		IPerson dec2 = new SpeakWoCaoDecorator(person);
		dec2.speak();
		
		//==装饰组合
		System.out.println("--------------");
		IPerson dec3 = new SpeakWoCaoDecorator(dec1);
		dec3.speak();
		
	}
}
