package com.cowthan.enum3;

/**
 
 策略枚举
 
 策略模式的枚举版，普通的策略模式是一个接口，具体实现类来提供具体算法
 
 
 需求：算每天的工资
 每天上班标准是8小时，工资固定
 超出部分是加班，工资1.5倍
 平时超出8小时算加班
 周末全算加班
 
 你分析一下这个需求：
 1 上班的这一天可能是工作日，周末，也有可能是其他节假日，而且加班工资可能也不一样
 2 所以先定义个工作日类型，也可以看做薪资类型，主要处理加班工资的问题，这里是PayType
 
 */
public enum PayRollDay {
	
	MONDAY(PayType.WEEKDAY),
	TUESDAY(PayType.WEEKDAY),
	WEDNESDAY(PayType.WEEKDAY),
	THURSDAY(PayType.WEEKDAY),
	FRIDAY(PayType.WEEKDAY),
	SATURDAY(PayType.WEEKEND),
	SUBDAY(PayType.WEEKEND);
	
	private final PayType payType;
	PayRollDay(PayType payType){
		this.payType = payType;
	}
	
	double pay(double hoursWorked, double payRate){
		return payType.pay(hoursWorked, payRate);
	}
	
	
	
	
	private enum PayType{
		WEEKDAY{
			double overtimePay(double hours, double payRate){
				return hours <= HOURS_PER_SHIFT ? 0 : (hours - HOURS_PER_SHIFT) * payRate / 2;
			}
		},
		
		WEEKEND{
			double overtimePay(double hours, double payRate){
				return (hours) * payRate / 2;
			}
		};
		
		
		private static final int HOURS_PER_SHIFT = 8;
		abstract double overtimePay(double hrs, double parRate);
		
		double pay(double hoursWorked, double payRate){
			double basePay = hoursWorked * payRate;
			return basePay + overtimePay(hoursWorked, payRate);
		}
		
	}
	
	
}
