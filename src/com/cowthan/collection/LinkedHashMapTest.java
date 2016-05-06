package com.cowthan.collection;

import java.util.LinkedHashMap;

import org.ayo.log.JLog;

public class LinkedHashMapTest {

	public static void main(String[] args) {
		LinkedHashMap<String, String> map = Mock.linkedHashMap_LRU();
		JLog.info(map);
		map.get("1");  //1被用过了，就会移动最后
		JLog.info(map);   
	}
	
}
