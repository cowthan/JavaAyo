package com.cowthan.pattern1.state.cowthan;
/**
 * 状态机，管理状态
 * @author qiaoliang
 *
 */
public class Context {
	public final static ClosedState closedState = new ClosedState();
	public final static OpenningState openningState = new OpenningState();
	public final static RunningState runningState = new RunningState();
	public final static StoppedState stoppedState = new StoppedState();
	
	public LiftState currentState;
	
	public void setLiftState(LiftState closedstate) {
		this.currentState = closedstate;
		this.currentState.setContext(this);
		
	}
	public LiftState getLiftState() {
		return currentState;
	}
	
	public void open(){
		currentState.open();
	}
	public void close(){
		currentState.close();
	}
	public void run(){
		currentState.run();
	}
	public void stop(){
		currentState.stop();
	}
}
