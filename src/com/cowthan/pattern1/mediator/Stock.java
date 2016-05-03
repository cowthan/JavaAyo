package com.cowthan.pattern1.mediator;

public class Stock extends AbstractColleague{

	public Stock(AbstractMediator mediator) {
		super(mediator);
		// TODO Auto-generated constructor stub
	}
	
	//==库存电脑数量
	private static int NUMBER = 100;
	
	//==继续采购
	public void increase(int number){
		NUMBER += number;
		System.out.println("库存：还有" + NUMBER + "件");
	}
	
	//==降低库存
	public void decrease(int number){
		NUMBER -= number;
		System.out.println("库存：还有" + NUMBER + "件");
	}
	
	//==获取库存数量
	public int getStockNumber(){
		return NUMBER;
	}
	
	//==清仓，存货压力太大，告诉采购不要采了，告诉销售不要卖了
	public void clearStock(){
		System.out.println("库存：清理" + NUMBER + "件");
		this.mediator.execute("stock.clear");
	}
	
}
