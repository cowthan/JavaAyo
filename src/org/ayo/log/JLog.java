package org.ayo.log;


import org.ayo.lang.JsonUtilsUseFast;


public class JLog {

	public static void info(Object s){
		if(s == null){
			System.out.println("null对象");
		}else{
			System.out.println(JsonUtilsUseFast.toJson(s));
		}
	}
	
	public static void info(Object s, boolean pretty){
		if(s == null){
			System.out.println("null对象");
		}else{
			System.out.println(JsonUtilsUseFast.toJson(s, pretty));
		}
	}
	
	public static void info(Object...objs){
		if(objs != null && objs.length > 0){
			System.out.print(JsonUtilsUseFast.toJson(objs));
		}else{
			System.out.println("==null==");
		}
		System.out.println();
	}
	
}
