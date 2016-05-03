package com.cowthan.pattern1.memento;
/**
 * 备忘录
 *
 */
public class Memento {
	private String state = "";  //记录的状态
	
	public Memento(String state){
		this.state = state;
	}
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
}
