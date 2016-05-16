package com.cowthan.memory;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	
	static class OOMObject{
		
	}
	
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		int count = 0;
		while(true){
			count++;
			System.out.println(count);
			list.add(new OOMObject());
		}
	}

}
