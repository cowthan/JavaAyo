package com.cowthan.pattern1.factory.method.simple;

/**
 * 场景类，女娲
 * @author qiaoliang
 *
 */
public class Director_NvWa {
	public static void main(String[] args) {
		
		//===造白人
		IHuman wh = HumanFactory.createHuman(WhiteHuman.class);
		wh.getColor();
		wh.say();
		
		//===造黑人
		IHuman bh = HumanFactory.createHuman(BlackHuman.class);
		bh.getColor();
		bh.say();
		
		//===造黄人
		IHuman yh = HumanFactory.createHuman(YellowHuman.class);
		yh.getColor();
		yh.say();
	}
}
