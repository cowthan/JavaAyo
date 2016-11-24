package com.cowthan.enum2;

public enum Operation_1 {

	PLUS, MINUS, TIMES, DIVIDE;
	
	public double apply(double x, double y){
		
		switch(this){
		case PLUS: return x+y;
		case MINUS: return x-y;
		case TIMES: return x*y;
		case DIVIDE: return x/y;
		}
		throw new AssertionError("unknown op: " + this);  ///没有这句,编译不会通过
	}
	
}
