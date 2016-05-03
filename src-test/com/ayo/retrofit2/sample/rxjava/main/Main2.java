package com.ayo.retrofit2.sample.rxjava.main;

import java.util.List;

import com.ayo.retrofit2.sample.rxjava.entity.Subject;
import com.ayo.retrofit2.sample.rxjava.http.MovieService;
import com.cowthan.codeworld.Utils;

public class Main2 {
	
	public static void main(String[] args) {
		
		RxRetrofitManager.getDefault().getService(MovieService.class).getTopMovie3(0, 10)
			.map(new TopResponseAnaliser<MyTopResponse<List<Subject>>, List<Subject>>())
			 .subscribeOn(Schedulers.io())  //在IO线程发布，加上这个，就发不起请求，必须sleep几秒，保证http返回之前，进程没死
	        //.unsubscribeOn(Schedulers.io())
	        .observeOn(Schedulers.computation())  ///这一段可以封装，返回个Bean的Observable，并且指定线程
	        .subscribe(new Subscriber<List<Subject>>() {
				
				@Override
				public void onNext(List<Subject> arg0) {
					System.out.println("Rxjava--" + arg0);
				}
				
				@Override
				public void onError(Throwable arg0) {
					//404：HTTP 404 Not Foundretrofit2.adapter.rxjava.HttpException: HTTP 404 Not Found
					//500: 没法测
					//超时：没法测
					System.out.println("出错了--" + arg0.getMessage());
					arg0.printStackTrace();
				}
				
				@Override
				public void onCompleted() {
					System.out.println("onCompleted");
				}
			});
		
		Utils.sleep(6000);
	}

}
