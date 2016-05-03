package com.qiniu.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ayo.lang.Ayo;
import org.ayo.lang.JsonUtilsUseFast;
import org.ayo.lang.Strings;

import com.qiniu.storage.model.FileInfo;

public class TestQiniu {
	
	public static class ConfigQiniu{
		
		public static String ak = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0";//Config.qiniu.ak_image;
		public static String sk = "JoHeFWYKrEfCR9JgLvr6TLr8WLjX6-6Y7qQVVrLJ";//Config.qiniu.sk_image;
		public static String bucket = "ccc1024";//Config.qiniu.bucket_image;
		public static String domain = "http://7xnroh.com1.z0.glb.clouddn.com/";
		public static String token = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0:DKdXIuGaFLTtOHoSk0DJzTfhdaI=:eyJzY29wZSI6ImNjYzEwMjQiLCJkZWFkbGluZSI6MTc2MjAyMTI4NX0=";
		

	}
	
	public static void main11(String[] args) {
		
		
		///乔良：dddood3058@126.com（fedcba4321）---ccc1024---里面放的全是露点美女和gif
		String ak = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0";//Config.qiniu.ak_image;
		String sk = "JoHeFWYKrEfCR9JgLvr6TLr8WLjX6-6Y7qQVVrLJ";//Config.qiniu.sk_image;
		String bucket = "ccc1024";//Config.qiniu.bucket_image;
		String domain = "http://7xnroh.com1.z0.glb.clouddn.com/";
		String token = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0:DKdXIuGaFLTtOHoSk0DJzTfhdaI=:eyJzY29wZSI6ImNjYzEwMjQiLCJkZWFkbGluZSI6MTc2MjAyMTI4NX0=";
		
		
		
		QNTokenMgmr tokenMgmr = QNTokenMgmr.newInstance(ak, sk, bucket);
		token = tokenMgmr.refreshToken();
		if(Ayo.isEmpty(token)){
			throw new RuntimeException("获取七牛token失败");
		}else{
			System.out.println("1 获取token：\n" + token);
		}
		
		///2 上传文件
//		try {
//			File data = new File("J:\\aaa.jpg");
//			String key = UUID.randomUUID().toString();
//			QNUploadRespModel respUpload = QNUploadMgmr.getDefault().upload(data, key, token);
//			System.out.println("2 上传文件：\n" + JsonUtils.toJson(respUpload));
//			System.out.println("刚上传图片的url地址是：" + QNUrlMgmr.getDefault().getOriginalUrl(respUpload.key));
//		} catch (Exception e) {
//			System.out.println("2 上传文件 Error：\n" + e.getMessage());
//			e.printStackTrace();
//		}
	}
	
	
	public static void main(String[] args) {
		
		///3 获取所有空间
		QNBucketMgmr bucketMgmr = QNBucketMgmr.newInstance(ConfigQiniu.ak, ConfigQiniu.sk);
		String[] buckets = bucketMgmr.getBuckets();
		if(buckets == null){
			System.out.println("3 获取空间列表：失败");
		}else{
			System.out.println("3 获取空间列表：\n" + JsonUtilsUseFast.toJson(buckets));
		}
		
		///4 获取指定空间下的文件列表
		List<FileInfo> files = bucketMgmr.getFileList("cowthan0331", "");
		Collections.sort(files, new Comparator<FileInfo>() {

			@Override
			public int compare(FileInfo o1, FileInfo o2) {
				return new Long(o1.putTime).compareTo(new Long(o2.putTime));
			}
			
		});
		System.out.println("4 获取文件列表：\n" + JsonUtilsUseFast.toJson(files));
		///转成url，好显示
		if(files != null){
			List<String> images = new ArrayList<String>();
			for(FileInfo f: files){
				String url = QNUrlMgmr.newInstance(ConfigQiniu.domain).getUrl(f.key);
				images.add(url);
			}
			System.out.println("文件列表：(" + images.size() +")\n" + JsonUtilsUseFast.toJson(images));
		}
		
		///5 抓取网络图片
		///QNBucketMgmr.getDefault().fetch("http://f1.topit.me/1/81/88/112786790183188811o.jpg", "cowthan0331", "asdsdsa");
	}
	

}
