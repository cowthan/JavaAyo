package com.cowthan.codeworld;

public class WBGeneratorForQiniuOld {
	
	/**
	 * 这个包是为了处理七牛的之前保存的图片，没办法，分成了jpg，gif，视频三个主题
	 * 
	 * 为什么呢，因为不这么分的话，得翻100多页jpg，才能翻到gif
	 * 
	 * 实际上这3个主题是一个主题，即timeline_color，以后jpg，gif，视频会混合发，不会出现上述情况
	 * 
	 */
	public static void main(String[] args) {
		
		////---------------------美女图片：合法-----------
		String ak = "uNaQ_NGIZurU3OMxikyGpk-t4v8tIbP8ct5VQs_f";
		String sk = "FbtNKEzCGbIFccnX14M9iKwQsGcX9YZUyiJylh-9";
		String bucket = "cowthan0331";
		String host = "http://7xicvb.com1.z0.glb.clouddn.com/";
		String dirPath = "";
		int type = 0;

		///处理jpg
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\cl_old_jpg";
//		type = 1;  //1 只管jpg， 2 只管gif， 3 只管视频， 4 都管
//		Utils.process(ak, sk, bucket, host, dirPath, type);
//		
//		///处理gif
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\cl_old_gif";
//		type = 2;  
//		Utils.process(ak, sk, bucket, host, dirPath, type);
//		
//		///处理vedio
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\cl_old_vedio";
//		type = 3;  
//		Utils.process(ak, sk, bucket, host, dirPath, type);
//		
		
		////---------------------美女图片：你懂的-----------
//		ak = "przk-OcKylSzIbRFUeTAoSoiYKkKg4NNRQbSLFM0";
//		sk = "JoHeFWYKrEfCR9JgLvr6TLr8WLjX6-6Y7qQVVrLJ";
//		bucket = "ccc1024";
//		host = "http://7xnroh.com1.z0.glb.clouddn.com/";
//		
//		///处理jpg
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\color_old_jpg";
//		type = 1;  
//		Utils.process(ak, sk, bucket, host, dirPath, type);
//		
//		///处理gif
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\color_old_gif";
//		type = 2;  
//		Utils.process(ak, sk, bucket, host, dirPath, type);
//		
//		///处理vedio
//		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\color_old_vedio";
//		type = 3;  
//		Utils.process(ak, sk, bucket, host, dirPath, type);
		
		////---------------------涵涵-----------
		ak = "uNaQ_NGIZurU3OMxikyGpk-t4v8tIbP8ct5VQs_f";
		sk = "FbtNKEzCGbIFccnX14M9iKwQsGcX9YZUyiJylh-9";
		bucket = "cowthan0925";
		host = "http://7xn16k.com1.z0.glb.clouddn.com/";
		
		///处理jpg
		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\hh_old_jpg";
		type = 1;  
		Utils.process(ak, sk, bucket, host, dirPath, type);
		
		///处理gif
		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\hh_old_gif";
		type = 2;  
		Utils.process(ak, sk, bucket, host, dirPath, type);
		
		///处理vedio
		dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\old\\hh_old_vedio";
		type = 3;  
		Utils.process(ak, sk, bucket, host, dirPath, type);
		
	}
	
}
