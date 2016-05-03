package com.cowthan.pattern1.factory.method.multi_factory;

/**
 * 场景类，女娲
 * @author qiaoliang
 *
 */
public class Director_NvWa {
	public static void main(String[] args) {
		
		//===造白人
		AbstractHumanFactory factory = new WhiteHumanFactory();
		IHuman wh = factory.createHuman();
		wh.getColor();
		wh.say();
		
		//===造黑人
		AbstractHumanFactory factory2 = new BlackHumanFactory();
		IHuman bh = factory2.createHuman();
		bh.getColor();
		bh.say();
		
		//===造黄人
		AbstractHumanFactory factory3 = new YellowHumanFactory();
		IHuman yh = factory3.createHuman();
		yh.getColor();
		yh.say();
	}
}
