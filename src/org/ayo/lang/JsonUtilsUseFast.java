package org.ayo.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public final class JsonUtilsUseFast {

	private JsonUtilsUseFast() {
	}

	/**
	 * 根据JsonArray字符串和cls，返回ArrayList<T>
	 * 
	 * @param jsonArrayString
	 * @param cls
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<T> getBeanList(String jsonArrayString, Class<T> cls) {

		List<T> beanList = new ArrayList<T>();
		beanList = JSON.parseArray(jsonArrayString, cls);
		return beanList;
	}

	public static <T> T getBean(String jsonString, Class<T> cls) {
		T t = null;
		t = JSON.parseObject(jsonString, cls);
		return t;
	}
	
	public static String toJson(Object bean, boolean pretty){
		if(bean == null){
			return "{}";
		}
		if(pretty) return JSON.toJSONString(bean, SerializerFeature.PrettyFormat);
		else return JSON.toJSONString(bean); 
	}
	
	public static String toJson(Object bean){
		return toJson(bean, false);
	}
	
}
