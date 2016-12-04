package com.cowthan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import android.webkit.URLUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cowthan.concurrent.T;

public class Test {
	public static void main(String[] args) {
		System.out.println(new Date());
		
		
//		List<String> t = JSON.parseObject("[\"aaa\"]", new TypeReference<List<String>>(){});
//		System.out.println(t.size() + "");
		
		String url = "http://www.baidu.com?key={key}";
		String key = "哈哈";
		String wholeUrl = "";
		try {
			wholeUrl = url.replace("{key}", URLEncoder.encode(key, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(wholeUrl);
		
	}
}








