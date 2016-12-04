

1 基本使用

```java
package com.cowthan.enum2;

public enum Planet {
	
	MERCURY(3.302e+23, 2.439e6),  //水星
	VENUS(4.869e+24, 6.052e6),  //金星
	EARTH(5.975e+24, 6.378e6),  //
	MARS(6.419e+23, 3.393e6),   //火星
	JUPITER(1.899e+27, 7.149e7), //木星
	SATURN(5.685e+26, 6.027e7), //土星
	URANUS(8.683e+25,  2.556e7), //天王星
	NEPTUNE(1.024e+26, 2.477e7); //海王星
	
	/*
	 
	 枚举天生不可变，因此所有域都应该是final的
	  
	 */
	private final double mass;   //  kg
	private final double radius; //  meter
	private final double surfaceGravity; //    m/s^2
	public boolean fuck = false;
	
	private static final double G = 6.67300E-11;
	
	Planet(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
		surfaceGravity = G * mass / (radius * radius);
	}
	
	public double mass() {return mass;}
	public double radius() { return radius;}
	public double surfaceGravity() { return surfaceGravity;}
	public double surfaceWeight(double mass){
		return mass * surfaceGravity;
	}
	
	
	public static void main(String[] args) {
		double earthWeight = 100; //kg
		double mass = earthWeight / Planet.EARTH.surfaceGravity();
		for(Planet p: Planet.values()){
			System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
		}
	}
}

```


2 抽象方法

```java
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
```


3 enum操作


 Operation.valus() 
 返回所有Operation类型的枚举常量
 


4 emun和String

