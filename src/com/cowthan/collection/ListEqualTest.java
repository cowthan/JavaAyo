package com.cowthan.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;

public class ListEqualTest {

	public static void main(String[] args) {
		
		List<Object> list1 = new ArrayList<>();
		List<A> list2 = new ArrayList<A>();
		
		String s = "////";
		String[] arr = s.split("/");
		System.out.println(JsonUtilsUseFast.toJson(arr));
		
		String pwd = "a！！订单";
		if(!pwd.matches("[0-9a-zA-Z_]*")){
			System.out.println("密码只能包含数字字母和下划线");
		}
		
	}
	
	class A extends Object{
		
	}
	
	public <T> List<T> upgrade(List<? extends T> list){
		List<T> res = new ArrayList<>();
		for(T t: list){
			res.add(t);
		}
		return res;
	}
	
}









