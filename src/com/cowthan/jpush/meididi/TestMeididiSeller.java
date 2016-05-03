package com.cowthan.jpush.meididi;

import java.util.HashMap;
import java.util.Map;

import com.cowthan.jpush.JPushConfig;
import com.cowthan.jpush.JPushUtils;

/**
 * 美滴滴咨询师端订单推送的测试
 * @author Administrator
 *
 */
public class TestMeididiSeller {
	
	public static void main(String[] args) {
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("orderNum", "113333");
		
		String[] alias = new String[]{"要推送的别名，就是融云id"};
		
		JPushUtils.pushToAndroid(JPushConfig.meididi_seller.ak,
				JPushConfig.meididi_seller.sk, 
				"title", 
				"content", 
				extras, 
				alias, 
				null, 
				false);
		
	}
	
}
