package com.cowthan.pattern1.factory.method.simple;

public class HumanFactory{

	public static <T extends IHuman> T createHuman(Class<T> clazz) {
		IHuman human = null;
		try{
			human = clazz.newInstance();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("生成错误");
		}
		return (T)human;
	}

}
