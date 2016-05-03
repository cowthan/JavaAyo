package com.cowthan.pattern1.mediator;

import java.util.Random;

public class Sale extends AbstractColleague{

	public Sale(AbstractMediator mediator) {
		super(mediator);
	}

	
	//==销售电脑
	public void sellComputor(int number){
		this.mediator.execute("sale.sell", number);
		System.out.println("销售：" + number + "台");
	}
	
	//==打折销售电脑
	public void offSale(){
		this.mediator.execute("sale.offsell");
	}
	
	//==反馈销售状况，0表示一个卖不出去，100表示有一个卖一个
	public int getSaleStatus(){
		Random rand = new Random(System.currentTimeMillis());
		int saleStatus = rand.nextInt(100);
		System.out.println("销售：销售" + saleStatus + "件");
		return saleStatus;
	}
}
