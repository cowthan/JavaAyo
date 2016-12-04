package com.cowthan.enum2;

/**
 * 特定于常量的方法实现
 *
 */
public enum Operation {

	PLUS("+") {
		@Override
		double apply(double x, double y) {
			return x + y;
		}
	}, 
	MINUS("-") {
		@Override
		double apply(double x, double y) {
			return x - y;
		}
	}, 
	TIMES("*") {
		@Override
		double apply(double x, double y) {
			return x * y;
		}
	}, 
	DIVIDE("/") {
		@Override
		double apply(double x, double y) {
			return x / y;
		}
	};
	
	private final String symbol;
	
	private Operation(String symbol) {
		this.symbol = symbol;
	}
	
	abstract double apply(double x, double y);
	
	@Override
	public String toString() {
		return symbol;
	}
}