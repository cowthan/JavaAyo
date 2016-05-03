package com.cowthan.pattern1.command;
/**
 * 命令类
 * @author qiaoliang
 *
 */
public abstract class CMD {
	protected RG rg = new RG();
	protected PG pg = new PG();
	protected CG cg = new CG();
	
	/**
	 * 本方法不与客户直接交流，客户有什么需求，就定义个什么对象，
	 * 传给Invoker，由Invoker调用本方法
	 * ——记住：客户只与Invoker交流
	 */
	public abstract void execute();
}
