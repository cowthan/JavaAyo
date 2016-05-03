package com.cowthan.pattern1.factory.method.工厂方法取代单例模式;

/**
 * 单例类，不允许通过new产生一个对象，一个项目中这种类可以有很多，
 * 现在需要一个类来负责生成这些类的对象，而不是每个类都实现一个
 * 单例模式，那样嫌麻烦
 * @author qiaoliang
 *
 */
public class Singleton {
	private Singleton(){}
	public void doSth(){
		System.out.println("做点什么");
	}
}
