package com.cowthan.qiniu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ayo.io.Files;
import org.ayo.log.JLog;
import org.ayo.qiniu.QiniuBucket;
import org.ayo.qiniu.QiniuSpace;

import com.qiniu.storage.model.FileInfo;


public class TestQiniu4 {
	

	///dddood3058@126.com（fedcba4321）---ccc1024---里面放的全是露点美女和gif
	
	
	public static void main(String[] args) {
		
		String ak = "S0JrnEupE4aHTB-OzVLfbkdhyFsw8OTLKsy_cs_1";
		String sk = "TNgvvMVjU5Hu74QBnaLuSTIok07C2aXc6e9inrN0";
		String token = "S0JrnEupE4aHTB-OzVLfbkdhyFsw8OTLKsy_cs_1:Rnx46_p41PPpK204dG0SQbWa7-4=:eyJzY29wZSI6Imd1b2p1bnVzdGIiLCJkZWFkbGluZSI6MTc2MTk5NjY4MX0=";

		String bucket = "guojunustb";
		String domain = "http://7xo1vz.com1.z0.glb.clouddn.com/";
		
		QiniuSpace space = QiniuSpace.space(ak, sk);
		
		//列出所有bucket
		String[] buckets = space.getBuckets();
		JLog.info("所有bucket：", buckets);
		
		//列出指定空间的所有文件，按上传时间排序
		for(String bkt: buckets){
			if(!bkt.equals(bucket)) continue;  ////只访问一个bucket
			System.out.println("------" + bkt);
			QiniuBucket bucket1 = space.bucket(bkt, domain);
			List<FileInfo> files = bucket1.getFileList(bucket, "");
			Collections.sort(files, new Comparator<FileInfo>() {
				@Override
				public int compare(FileInfo o1, FileInfo o2) {
					return new Long(o1.putTime).compareTo(new Long(o2.putTime));
				}
				
			});
			
			StringBuilder s = new  StringBuilder("<html><body>");
			
			System.out.println("图片数：" + files.size());
			int count = 0;
			for(FileInfo file: files){
				String url = bucket1.getUrl(file.key);
				JLog.info(url);
				//s.append("<img src='{ss}' /><br/>\n".replace("{ss}", url));
				downblaod(url, file.key + ".png");
				
				count++;
				System.out.println("下载完一个：" + count);
			}
			s.append("</body></html>");
//			try {
//				Files.steam.output(s.toString(), new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\aa.html")));
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("图片数：" + files.size());
		}
		
		//JLog.info("空间" + bucket + "下的所有文件：", files);
		
		//获取文件的url
		
		
		//获取token，上传文件要用
		//String newToken = bucket1.refreshToken();
		//JLog.info("token刷新了", newToken);
		
		QiniuBucket bucket1 = space.bucket(bucket, domain);
		//上传文件
//		try {
//			File data = new File("J:\\aaa.jpg");
//			String key = UUID.randomUUID().toString();
//			QNUploadRespModel respUpload = bucket1.upload(data, key, token);
//			JLog.info("上传图片", respUpload);
//		} catch (Exception e) {
//			System.out.println("2 上传文件 Error：\n" + e.getMessage());
//			e.printStackTrace();
//		}
//		
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
	
	
	 public static void downblaod(String urll, String filename) {  
         
	        URL url = null;  
	        InputStream is = null;  
	        FileOutputStream fos = null;  
	        try {  
	            //构建图片的url地址  
	            url = new URL(urll);  
	            //开启连接  
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	            //设置超时的时间，5000毫秒即5秒  
	            conn.setConnectTimeout(5000);  
	            //设置获取图片的方式为GET  
	            conn.setRequestMethod("GET");  
	            //响应码为200，则访问成功  
	            if (conn.getResponseCode() == 200) {  
	                //获取连接的输入流，这个输入流就是图片的输入流  
	                is = conn.getInputStream();  
	                //构建一个file对象用于存储图片
	               String path = "./" + filename;
	                File file = new File(path);  
	                fos = new FileOutputStream(file);  
	                int len = 0;  
	                byte[] buffer = new byte[1024];  
	                //将输入流写入到我们定义好的文件中  
	                while ((len = is.read(buffer)) != -1) {  
	                    fos.write(buffer, 0, len);  
	                }  
	                //将缓冲刷入文件  
	                fos.flush();  
	                //告诉handler，图片已经下载成功  
	            }  
	        } catch (Exception e) {  
	            //告诉handler，图片已经下载失败  
	            e.printStackTrace();  
	        } finally {  
	            //在最后，将各种流关闭  
	            try {  
	                if (is != null) {  
	                    is.close();  
	                }  
	                if (fos != null) {  
	                    fos.close();  
	                }  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  

}
