package org.ayo.qiniu;

import java.util.ArrayList;
import java.util.List;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class QiniuSpace {
	
	private Auth auth;
	private String ak;
	private String sk;
	private BucketManager bucketManager;
	
	private QiniuSpace(String ak, String sk){
		this.ak = ak;
		this.sk = sk;
		auth = Auth.create(ak, sk);
		bucketManager = new BucketManager(auth);
	}
	
	public static QiniuSpace space(String ak, String sk){
		return new QiniuSpace(ak, sk);
	}
	
	public QiniuBucket bucket(String bucket, String domain){
		return new QiniuBucket(bucket, domain, auth, bucketManager);
	}
	
	/**
	 * 获取空间名列表
	 * @return
	 */
	public String[] getBuckets(){
		try {
			String[] buckets = bucketManager.buckets();
			return buckets;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	

}
