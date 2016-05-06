package com.cowthan.collection;

import org.ayo.lang.JsonUtilsUseFast;

public class Dict<K, V> {

	public static class Entry<K, V>{
		public K k;
		public V v;
		
		public Entry(){
			
		}
		
		public Entry(K kk, V vv){
			k = kk;
			v = vv;
		}
	}
	
	private Entry<K, V>[] pairs;  //键值对作为一个object[2]存储，所有键值对就是个
	private int index;
	
	public Dict(int length){
		pairs = new Entry[length];
	}
	
	public void put(K key, V value){
		if(index >= pairs.length){
			throw new ArrayIndexOutOfBoundsException();
		}
		pairs[index++] = new Entry(key, value);
	}
	
	public V get(K key){
		for(int i = 0; i < index; i++){
			if(key.equals(pairs[i].k)){
				return pairs[i].v;
			}
		}
		return null;
	}
	
	public int size(){
		return index;
	}
	
	@Override
	public String toString() {
		return JsonUtilsUseFast.toJson(pairs, true);
	}
	
	
	public static void main(String[] args) {
		
		Dict<String, String> map = new Dict<String, String>(10);
		map.put("1", "一");
		map.put("2", "贰");
		map.put("3", "叁");
		map.put("4", "肆");
		map.put("5", "伍");
		System.out.println(map.size());
		System.out.println(map.toString());
		System.out.println(map.get("4"));
		
	}
}
