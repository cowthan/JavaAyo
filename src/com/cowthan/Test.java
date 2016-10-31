package com.cowthan;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cowthan.concurrent.T;

public class Test {
	public static void main(String[] args) {
		System.out.println(new Date());
		
		
		List<String> t = JSON.parseObject("[\"aaa\"]", new TypeReference<List<String>>(){});
		System.out.println(t.size() + "");
	}
}








