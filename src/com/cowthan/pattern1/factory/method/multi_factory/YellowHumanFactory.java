package com.cowthan.pattern1.factory.method.multi_factory;

public class YellowHumanFactory extends AbstractHumanFactory{

	@Override
	public IHuman createHuman() {
		YellowHuman h = new YellowHuman();
		//-----------------------//
		//---进行一些初始化------//		
		//--------------------- //
		return h; 
	}


}
