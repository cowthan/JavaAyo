package com.cowthan.pattern1.state;

public class StoppedState extends LiftState {

	@Override
	public void open() {
		super.context.setLiftState(Context.openningState);
		context.open();

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		super.context.setLiftState(Context.runningState);
		context.run();

	}

	@Override
	public void stop() {
		System.out.println("停止");

	}

}
