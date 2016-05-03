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
public class TestMeididiBuyer {
	
	public static void main(String[] args) {
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("orderNum", "113333");
		
		//要推送的别名，就是融云id
		String[] alias = new String[]{"132535355795642b8038df85"};
		
		JPushUtils.pushToAndroid(JPushConfig.meididi_buyer.ak,
				JPushConfig.meididi_buyer.sk, 
				"不是来自服务器的", 
				"不是来自服务器的，自己测试的", 
				extras, 
				alias, 
				null, 
				false);
		
	}
	
}
