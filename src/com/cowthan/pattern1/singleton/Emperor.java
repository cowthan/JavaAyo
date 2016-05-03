package com.cowthan.pattern1.singleton;

/**
 * 皇帝类，只有一个实例，合理
 *
 */
public class Emperor {
	
	//====饿汉式：最简单
	private static final Emperor emperor = new Emperor();
	public static Emperor getInstance(){
		return emperor;
	}
	
	private Emperor(){
		
	}
	
	public void say(){
		System.out.println("我是皇帝");
	}
	
}
