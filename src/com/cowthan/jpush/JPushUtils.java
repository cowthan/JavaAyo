package com.cowthan.jpush;

import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;

public class JPushUtils {
	
	/**
	 * 推送：分两种模式，静默和非静默，非静默就是会提示在通知栏，静默就是只能在代码里接收和处理，由用户代码决定是否提示给用户
	 * @param ak 
	 * @param sk 
	 * @param title  通知栏的title，静默模式下不知道这个应该是什么，但非静默模式下这个必须是可读的
	 * @param content 通知栏的内容，或者静默推送发送的json串，当然也可以不是json串
	 * @param extras 非静默模式下才用到这个，因为content是用户可读的，所以用extra传递附加数据
	 * @param alias 别名，非空则忽略极光id
	 * @param registrationIds 极光id，如果要启用这个，alias必须为null或空
	 * @param willSilence  true则是静默模式，false是非静默模式
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static void pushToAndroid(String ak, String sk, String title, String content, Map<String, String> extras, String[] alias, String[] registrationIds, boolean willSilence){
		try {
			_pushToAndroid(ak, sk, title, content, extras, alias, registrationIds, willSilence);
		} catch (APIConnectionException e) {
			System.out.println("推送结果：发送失败, APIConnectionException");
			e.printStackTrace();
		} catch (APIRequestException e) {
			System.out.println("推送结果：发送失败, APIRequestException");
			e.printStackTrace();
		}
	}
	
	
	private static void _pushToAndroid(String ak, String sk, String title, String content, Map<String, String> extras, String[] alias, String[] registrationIds, boolean willSilence) throws APIConnectionException, APIRequestException {
		
		//初始化
		JPushClient jpush = new JPushClient(sk, ak);
		
		//构造推送的字段
		//extras
		
		//开始推送
		PushResult res = null;
		String mode = "未知";
		if(isEmpty(alias) && isEmpty(registrationIds)){
			throw new RuntimeException("别名和极光id不能同时为空，测试环境不要给所有人推");
		}
		
		if(willSilence){
			if(alias == null || alias.length == 0){
				///极光id，静默模式
				mode = "静默推送，按照RegistrationID";
				res = jpush.sendAndroidMessageWithRegistrationID(title, content, registrationIds);
			}else{
				///别名，静默模式
				mode = "静默推送，按照别名";
				res = jpush.sendAndroidMessageWithAlias(title, content, alias);
			}
		}else{
			if(alias == null || alias.length == 0){
				///极光id，非静默模式
				mode = "非静默推送，按照RegistrationID";
				res = jpush.sendAndroidNotificationWithRegistrationID(title, content, extras, registrationIds);
			}else{
				///别名，非静默模式
				mode = "非静默推送，按照别名";
				//res = jpush.sendNotificationAll("hahahah");
				res = jpush.sendAndroidNotificationWithAlias(title, content, extras, alias);
			}
		}
		
		System.out.println("推送模式：" + mode);
		if (res != null) {
		    if (res.isResultOK()) {
		        System.out.println("推送结果：发送成功");
		    } else {
		        System.out.println("推送结果：发送失败");
		    }
		} else {
		    System.out.println("推送结果：无法获取返回数据");
		}
	}
	
	///推送过去直接显示在通知栏，并有声音，led灯，震动等提示
//	PushResult	sendAndroidNotificationWithAlias(String title, String alert, Map<String,String> extras, String... alias) 
//	PushResult	sendAndroidNotificationWithRegistrationID(String title, String alert, Map<String,String> extras, String... registrationID) 

	///推送过去什么都不提示，只能在代码里得到，一般msgContent会发送个json串
//	PushResult	sendAndroidMessageWithAlias(String title, String msgContent, String... alias) 
//	PushResult	sendAndroidMessageWithRegistrationID(String title, String msgContent, String... registrationID) 

	///给iOS推送
//	 PushResult	sendIosMessageWithAlias(String title, String msgContent, String... alias) 
//	 PushResult	sendIosMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
//	 PushResult	sendIosNotificationWithAlias(String alert, Map<String,String> extras, String... alias) 
//	 PushResult	sendIosNotificationWithRegistrationID(String alert, Map<String,String> extras, String... registrationID)
	
	private static <T> boolean isEmpty(T[] arr){
		return arr == null || arr.length == 0;
	}
}
