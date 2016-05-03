package com.cowthan.pattern1.proxy.ordinary;

/**
 * 代理的个性：
 * ——代理的个性才是代理存在的价值
 * ——代理的个性就是代理干了真实对象不能干的事
 * 
 * 游戏代练的接口，就是收钱，这个例子不太恰当，因为基本上代理的意义是对
 * 对象功能的扩展，而不是仅仅完成了对象能做的事情之后去跟对象要钱
 * @author qiaoliang
 *
 */
public interface IGameProxy {
	void count();
}
