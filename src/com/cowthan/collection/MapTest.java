package com.cowthan.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class MapTest {

	public static void main(String[] args) {
		
		//打印环境变量
		for(Map.Entry<String, String> e: System.getenv().entrySet()){
			System.out.println(e.getKey() + "==>" + e.getValue());
		}
		
		System.out.println("-----------------------");
		
		//打印系统变量，好像都是java相关的
		//System.setProperty(key, value)可设置系统变量
		Properties properties = System.getProperties();  
		Iterator it =  properties.entrySet().iterator();  
		while(it.hasNext())  
		{  
		    Entry entry = (Entry)it.next();  
		    System.out.print(entry.getKey()+"=");  
		    System.out.println(entry.getValue());  
		}  
		
	}
	
}
