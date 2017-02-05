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

public class TestQiniu3 {

	///cowthan@163.com    涵涵
	
	
	public static void main(String[] args) {
		
		String ak = "uNaQ_NGIZurU3OMxikyGpk-t4v8tIbP8ct5VQs_f";
		String sk = "FbtNKEzCGbIFccnX14M9iKwQsGcX9YZUyiJylh-9";
		String token = "uNaQ_NGIZurU3OMxikyGpk-t4v8tIbP8ct5VQs_f:AM6XGfArmAlRekKqbPk-teMuBoU=:eyJzY29wZSI6ImNvd3RoYW4xMTAzIiwiZGVhZGxpbmUiOjE3NjIwMjA4NDB9";

		String bucket = "cowthan0925";
		String domain = "http://7xn16k.com1.z0.glb.clouddn.com/";
		
		QiniuSpace space = QiniuSpace.space(ak, sk);
		
		//列出所有bucket
		String[] buckets = space.getBuckets();
		JLog.info("所有bucket：", buckets);
		
		//列出指定空间的所有文件，按上传时间排序
		for(String bkt: buckets){
			if(!bkt.equals(bucket)) continue;
			System.out.println("------" + bkt);
			QiniuBucket bucket1 = space.bucket(bkt, domain);
			List<FileInfo> files = bucket1.getFileList(bkt, "");
			Collections.sort(files, new Comparator<FileInfo>() {
				@Override
				public int compare(FileInfo o1, FileInfo o2) {
					return new Long(o1.putTime).compareTo(new Long(o2.putTime));
				}
				
			});
			
			for(FileInfo file: files){
				String url = bucket1.getUrl(file.key);
				JLog.info(url);
			}
		}
		
		//JLog.info("空间" + bucket + "下的所有文件：", files);
		
		//获取文件的url
		
		
		//获取token，上传文件要用
		//String newToken = bucket1.refreshToken();
		//JLog.info("token刷新了", newToken);
		
		QiniuBucket bucket1 = space.bucket(bucket, domain);
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
		//bucket1.fetch("http://f1.topit.me/1/81/88/112786790183188811o.jpg", bucket, "keykeykeyddd");
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
