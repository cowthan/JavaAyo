package com.cowthan.object_management;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public class Test {

	public static class Boolean{
		
		public static final Boolean True = new Boolean(true);
		public static final Boolean False = new Boolean(false);
		
		public static Boolean valueOf(boolean b){
			return new Boolean(b); //b ? True : False;
		}

		private boolean v;
		
		private Boolean(boolean b){
			v = b;
			
////Empty系列：内部其实都对应一个private static final的实现类，无法插入数据，因为都是immutalble
//List<String> list = Collections.EMPTY_LIST;
//Set<String> set = Collections.EMPTY_SET;
//Map<String, String> map = Collections.EMPTY_MAP;
//
//list = Collections.emptyList();
//set = Collections.emptySet();
//map = Collections.emptyMap();
//Enumeration<String> enumeration = Collections.emptyEnumeration();
//Collections.emptyIterator();
//Collections.emptyListIterator();
//Collections.emptyNavigableMap();
//Collections.emptyNavigableSet();
//Collections.emptySortedMap();
//Collections.emptySortedSet();
//
////unmodifiable系列：不可变集合
//Collection<String> c = new LinkedList<String>();
//Collection<String> c1 = Collections.unmodifiableCollection(c);
//list = Collections.unmodifiableList(List);
//
//map = Collections.unmodifiableMap(Map);
//NavigableMap<String, String> nmap = Collections.unmodifiableNavigableMap(NavigableMap<K, V>);
//SortedMap<String, String> smap = Collections.unmodifiableSortedMap(SortedMap<K, V>);
//
//set = Collections.unmodifiableSet(Set<String>);
//SortedSet<String> sset = Collections.unmodifiableSortedSet(SortedSet<T>);
//NavigableSet<String> nset = Collections.unmodifiableNavigableSet(NavigableSet<T>);
//
////synchronized系列
//c1 = Collections.synchronizedCollection(c);
//List<String> list = Collections.synchronizedList(list);
//
//map = Collections.synchronizedMap(Map);
//SortedMap<String, String> smap = Collections.synchronizedSortedMap(SortedMap<K, V>);
//NavigableMap<String, String> nmap = Collections.synchronizedNavigableMap(NavigableMap<K, V>);
//
//set = Collections.synchronizedSet(Set<T>);
//SortedSet<String> sset = Collections.synchronizedSortedSet(SortedSet);
//NavigableSet<String> nset = Collections.synchronizedNavigableSet(NavigableSet<T>);
//
////singleton系列
//Set<T> set = Collections.singleton(T t);
//List<T> list = Collections.singletonList(T t);
//Map<K, V> map = Collections.singletonMap(key, value);
//
////checked系列
//Collections.checkedList(list, type);
		}
		
	}
	
}
