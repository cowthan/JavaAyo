package com.cowthan.pattern1.proxy.ordinary;

public class GamePlayer implements IGamePlayer{

	private String name = null;
	
	private GamePlayer(){}
	public GamePlayer(String name){ this.name = name; }
	
	@Override
	public void login(String name, String pass) {
		System.out.println(name + "--登陆");
	}

	@Override
	public void killMonsters() {
		System.out.println(name + "--杀怪");
	}

	@Override
	public void upgrade() {
		System.out.println(name + "--升级");
	}

}
