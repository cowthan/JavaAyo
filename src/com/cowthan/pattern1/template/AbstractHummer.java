package com.cowthan.pattern1.template;
/**
 * 抽象悍马模型
 * @author qiaoliang
 *
 */
public abstract class AbstractHummer {
	public abstract void start();
	public abstract void stop();
	public abstract void alarm();
	public abstract void engineBoom();
	
	//===为了给子类当钩子用
	public abstract boolean isAlarm();
	//=====
	
	/**
	 * 模板方法，对于一切子类都是一样的
	 * ——当然，肯定会有不一样的地方，有的子类在某些地方和其他不一样，
	 * 这时就应该用到：钩子
	 */
	public final void run(){
		this.start();
		this.engineBoom();
		if(this.isAlarm()) this.alarm();
		this.stop();
	}
}
