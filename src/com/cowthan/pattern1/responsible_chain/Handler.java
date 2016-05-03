package com.cowthan.pattern1.responsible_chain;

public abstract class Handler {
	
	private Handler nextHandler = null;
	
	public Handler(){};
	public void setNext(Handler handler){this.nextHandler = handler;}
	
	protected abstract Response handle(Request req);
	protected abstract int getLevel();
	
	public final Response handleMsg(Request req){
		if(req.getLevel() == this.getLevel()){
			return this.handle(req);
		}else{
			if(this.nextHandler != null){
				return nextHandler.handleMsg(req);
			}else{
				System.out.println(this.getClass().getSimpleName() + ": 没有人做后续处理了，此请求没人处理");
				return null;
			}
		}
	}
	
}
