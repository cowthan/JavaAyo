package com.ayo.retrofit2.sample.rxjava.http;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import com.ayo.retrofit2.sample.rxjava.entity.HttpResult;
import com.ayo.retrofit2.sample.rxjava.entity.Subject;
import com.ayo.retrofit2.sample.rxjava.main.MyTopResponse;

/**
 * Created by liukun on 16/3/9.
 */
public interface MovieService {

//    @GET("top250")
//    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("top250")
//    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("top250")
//    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
    
    @GET("top250")
    Call<HttpResult<List<Subject>>> getTopMovie2(@Query("start") int start, @Query("count") int count);
    
    @GET("top250")
    Observable<MyTopResponse<List<Subject>>> getTopMovie3(@Query("start") int start, @Query("count") int count);
}
