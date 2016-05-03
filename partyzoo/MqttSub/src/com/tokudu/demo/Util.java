package com.tokudu.demo;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by guojun on 2015/8/2.
 * 通用工具类
 */
public class Util {

    public static String getImei() {
    	
    	try {
    		String imei = ((TelephonyManager) App.app.getSystemService(Context.TELEPHONY_SERVICE))
    				.getDeviceId();
    		if(imei == null){
    			imei = RandomUtils.getRandomLetters(8);
    		}
    		return imei;
		} catch (Exception e) {
			e.printStackTrace();
			return UUID.randomUUID().toString();
		}
    }
}
