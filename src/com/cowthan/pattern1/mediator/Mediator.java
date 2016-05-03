package com.cowthan.pattern1.mediator;

public class Mediator extends AbstractMediator{

	@Override
	public void execute(String action, Object... objects) {
		//采购电脑：Purchase行为，但依赖于库存和销售
		if(action.equals("purchase.buy")){
			this.buyComputor((Integer)objects[0]);
		}
		//销售电脑：Sale行为，但依赖于库存
		else if(action.equals("sale.sell")){
			this.sellComputor((Integer)objects[0]);
		}
		//打折销售：Sale行为，但依赖于库存
		else if(action.equals("sale.offsell")){
			this.offsell();
		}
		//清仓处理：Stock行为，但依赖于销售处理
		else if(action.equals("stock.clear")){
			this.clearStock();
		}
	}

	//==库存清仓
	private void clearStock() {
		if(this.sale == null) return;
		if(this.purchase == null) return;
		
		this.sale.offSale();   //让销售卖
		this.purchase.refuseBuyComputor();//让采购别进货了
	}

	//==打折销售
	private void offsell() {
		if(this.stock == null) return;
		//sale的业务，但需要知道打折销售多少，多少就是stock的业务
		System.out.println("打折" + stock.getStockNumber() + "台");
	}

	//==卖电脑
	private void sellComputor(int number) {
		if(this.stock == null) return;
		if(this.purchase == null) return;
		
		if(this.stock.getStockNumber() < number){
			//库存不够,  让采购买
			this.purchase.buyComputor(number);
		}
		//库存也减少了
		this.stock.decrease(number);
		
	}


	//==采购电脑
	private void buyComputor(int number) {
		if(this.sale == null) return;
		if(this.stock == null) return;
		
		int saleStatus = this.sale.getSaleStatus();
		if(saleStatus > 80){
			//销售情况良好，就采购全部
			this.stock.increase(number);
		}else{
			//销售情况不好，就采购一半
			int buyNumber = number/2;
			this.stock.increase(buyNumber);
		}
		
	}

}
