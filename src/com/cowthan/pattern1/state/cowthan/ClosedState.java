package com.cowthan.pattern1.state.cowthan;

public class ClosedState extends LiftState {

	@Override
	public void open() {
		System.out.println("动作：开门");
		super.context.setLiftState(Context.openningState);
	}

	@Override
	public void close() {
		//System.out.println("关门");

	}

	@Override
	public void run() {
		System.out.println("动作：运行");
		super.context.setLiftState(Context.runningState);
	}

	@Override
	public void stop() {
		System.out.println("动作：停止");
		super.context.setLiftState(Context.stoppedState);

	}

}
