package com.cowthan.pattern1.builder;

import java.util.ArrayList;

/**
 * 抽象悍马模型
 * @author qiaoliang
 *
 */
public abstract class ICar {
	public abstract void start();
	public abstract void stop();
	public abstract void alarm();
	public abstract void engineBoom();
	
	//为了让客户能够设置各个动作的执行顺序
	private ArrayList<String> sequence = new ArrayList<String>();
	final public void setSequence(ArrayList<String> sequence){
		this.sequence = sequence;
	}
	
	public void run(){
		for(String s: sequence){
			if(s.equals("start")) this.start();
			else if(s.equals("engine boom")) this.engineBoom();
			else if(s.equals("alarm")) this.alarm();
			else if(s.equals("stop")) this.stop();
		}
	}
}
