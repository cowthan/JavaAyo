package com.cowthan.pattern1.factory.method;

public class HumanFactory extends AbstractHumanFactory{

	@Override
	public <T extends IHuman> T createHuman(Class<T> clazz) {
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
