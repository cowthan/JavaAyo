package com.cowthan.pattern1.mediator;

/**
 * 抽象同事类
 *
 */
public class AbstractColleague {
	protected AbstractMediator mediator;
	
	/**
	 * 为什么让构造方法注入中介者，因为每个同事必须都认识中介者
	 * @param mediator
	 */
	public AbstractColleague(AbstractMediator mediator){
		this.mediator = mediator;
	}
}
