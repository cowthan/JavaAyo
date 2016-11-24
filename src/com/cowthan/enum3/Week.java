package com.cowthan.enum3;

import java.util.HashMap;
import java.util.Map;

/**
Enum.valueOf(s)方法，根据常量名的字符串直接得到枚举常量
如果toString被覆盖（默认返回常量名），则你需要下面这段代码来进行字符串和枚举常量的映射：
private static final Map<String, Week> stringToEnum = new HashMap<>();
static{
	for(Week w: Week.values()){
		stringToEnum.put(w.toString(), w);
	}
}
Week monday2 = Week.stringToEnum.get("周一");

 *
 */
public enum Week {
	
	Monday;
	
	
	public String toString() {
		if(this == Week.Monday){
			return "周一";
		}
		return "";
	}
	
	private static final Map<String, Week> stringToEnum  = new HashMap<>();
	static{
		for(Week w: Week.values()){
			stringToEnum.put(w.toString(), w);
		}
	}
	
	public static void main(String[] args) {
		
		Week monday = Week.valueOf("Monday");
		System.out.println(monday);
		
		Week monday2 = Week.stringToEnum.get("周一");
		System.out.println(monday2);
		
	}

}
