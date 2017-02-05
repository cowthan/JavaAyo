package com.cowthan.jpush.meididi;

import java.util.HashMap;
import java.util.Map;

import com.cowthan.jpush.JPushConfig;
import com.cowthan.jpush.JPushUtils;

/**
 * 美滴滴用户端订单推送的测试
 * @author Administrator
 *
 */
public class TestFringe {
	
	public static void main(String[] args) {
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("orderNum", "113333");
		
		//要推送的别名，就是融云id
		String[] alias = new String[]{"special"};
		
		JPushUtils.pushToAndroid("37250f16d832c50f0361d1be",
				"31e114ab551cee492f6abf4b", 
				"不是来自服务器的", 
				"不是来自服务器的，自己测试的", 
				null, 
				null, 
				null, 
				false);
		
	}
	
}
