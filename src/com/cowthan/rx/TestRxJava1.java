package com.cowthan.rx;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Lesson 1：最原始的RxJava使用方式
 * @author Administrator
 *
 */
public class TestRxJava1 {

	//一个可被观察的对象，承载着一个任务
	Observable<String> myObservable = Observable
			.create(new Observable.OnSubscribe<String>() {
				@Override
				public void call(Subscriber<? super String> sub) {
					System.out.println("发布者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
					sub.onNext("Hello, world!");
					sub.onCompleted();
				}
			});

	// /一个订阅者，将和一个observerable绑定
	Subscriber<String> mySubscriber = new Subscriber<String>() {
		@Override
		public void onNext(String s) {
			System.out.println("订阅者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
			
			System.out.println(s);
		}

		@Override
		public void onCompleted() {
			System.out.println("订阅者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
			
		}

		@Override
		public void onError(Throwable e) {
		}
	};

	private void test() {
		///下面两个都是在当前线程发布和订阅，基本没意义
		//myObservable.subscribe(mySubscriber);
		///----主线程
		//myObservable.subscribeOn(Schedulers.immediate()).subscribe(mySubscriber);
		
		//选择线程：
		/*
		 * subscribeOn() : 指定发布者从哪个线程发布消息
		 * observeOn()：     指定订阅者从哪个线程处理消息
		 * 
		 * 参数都是Scheduler，有几个内置的Scheduler：
		 * 
		 *（1）Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler
		 *（2）Schedulers.newThread(): 总是启用新线程，并在新线程执行操作，实际上没什么实践意义
		 *（3）Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程
		 *（4）Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU
		 *（5）Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行
		 * 
		 */
//		myObservable.subscribeOn(Schedulers.immediate()).observeOn(Schedulers.immediate()).subscribe(mySubscriber);
//		myObservable.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.immediate()).subscribe(mySubscriber);
//		myObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(mySubscriber);
		myObservable.subscribeOn(Schedulers.computation()).observeOn(Schedulers.immediate()).subscribe(mySubscriber);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new TestRxJava1().test();
	}

}
