package com.cowthan.pattern1.memento;
/**
 * 备忘录管理员
 *
 */
public class MementoMgmr {
	private Memento mem;
	public Memento getMemento(){return mem;}
	public void setMemento(Memento men){this.mem = mem;}
}
