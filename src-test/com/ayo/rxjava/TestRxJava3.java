package com.ayo.rxjava;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Lesson 3：简单的写法，和lamda表达式
 * 
 * just和from
 * 
 * 如果你在Android平台上（因此不能使用Java 8），那么我严重推荐使用retrolambda，它将极大的减少代码的冗余度。
 * @author Administrator
 *
 */
public class TestRxJava3 {
	
	public static void main(String[] args) {
		///更简化的写法：注意，just的参数如果是List或者数组，Action.call的参数也就得是list或数组，而在subscriber上处理列表或数组是不优雅的
		Observable.just("Hello, world!")
	    .subscribe(new Action1<String>() {
	        @Override
	        public void call(String s) {
	              System.out.println(s);
	        }
	    });
		
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.baidu.com/");
		urls.add("http://www.google.com/");
		urls.add("http://www.github.com/");
		Observable.from(urls)
		    .subscribe(new Action1<String>() {
		        @Override
		        public void call(String s) {
		              System.out.println(s);
		        }
		    });
		
		///更更简化的写法：lamda
//		Observable.just("Hello, world!")
//	    .subscribe(s -> System.out.println(s));
	}
	
}
