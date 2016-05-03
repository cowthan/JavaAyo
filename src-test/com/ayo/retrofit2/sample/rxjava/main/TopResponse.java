package com.ayo.retrofit2.sample.rxjava.main;

public interface TopResponse<T> {

	boolean ok();
	boolean msg();
	T body();
}
