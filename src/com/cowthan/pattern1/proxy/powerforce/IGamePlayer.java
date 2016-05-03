package com.cowthan.pattern1.proxy.powerforce;

public interface IGamePlayer {
	void login(String name, String pass);
	void killMonsters();
	void upgrade();
	
	IGamePlayer getProxy();
}
