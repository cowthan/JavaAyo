package com.cowthan.json;

import java.util.HashMap;
import java.util.Map;

import org.ayo.lang.JsonUtilsUseFast;
import org.ayo.lang.Lang;

import com.cowthan.json.data.Bean;


public class JsonTest {
	public static void main(String[] args) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("haha", "111");
		map.put("fsadf", "22");
		map.put("hahsdfsa", "11sfs1");
		map.put("sdfsaf", "1dsafsd11");
		
		String s = JsonUtilsUseFast.toJson(new Bean());
		System.out.println(s);
		
		//{"birth":1462270181596,"id":10,"isGirl":false,"name":"王二"}
		String json = "{\"birth\":1462270181596,\"id\":10,\"isGirl\":false,\"name\":\"王二\"}"; //,\"girlFriend\":\"\"
		Bean b = JsonUtilsUseFast.getBean(json, Bean.class);
		System.out.println(b.birth);
		
		json = "{\"2千\": 400, \"5千\": 570,\"1万\": 760,\"2万\": 1140}";
		Map<String, String> map2 = JsonUtilsUseFast.getBean(json, HashMap.class);
		Lang.log("", map2);
		
		json = "{}";
		map2 = JsonUtilsUseFast.getBean(json, HashMap.class);
		Lang.log("", map2);
		
		///异常
		json = "";
		map2 = JsonUtilsUseFast.getBean(json, HashMap.class);
		Lang.log("", map2);
		
	}
	///{"createTime":"13232332","orderNum":"1122334","orderStatus":"1","portraiUri":"http://img3.imgtn.bdimg.com/it/u=3841520159,2387139296&fm=21&gp=0.jpg","tags":"","userId":"12313241234132"}

	public static class PreOrderMessage{

		public String orderNum = "1122334";
		public String orderStatus = "1";
		public String userId = "12313241234132";
		public String portraiUri = "http://img3.imgtn.bdimg.com/it/u=3841520159,2387139296&fm=21&gp=0.jpg";
		public String tags = "";
		public String createTime = "13232332";

		public PreOrderMessage(){
			
		}
	}

}
