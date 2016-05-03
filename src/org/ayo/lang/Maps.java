package org.ayo.lang;


import java.util.HashMap;

public class Maps {

	public static <K, V> HashMap<K, V> newHashMap(Object...args){
		HashMap<K, V> m = new HashMap<K, V>();
		if(args == null || args.length == 0){

		}else{
			for(int i = 0; i < args.length; i+=2){
				Object k = args[i];
				Object v = args[i+1];
				m.put((K)k, (V)v);
			}
		}
		return m;
	}
}
