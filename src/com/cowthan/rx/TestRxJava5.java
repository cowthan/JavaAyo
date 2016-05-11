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
 * Lesson 5：flatMap操作符
 * 
 * 你可以看到，之前构建一个observable时，使用：
 * Observable.just("Hello, world!", "Fuck world!", "Fuck the whole world!")
 * 或者
 * Observable.from(list)
 * 
 * 但是，对于Observable.just("keyword").map().subscribe();
 * ——如果map里是对keyword的查询操作，返回来一个列表
 * 即map应该是：
 * .map(new Func1<String, List<String>>() {
		        @Override
		        public List<String> call(String s) {
		        	对数据查询，返回了一个列表
		        	return list;
		        }
	    	})
 	.subscribe(new Action1<List<String>>() {

				@Override
				public void call(Bean arg0) {
					---处理列表，不优雅
					---怎么才能不在这里处理列表呢
				}
	    	});
	    	
 * 
 * 
 * 这么理解flatMap吧，它能避免Observable和subscribe的嵌套，使代码优美，至于是怎么做到避免嵌套的呢，
 * 其实很简单，flatMap能接受任意类型的数据源输入，然后使用just或者from返回一个新的observable
 * 所以：
 * ——map操作符解决了已有数据源的订阅消息类型转换（通过对每个消息进行处理转换）
 * ——flatMap操作符解决了一个新数据源的转换（通过对新数据源的just或from操作）
 * 
 * 当然，你完全可以在subscribe里处理一个List或数组，直接处理或者嵌套Observable和subscribe，但不推荐这样做
 * ——直接处理可能是业务所需，如需要从整体上处理和分析list，而不关心他的每一个元素
 * ——但是如果需要关心的是每一个元素，则推荐flatMap
 * ——所以一切还是业务逻辑说了算，不要迷信技术
 * 
 * @author Administrator
 *
 */
public class TestRxJava5 {
	
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
		    		return urls;
		        }
	    	})
	    	.flatMap(new Func1<List<String>, Observable<String>>() { //------------输入List，返回Observable<String>

				@Override
				public Observable<String> call(List<String> arg0) {
					return Observable.from(arg0);
				}
			})
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
