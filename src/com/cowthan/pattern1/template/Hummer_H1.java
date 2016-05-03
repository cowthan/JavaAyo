package com.cowthan.pattern1.template;

public class Hummer_H1 extends AbstractHummer{

	@Override
	public void start() {
		System.out.println("悍马H1--发动");
	}

	@Override
	public void stop() {
		System.out.println("悍马H1--停止");
	}

	@Override
	public void alarm() {
		System.out.println("悍马H1--喇叭响");
	}

	@Override
	public void engineBoom() {
		System.out.println("悍马H1--引擎声音");
	}

	///=========钩子=============//
	private boolean alarmFlag = true;
	public void setAlarm(boolean flag){ this.alarmFlag = flag; }

	@Override
	public boolean isAlarm() {
		return alarmFlag;
	}
	//===========================//
}
