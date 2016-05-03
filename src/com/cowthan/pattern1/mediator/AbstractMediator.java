package com.cowthan.pattern1.mediator;

public abstract class AbstractMediator {
	
	protected Purchase purchase; //采购
	protected Sale sale;         //销售
	protected Stock stock;       //库存
	
	/**
	 * 提供这三个方法，而不是让用户在构造方法中注入，是因为：
	 * 中介者可能只需要知道部分同事就行
	 */
	public void setPurchase(Purchase purchase){this.purchase = purchase;}
	public void setSale(Sale sale){this.sale = sale;}
	public void setStock(Stock stock){this.stock = stock;}
	
	/**
	 * 业务方法
	 */
	public abstract void execute(String action, Object...object);
}
