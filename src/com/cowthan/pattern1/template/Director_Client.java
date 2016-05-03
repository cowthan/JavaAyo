package com.cowthan.pattern1.template;

public class Director_Client {
	public static void main(String[] args) {
		System.out.println("----------");
		AbstractHummer h1 = new Hummer_H1();
		h1.run();
		
		System.out.println("----------");
		Hummer_H1 hh1 = (Hummer_H1)h1;
		hh1.setAlarm(false);
		hh1.run();
		
		System.out.println("----------");
		AbstractHummer h2 = new Hummer_H2();
		h2.run();
	}
}
