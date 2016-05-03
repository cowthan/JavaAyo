package com.cowthan.pattern1.state;
/**
 * 电梯门开启状态
 * @author qiaoliang
 *
 */
public class OpenningState extends LiftState {

	/**
	 * 不好理解，Openning这个state是通过open这个动作产生的，
	 * 所以外部想开门时，就调用OpenningState.open()，并且找到
	 * 这个具体state的方式就是先set一下context的state，context就会
	 * 找到具体的state
	 */
	@Override
	public void open() {
		System.out.println("电梯门开启...");

	}

	/**
	 * 在openning状态下关门，会：
	 * ——先set一下context中维护的状态
	 * ——此时context调用的是closedState.close()
	 * 
	 * 就这样即完成了状态切换，又执行了动作
	 */
	@Override
	public void close() {
		super.context.setLiftState(Context.closedState);
		super.context.getLiftState().close();
	}

	@Override
	public void run() {
		System.out.println("Openning状态：无法run");

	}

	@Override
	public void stop() {
		System.out.println("Openning状态：无法stop");

	}

}
