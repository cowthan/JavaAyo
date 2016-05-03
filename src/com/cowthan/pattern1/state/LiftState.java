package com.cowthan.pattern1.state;
/**
 * 表示电梯状态，封装了在当前状态下能够进行的动作，
 * 并维护了一个外部环境Context，Context知道维护着
 * 当前的电梯状态
 * @author qiaoliang
 *
 */
public abstract class LiftState {
	protected Context context;
	
	public void setContext(Context ctx){
		this.context = ctx;
	}
	
	public abstract void open();
	public abstract void close();
	public abstract void run();
	public abstract void stop();
}
