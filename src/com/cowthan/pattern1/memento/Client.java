package com.cowthan.pattern1.memento;

public class Client {
	public static void main(String[] args) {
		Boy b = new Boy();
		
		//==初始化状态
		 b.setState("心情还行");
		 System.out.println("初始状态：" + b.getState());
		 
		 //==备份
		 System.out.println("备份。。。");
		 Memento mem = b.createMemento();
		 
		 //==更改状态
		 System.out.print("状态更改:");
		 b.changeState();
		 System.out.print(b.getState() + "\n");
		 
		 //==恢复还原
		 System.out.println("还原。。。");
		 b.restoreMemetnto(mem);
		 System.out.println("现在状态：" + b.getState());
	}
	
}
