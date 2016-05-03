package com.cowthan.pattern1.state.cowthan;

public class RunningState extends LiftState {

	@Override
	public void open() {
		System.out.println("Running：无法open");

	}

	@Override
	public void close() {
		System.out.println("Closed: 门肯定是关着的");

	}

	@Override
	public void run() {
		//System.out.println("运行");

	}

	@Override
	public void stop() {
		System.out.println("动作：停止");
		super.context.setLiftState(Context.stoppedState);

	}

}
