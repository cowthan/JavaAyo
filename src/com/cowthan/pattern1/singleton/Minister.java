package com.cowthan.pattern1.singleton;
/**
 * 臣子类
 * @author qiaoliang
 *
 */
public class Minister {
	public static void main(String[] args) {
		//对于臣子来说，不论哪天见到皇上，皇上都是同一个人
		for(int day = 0; day < 3; day++){
			Emperor emp = Emperor.getInstance();
			emp.say();
		}
	}
}
