package com.cowthan.pattern1.proxy.ordinary;

public class Director_Client {
	public static void main(String[] args) {
		
		IGamePlayer player = new GamePlayerProxy("张三");
		player.login("zhangsan", "111");
		player.killMonsters();
		player.upgrade();
		
	}
}
