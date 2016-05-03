package com.cowthan.pattern1.factory.method.multi_factory;

public class WhiteHumanFactory extends AbstractHumanFactory{

	@Override
	public IHuman createHuman() {
		WhiteHuman h = new WhiteHuman();
		//-----------------------//
		//---进行一些初始化------//		
		//--------------------- //
		return h; 
	}


}
