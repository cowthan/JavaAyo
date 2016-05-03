package com.cowthan.pattern1.mediator;

public class Purchase extends AbstractColleague{

	public Purchase(AbstractMediator mediator) {
		super(mediator);
		// TODO Auto-generated constructor stub
	}
	
	//==依赖方法
	public void buyComputor(int number){
		this.mediator.execute("purchase.buy", number);
	}
	
	//==自发行为
	public void refuseBuyComputor()
	{
		System.out.println("采购部：不再采购电脑了");
	}

}
