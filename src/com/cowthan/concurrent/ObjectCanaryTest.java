package com.cowthan.concurrent;

import org.ayo.concurrent.ObjectCanary;

public class ObjectCanaryTest {
	
	public static void main(String[] args) {
		class A{}
        class B extends A{}
        ObjectCanary<A> o = new ObjectCanary<>(new B());
        o.set(new B());
		
	}
	

}
