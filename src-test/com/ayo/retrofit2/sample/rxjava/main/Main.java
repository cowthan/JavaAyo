package com.ayo.retrofit2.sample.rxjava.main;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ayo.retrofit2.sample.rxjava.entity.HttpResult;
import com.ayo.retrofit2.sample.rxjava.entity.Subject;
import com.ayo.retrofit2.sample.rxjava.http.ApiException;
import com.ayo.retrofit2.sample.rxjava.http.HttpMethods;
import com.ayo.retrofit2.sample.rxjava.http.MovieService;
import com.ayo.retrofit2.sample.rxjava.subscribers.SubscriberOnNextListener;
import com.cowthan.codeworld.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Main {
	
	
	
	public static void main(String[] args) {
		
		//这一段可以封装
		String BASE_URL = "https://api.douban.com/v2/movie/";
		int DEFAULT_TIMEOUT = 5;
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        
        
        ///异步方式：返回Call
//       Call<HttpResult<List<Subject>>> call = movieService.getTopMovie2(0, 10);
//        call.enqueue(new Callback<HttpResult<List<Subject>>>(){
//
//    		@Override
//    		public void onResponse(Call<HttpResult<List<Subject>>> call, Response<HttpResult<List<Subject>>> response) {
//    			//System.out.println("异步--" + JsonUtilsUseFast.toJson(response.body()));
//    		}
//
//    		@Override
//    		public void onFailure(Call<HttpResult<List<Subject>>> call, Throwable t) {
//    			
//    		}
//        	
//        });
        
        ///同步方式：直接返回Response
//        Call<HttpResult<List<Subject>>> call = movieService.getTopMovie2(0, 10);
//        try {
//        	HttpResult<List<Subject>> resp = call.execute().body();
//            System.out.println("同步--" + JsonUtilsUseFast.toJson(resp));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        
		
        //这一段怎么封装
        Observable<HttpResult<List<Subject>>> observable = movieService.getTopMovie(0, 10);
        observable.map(new Func1<HttpResult<List<Subject>>, List<Subject>>(){

            @Override
            public List<Subject> call(HttpResult<List<Subject>> httpResult) {
                if (httpResult.getCount() == 0) {
                	//System.out.println("wwwwwwwwwwwwwwwwww");
                    throw new ApiException(100);
                }
                return httpResult.getSubjects();
            }
        })    
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

	
	public static void main1(String[] args) {
		
		final SubscriberOnNextListener<List<Subject>> getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                System.out.println(subjects);
            }
        };
	        
        HttpMethods.getInstance().getTopMovie(new Subscriber<List<Subject>>() {
			
			@Override
			public void onNext(List<Subject> arg0) {
				System.out.println(arg0);
			}
			
			@Override
			public void onError(Throwable arg0) {
				arg0.printStackTrace();
			}
			
			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}
		}, 0, 10);
		
	}

}
