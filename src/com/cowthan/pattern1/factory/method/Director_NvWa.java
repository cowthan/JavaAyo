package com.cowthan.pattern1.factory.method;

/**
 * 场景类，女娲
 * @author qiaoliang
 *
 */
public class Director_NvWa {
	public static void main(String[] args) {
		AbstractHumanFactory factory = new HumanFactory();
		
		//===造白人
		IHuman wh = factory.createHuman(WhiteHuman.class);
		wh.getColor();
		wh.say();
		
		//===造黑人
		IHuman bh = factory.createHuman(BlackHuman.class);
		bh.getColor();
		bh.say();
		
		//===造黄人
		IHuman yh = factory.createHuman(YellowHuman.class);
		yh.getColor();
		yh.say();
	}
}
