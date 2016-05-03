package com.cowthan.pattern1.factory.method.multi_factory;

public class BlackHumanFactory extends AbstractHumanFactory{

	@Override
	public IHuman createHuman() {
		BlackHuman h = new BlackHuman();
		//-----------------------//
		//---进行一些初始化------//		
		//--------------------- //
		return h; 
	}


}
