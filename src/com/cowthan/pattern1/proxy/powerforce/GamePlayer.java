package com.cowthan.pattern1.proxy.powerforce;


public class GamePlayer implements IGamePlayer{

	private String name = null;
	
	private GamePlayer(){}
	public GamePlayer(String name){ this.name = name; }
	
	@Override
	public void login(String name, String pass) {
		if(this.proxy == null) {System.out.println("去找我的代理吧，调用getProxy()");return;}
		System.out.println(name + "--登陆");
	}

	@Override
	public void killMonsters() {
		if(this.proxy == null) {System.out.println("去找我的代理吧，调用getProxy()");return;}
		System.out.println(name + "--杀怪");
	}

	@Override
	public void upgrade() {
		if(this.proxy == null) {System.out.println("去找我的代理吧，调用getProxy()");return;}
		System.out.println(name + "--升级");
	}
	
	//====维护着自己的代理=========//
	private IGamePlayer proxy = null;
	
	@Override
	public IGamePlayer getProxy() {
		this.proxy = new GamePlayerProxy(this);
		return this.proxy;
	}

}
