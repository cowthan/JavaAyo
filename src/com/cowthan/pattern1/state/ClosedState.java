package com.cowthan.pattern1.state;

public class ClosedState extends LiftState {

	@Override
	public void open() {
		super.context.setLiftState(Context.openningState);
		context.open();
	}

	@Override
	public void close() {
		System.out.println("关门");

	}

	@Override
	public void run() {
		super.context.setLiftState(Context.runningState);
		context.run();
	}

	@Override
	public void stop() {
		super.context.setLiftState(Context.stoppedState);
		context.stop();

	}

}
