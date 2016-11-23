package com.cowthan.fanxing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.security.auth.kerberos.KerberosKey;

import com.google.gson.reflect.TypeToken;

public class Test1 {

	public static void main(String[] args) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		System.out.println(map.getClass().getName());
		
		System.out.println(map.getClass().getGenericSuperclass().getTypeName());
		
		Type type = new TypeToken<Map<String, Integer>>(){}.getType();
		System.out.println(type.getTypeName());
		
		type = new TypeToken<KeyVType<HashMap<String, LinkedList<int[]>>>>(){}.getType();
		System.out.println(type.getTypeName());
		
		KeyVType<HashMap<String, LinkedList<int[]>>> mmm = new KeyVType<HashMap<String, LinkedList<int[]>>>("key") {};
		System.out.println(mmm);
		
	}
	
}

//System.out.println(new KeyVType<HashMap<String, LinkedList<int[]>>>("key") {});
//[key -> java.util.HashMap<java.lang.String, java.util.LinkedList<int[]>>,
//class java.util.HashMap,
//[class java.lang.String, java.util.LinkedList<int[]>]]


 class KeyVType<K>{
	 
	 public String key;
	 
	public KeyVType(String k){
		key = k;
	}
	
	@Override
	public String toString() {
		Type type = new TypeToken<K>(){}.getType();
		System.out.println(type.getTypeName());
		
		Type t = this.getClass().getGenericSuperclass();
		System.out.println("this.getClass().getGenericSuperclass() = " + t.getTypeName());
		System.out.println("是否泛型类型：" + (t instanceof ParameterizedType));
		
		analiseParameterized(t, "    ");
		
		return t.getTypeName();
	}
	
	private void analiseParameterized(Type t, String prefix){
		if(t instanceof ParameterizedType){
			Type[] arguments = ((ParameterizedType) t).getActualTypeArguments();
			for(Type t2: arguments){
				System.out.println(prefix + "泛型参数--" + t2.getTypeName());
				if(t2 instanceof ParameterizedType){
					analiseParameterized(t2, prefix + "    ");
				}
				
			}
		}
	}
}
