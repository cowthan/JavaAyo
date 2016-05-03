package com.cowthan.pattern1.template;

public class Hummer_H2 extends AbstractHummer{

	@Override
	public void start() {
		System.out.println("悍马H2--发动");
	}

	@Override
	public void stop() {
		System.out.println("悍马H2--停止");
	}

	@Override
	public void alarm() {
		System.out.println("悍马H2--喇叭响");
	}

	@Override
	public void engineBoom() {
		System.out.println("悍马H2--引擎声音");
	}
	///=========钩子=============//
	//private boolean alarmFlag = true;
	//public void setAlarm(boolean flag){ this.alarmFlag = flag; }
	//===悍马2没有喇叭，所以不需要上面两个东西
	@Override
	public boolean isAlarm() {
		return false;
	}
	//===========================//
}
