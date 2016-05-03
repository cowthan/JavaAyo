package com.cowthan.pattern1.memento;
/**
 * 需要被记录状态的类
 * @author qiaoliang
 *
 */
public class Boy {
	private String state = "";  //这是需要保存的状态
	
	public void changeState(){
		this.state = "心情很悲伤";
	}
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	//========备忘录相关==========//
	//==备份
	public Memento createMemento(){
		return new Memento(this.state);
	}
	//==恢复
	public void restoreMemetnto(Memento mem){
		this.setState(mem.getState());
	}
}
