package com.qiniu.test;

import com.qiniu.util.Auth;

/**
 * 管理七牛的token
 * @author cowthan
 *
 */
public class QNTokenMgmr {

	public static QNTokenMgmr newInstance(String ak, String sk, String bucket){
		return new QNTokenMgmr(ak, sk, bucket);
	}
	
	private QNTokenMgmr(String ak, String sk, String bucket){
		this.ak = ak;
		this.sk = sk;
		this.bucket = bucket;
		auth = Auth.create(ak, sk);
	}
	
	private Auth auth;
	private String ak;
	private String sk;
	private String bucket;

	// 
	/**
	 * 简单上传，使用默认策略
	 * token的过期时间是10年
	 * @return
	 */
	public String refreshToken(){
	    //return auth.uploadToken(Config.ConfigQiniu.BUCKET);
		try {
			return auth.uploadToken(bucket, null, 10 * 365 * 24 * 60 * 60, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

//	// 覆盖上传
//	public String getUpToken1(){
//		
//	    return auth.uploadToken(bucket, "key");
//	}
//
//	// 设置指定上传策略
//	public String getUpToken2(){
//	    return auth.uploadToken(bucket, null, 3600, new StringMap()
//	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
//	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
//	}
//
//	// 设置预处理、去除非限定的策略字段
//	public String getUpToken3(){
//	    return auth.uploadToken(bucket, null, 3600, new StringMap()
//	            .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
//	            .putNotEmpty("persistentPipeline", ""), true);
//	}

	/**
	* 生成上传token
	*
	* @param bucket  空间名
	* @param key     key，可为 null
	* @param expires 有效时长，单位秒。默认3600s
	* @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
	*        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
	* @param strict  是否去除非限定的策略字段，默认true
	* @return 生成的上传token
	*/
//	public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict){
//		return "";
//	}
	
}
