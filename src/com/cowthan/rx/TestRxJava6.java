package com.cowthan.rx;

import java.util.ArrayList;
import java.util.List;


import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Scheduler.Worker;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Lesson 6：更多操作
 * 
 * fitler：过滤
 * doOnNext：在消息发送给subscriber之前，做一些处理
 * take：取前几N条消息，其他的忽略
 * 
 * 通过下面这个例子也可以看出，如果没有flatMap，代码会有多难读
 *
 */
public class TestRxJava6 {
	
	public static void main(String[] args) {
		Observable.just("keyword")
	    	.map(new Func1<String, List<String>>() { //------------输入String，返回List
		        @Override
		        public List<String> call(String s) {
		        	List<String> urls = new ArrayList<String>();
		        	///模拟数据库操作
		    		urls.add("http://www.baidu.com/");
		    		urls.add("http://www.google.com/");
		    		urls.add("http://www.github.com/");
		    		urls.add("fuck, this will be ignored");
		    		urls.add("http://www.github.com/");
		    		urls.add("http://www.github.com/");
		    		urls.add("http://www.github.com/");
		    		return urls;
		        }
	    	})
	    	.flatMap(new Func1<List<String>, Observable<String>>() { //------------输入List，返回Observable<String>

				@Override
				public Observable<String> call(List<String> arg0) {
					return Observable.from(arg0);
				}
			})
			.filter(new Func1<String, Boolean>() {//------------过滤，符合条件的才会被发布

				@Override
				public Boolean call(String arg0) {
					System.out.println("开始过滤");
					return arg0 != null && !arg0.contains("fuck");
				}
			})
			.take(4)  //------------只取4个
	    	.subscribe(new Action1<String>() {
	
				@Override
				public void call(String arg0) {
					System.out.println("subscriber: " + arg0);
				}
	    	});
		
	}
	
	public static class Bean{
		public String name;
	}
	
}
