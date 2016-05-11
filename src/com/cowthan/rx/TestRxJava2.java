package com.cowthan.rx;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Lesson 1：样板代码的封装：just和Action
 * @author Administrator
 *
 */
public class TestRxJava2 {

	//一个可被观察的对象，承载着一个组消息，等待发送
	/*
	 这个应该是相当于lesson 1里的
	 public void call(Subscriber<? super String> sub) {
					sub.onNext("Hello, world!");
					sub.onCompleted();
				}
	改成：
	public void call(Subscriber<? super List<String>> sub) {
			try{
			for(){
				sub.onNext("Hello, world!");
			}
			sub.onCompleted();
			}catch(e){
				sub.onError(e);
			}
	}
	 */
	Observable<String> myObservable =
		    Observable.just("Hello, world!", "Fuck world!");

	// /一个订阅者，将和一个observerable绑定
	Action1<String> onNextAction = new Action1<String>() {
	    @Override
	    public void call(String s) {
	    	//这里面对消息的处理，如果出现异常，会跑到onErrorAction
	        System.out.println(s);
	    }
	};
	
	Action1<Throwable> onErrorAction = new Action1<Throwable>() {
	    @Override
	    public void call(Throwable s) {
	    	System.out.println("onErrorAction");
	        s.printStackTrace();
	    }
	};
	
	Action0 onCompleteAction = new Action0() {
		
		@Override
		public void call() {
			System.out.println("onCompleteAction： 结束了！");
		}
	};

	private void test() {
		myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
	}

	public static void main(String[] args) {

		new TestRxJava2().test();
	}

}
