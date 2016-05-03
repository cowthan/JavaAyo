package com.cowthan.pattern1.mediator;

public class Direct_Client {
	public static void main(String[] args) {
		
		//===初始化
		AbstractMediator mediator = new Mediator();
		
		Purchase purchase = new Purchase(mediator);
		Sale sale = new Sale(mediator);
		Stock stock = new Stock(mediator);
		
		mediator.setPurchase(purchase);
		mediator.setStock(stock);
		mediator.setSale(sale);
		
		//===使用
		System.out.println("--------采购人员----------");
		purchase.buyComputor(30);
		
		System.out.println("--------销售人员----------");
		sale.sellComputor(20);
		
		System.out.println("--------库存人员----------");
		stock.clearStock();
		
	}
}
