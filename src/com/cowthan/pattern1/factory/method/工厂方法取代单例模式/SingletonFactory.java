package com.cowthan.pattern1.factory.method.工厂方法取代单例模式;

import java.lang.reflect.Constructor;

public class SingletonFactory {
	private static Singleton singleton;
	//===只实例化一次，使用暴力反射
	static{
		try {
			Class cls = Class.forName(Singleton.class.getName());
			Constructor cons = cls.getDeclaredConstructor();
			cons.setAccessible(true);
			singleton = (Singleton) cons.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Singleton getSingleton(){
		return singleton;
	}
	
	/**
	 * 扩展：一个项目可以有一个单例构造器，负责生成所有单例对象，只需要传入类型，
	 * 但是需要事先知道有几个单例类型
	 */
}
