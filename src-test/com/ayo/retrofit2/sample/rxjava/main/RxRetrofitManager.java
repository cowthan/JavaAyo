package com.ayo.retrofit2.sample.rxjava.main;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * 就我看吧，sample包下这个例子封装的并不好，没啥意思，所以我自己尝试一下有没有什么高大上的套路
 * 但他这个HttpResult还是可取的，也只能这样了
 * 
 * 1 发起请求，返回Observable<顶层response对象--HttpResult<业务Bean>>
 * 2 统一处理原始Observable，即map一下，返回Observable<业务Bean>，这里就有意思了，HttpResult可能是code非0的请求
 *  注意，这里说的code非0，代表的业务逻辑的失败，但不是http请求的失败（如404,500，超时）
 * 3 没思路了
 * 
 * MovieService api = RxRetrofitManager.getDefault().getService(MovieService.class);
 * Observable<TopResponse<List<Subject>>> obs = api.getTopMovie(0, 10);
 * 
 * obs.map
 *
 */
public class RxRetrofitManager {
	
	public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    //构造方法私有
    private RxRetrofitManager() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }
    
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final RxRetrofitManager INSTANCE = new RxRetrofitManager();
    }

    //获取单例
    public static RxRetrofitManager getDefault(){
        return SingletonHolder.INSTANCE;
    }
	
	public <T> T getService(Class<T> klass){
		T service = retrofit.create(klass);
		return service;
	}
	
	
	//public 
	
	/**
	 * 下面这个方法的封装，本来想考虑code非0的情况，但是不好啊
	 * @param obs
	 * @return
	 */
	public <T> Observable<T> func(Observable<TopResponse<T>> obs){
		return obs.map(new Func1<TopResponse<T>, T>() {

			@Override
			public T call(TopResponse<T> arg0) {
				return arg0.body();
			}
		});
	}
	
	
}
