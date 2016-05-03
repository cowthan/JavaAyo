package com.cowthan.json.data;

import java.util.Date;

public class Bean {
	
	public static String A = "aa";

	public String name = "王二";
	public int id = 10;
	public boolean isGirl = false;
	public Date birth = new Date();
	public Bean girlFriend = null;
	
	public String nth;
	
	public String getAddr(){
		return "北京";
	}
	
	public String getName(){
		return "张三";
	}
}
