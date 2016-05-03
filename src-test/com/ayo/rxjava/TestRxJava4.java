package com.ayo.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Lesson 4：map方法--转化，即把消息转换成另一种数据结构，可以供subscriber用
 * 
 * map是一个Operators：
 * ——Operators在消息发送者Observable和消息消费者Subscriber之间起到操纵消息的作用
 * ——RxJava拥有大量的opetators，但刚开始最好还是从一小部分开始熟悉
 * 
 * 特别注意：
 * 如果observable和subscriber不是在一个线程，则map实际上是在observable的线程执行
 * 
 * 适用于obsererable的消息类型和subscriber的消息类型不一样
 * 当然也可以直接在subscriber里转换
 * 
 * @author Administrator
 *
 */
public class TestRxJava4 {
	
	public static void main(String[] args) {
		///map的使用:注意，map可以连用
		Observable.just("Hello, world!", "Fuck world!", "Fuck the whole world!")
	    	.map(new Func1<String, Bean>() {
		        @Override
		        public Bean call(String s) {
		        	Bean b = new Bean();
		        	b.name = s + "---经过map-1处理了";
		            return b;
		        }
	    	})
	    	.map(new Func1<Bean, Bean>() {
		        @Override
		        public Bean call(Bean bb) {
		        	Bean b = new Bean();
		        	b.name = bb.name + "---经过map-2处理了";
		            return b;
		        }
	    	})
	    	.subscribe(new Action1<Bean>() {

				@Override
				public void call(Bean arg0) {
					System.out.println("subscriber: " + arg0.name);
				}
	    	});
		
	}
	
	public static class Bean{
		public String name;
	}
	
}
