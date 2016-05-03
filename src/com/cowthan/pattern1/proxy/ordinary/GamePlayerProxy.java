package com.cowthan.pattern1.proxy.ordinary;

/**
 * 代理类：
 * 1、想代理IGamePlayer，就必须实现IGamePlayer
 * 2、要代理IGamePlayer，就必须有个IGamePlayer的实现类对象作为成员
 * 3、要添加代理功能，比如收钱，最好单独开一个代理接口
 * 4、可以从构造方法直接传入代理对象，也可以传入参数，在代理类中自己new代理对象
 * 
 * 虚拟代理：
 * 这个听起来高级，其实就是对本类的实现来说，不在构造方法中new对象，而是在每个方法
 * 的实现中加上这样的代码：
 * if(player == null) player = new player(name);
 * player.xxx();
 *
 */
public class GamePlayerProxy implements IGamePlayer, IGameProxy {
	
	private IGamePlayer player;

	/**
	 * 现在选择在代理中new一个被代理的真实对象，为了配合这个决定，可以
	 * 在实际项目中，约定禁止在外部new一个真实对象
	 * ——但这个问题，虽然可以通过技术来实现，但不赞成，因为这破坏了
	 * 代码的移植性，重用性和可维护性，还是通过约定说好了就行
	 * @param name
	 */
	public GamePlayerProxy(String name){
		player = new GamePlayer(name);
	}
	
	@Override
	public void count() {
		System.out.println("收钱了");
		
	}

	@Override
	public void login(String name, String pass) {
		this.player.login(name, pass);
		
	}

	@Override
	public void killMonsters() {
		this.player.killMonsters();
		
	}

	@Override
	public void upgrade() {
		this.player.upgrade();
		count();
	}
	
	

}
