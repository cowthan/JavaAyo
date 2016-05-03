package com.cowthan.pattern1.state.cowthan;

public class Client {
	public static void main(String[] args) {
		Context ctx = new Context();
		ctx.setLiftState(Context.closedState);
		
		ctx.open();
		ctx.close();
		ctx.run();
		ctx.stop();
		ctx.run();
		ctx.open();
		ctx.stop();
		ctx.open();
	}
	
}
