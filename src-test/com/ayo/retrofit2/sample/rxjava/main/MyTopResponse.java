package com.ayo.retrofit2.sample.rxjava.main;

public class MyTopResponse<T> implements TopResponse<T>{

	
	
	@Override
	public boolean ok() {
		return false;
	}

	@Override
	public boolean msg() {
		return false;
	}

	@Override
	public T body() {
		return null;
	}

}
