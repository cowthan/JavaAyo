package com.cowthan.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class C1 {

	public static void main(String[] args) {
		
		//创建
		Collection<Integer> collection = new ArrayList<>(10);  //参数是capacity，不是size
		Collection<Integer> collection2 = new ArrayList<>();  //参数默认值就是10
		Collection<Integer> collection3 = new ArrayList<>(collection);		 //类型是java.util.ArrayList
		Collection<Integer> collection4 = Arrays.asList(1,2,3,4,5);
		Collection<Integer> collection5 = Arrays.<Integer>asList(1,2,3,4,5);
		Collection<Integer> collection6 = Arrays.asList(new Integer[]{5,6,7,8});  //类型是：java.util.Arrays$ArrayList，所以一般是将这个返回作为new ArrayList的参数
		System.out.println(collection5.getClass().getName());   //
		
		//添加：add和addAll，add就不多说了
		Collections.addAll(collection, 11,12,13,14);
		Collections.addAll(collection, new Integer[]{3,4,5,6});
		collection.addAll(collection4);
		
		//
		List<Integer> arraylist = new ArrayList<Integer>(10);
		List<Integer> linkedlist = new LinkedList<Integer>();

		Set<Integer> hashset = new HashSet<Integer>();
		Set<Integer> treeset = new TreeSet<Integer>();
		Set<Integer> linkedhashset = new LinkedHashSet<Integer>();

		Map<String, String> hashmap = new HashMap<String, String>();
		Map<String, String> treemap = new TreeMap<String, String>();
		Map<String, String> linkedhashmap = new LinkedHashMap<String, String>();
		
		//遍历
		Iterator<Integer> it = arraylist.iterator();
		while(it.hasNext()){
			Integer i = it.next();
			System.out.println(i);
		}
		
		ListIterator<Integer> it2 = arraylist.listIterator();
		while(it2.hasNext()){
			Integer i = it2.next();
			int prevIndex = it2.previousIndex();
			Integer prev = it2.previous();
			int nextIndex = it2.nextIndex();
			Integer next = it2.next();
			it2.hasPrevious();
			it2.hasNext();
			it2.remove();
			System.out.println(i);
		}
		
		for(Integer i: arraylist){
			System.out.println(i);
		}
		
		for(int i = 0; i < arraylist.size(); i++){
			System.out.println(i);
		}
		
		///
	}
	
}
