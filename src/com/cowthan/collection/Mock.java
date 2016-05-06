package com.cowthan.collection;

import java.util.LinkedHashMap;

public class Mock {
	
	public static LinkedHashMap<String, String> linkedHashMap(){
		
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		map.put("1", "一");
		map.put("2", "贰");
		map.put("3", "叁");
		map.put("4", "肆");
		map.put("5", "伍");
		
		return map;
	}
	
public static LinkedHashMap<String, String> linkedHashMap_LRU(){
		
		LinkedHashMap<String, String> map = new LinkedHashMap<>(16, 0.75f, true);
		
		map.put("1", "一");
		map.put("2", "贰");
		map.put("3", "叁");
		map.put("4", "肆");
		map.put("5", "伍");
		
		return map;
	}

}
