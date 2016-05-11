package com.cowthan.qiniu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.ayo.log.JLog;
import org.ayo.qiniu.QNUploadRespModel;
import org.ayo.qiniu.QiniuBucket;
import org.ayo.qiniu.QiniuSpace;

import com.qiniu.common.Config;
import com.qiniu.storage.model.FileInfo;

public class TestQiniu {
	
	public static class ConfigQiniu{
		
		public static String ak = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0";//Config.qiniu.ak_image;
		public static String sk = "JoHeFWYKrEfCR9JgLvr6TLr8WLjX6-6Y7qQVVrLJ";//Config.qiniu.sk_image;
		public static String bucket = "ccc1024";//Config.qiniu.bucket_image;
		public static String domain = "http://7xnroh.com1.z0.glb.clouddn.com/";
		public static String token = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0:DKdXIuGaFLTtOHoSk0DJzTfhdaI=:eyJzY29wZSI6ImNjYzEwMjQiLCJkZWFkbGluZSI6MTc2MjAyMTI4NX0=";
		

	}
	
	public static void main(String[] args) {
		
		
		///乔良：dddood3058@126.com（fedcba4321）---ccc1024---里面放的全是露点美女和gif
		String ak = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0";//Config.qiniu.ak_image;
		String sk = "JoHeFWYKrEfCR9JgLvr6TLr8WLjX6-6Y7qQVVrLJ";//Config.qiniu.sk_image;
		String bucket = "ccc1024";//Config.qiniu.bucket_image;
		String domain = "http://7xnroh.com1.z0.glb.clouddn.com/";
		String token = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0:DKdXIuGaFLTtOHoSk0DJzTfhdaI=:eyJzY29wZSI6ImNjYzEwMjQiLCJkZWFkbGluZSI6MTc2MjAyMTI4NX0=";
		
		QiniuSpace space = QiniuSpace.space(ak, sk);
		
		//列出所有bucket
		String[] buckets = space.getBuckets();
		JLog.info("所有bucket：", buckets);
		
		//列出指定空间的所有文件，按上传时间排序
		QiniuBucket bucket1 = space.bucket(bucket, domain);
		List<FileInfo> files = bucket1.getFileList(bucket, "");
		Collections.sort(files, new Comparator<FileInfo>() {
			@Override
			public int compare(FileInfo o1, FileInfo o2) {
				return new Long(o1.putTime).compareTo(new Long(o2.putTime));
			}
			
		});
		//JLog.info("空间" + bucket + "下的所有文件：", files);
		
		//获取文件的url
		FileInfo file = files.get(0);
		String url = bucket1.getUrl(file.key);
		JLog.info(url);
		
		//获取token，上传文件要用
		String newToken = bucket1.refreshToken();
		JLog.info("token刷新了", newToken);
		
		//上传文件
		try {
			File data = new File("J:\\aaa.jpg");
			String key = UUID.randomUUID().toString();
			QNUploadRespModel respUpload = bucket1.upload(data, key, token);
			JLog.info("上传图片", respUpload);
		} catch (Exception e) {
			System.out.println("2 上传文件 Error：\n" + e.getMessage());
			e.printStackTrace();
		}
		
		//抓取网页图片
		bucket1.fetch("http://f1.topit.me/1/81/88/112786790183188811o.jpg", bucket, "keykeykeyddd");
	}
	
	
	public static void maindd(String[] args) {
		
//		///转成url，好显示
//		if(files != null){
//			List<String> images = new ArrayList<String>();
//			for(FileInfo f: files){
//				String url = QNUrlMgmr.newInstance(ConfigQiniu.domain).getUrl(f.key);
//				images.add(url);
//			}
//			System.out.println("文件列表：(" + images.size() +")\n" + JsonUtilsUseFast.toJson(images));
//		}
//		
		///5 抓取网络图片
		///QNBucketMgmr.getDefault().fetch("http://f1.topit.me/1/81/88/112786790183188811o.jpg", "cowthan0331", "asdsdsa");
	}
	

}
