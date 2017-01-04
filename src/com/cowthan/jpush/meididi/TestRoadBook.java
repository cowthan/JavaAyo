package com.cowthan.jpush.meididi;

import java.util.HashMap;
import java.util.Map;

import com.cowthan.jpush.JPushConfig;
import com.cowthan.jpush.JPushUtils;
import com.cowthan.jpush.sample.PushExample;

/**
 * 美滴滴咨询师端订单推送的测试
 * @author Administrator
 *
 */
public class TestRoadBook {
	
	public static void main(String[] args) {
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("orderNum", "113333");
		
		String[] alias = new String[]{"breaker"};
		
		//PushExample.testSendPush_fromJSON();
		
		JPushUtils.pushToAndroid(JPushConfig.roadbook.ak,
				JPushConfig.roadbook.sk, 
				"title", 
				"content", 
				extras, 
				null, 
				new String[]{"120c83f7602bb23b72a"}, 
				false);
		
	}
	
}
