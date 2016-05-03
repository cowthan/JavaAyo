package com.cowthan.pattern1.proxy.powerforce;

public class Director_Client {
	public static void main(String[] args) {
		
		System.out.println("===使用强制代理时===");
		IGamePlayer player = new GamePlayer("张三").getProxy();
		player.login("zhangsan", "111");
		player.killMonsters();
		player.upgrade();
		
		System.out.println("===不使用强制代理时：直接使用对象===");
		IGamePlayer player2 = new GamePlayer("张三");
		player2.login("zhangsan", "111");
		player2.killMonsters();
		player2.upgrade();
		
		//注意这里有个问题：别的代理不是你的代理，所以无法替你完成工作，但他还是跟你收钱了！
		System.out.println("===不使用强制代理时：使用别的代理===");
		IGamePlayer player3 = new GamePlayerProxy(player2);
		player3.login("zhangsan", "111");
		player3.killMonsters();
		player3.upgrade();
	}
}
