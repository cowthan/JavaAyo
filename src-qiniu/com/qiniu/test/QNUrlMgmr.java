package com.qiniu.test;

import org.ayo.lang.Ayo;
import org.ayo.lang.Strings;

/**
 * 管理七牛的图片访问：url的拼接
 * @author cowthan
 *
 */
public class QNUrlMgmr {
	
	public static QNUrlMgmr newInstance(String domain){
		return new QNUrlMgmr(domain);
	}
	
	private QNUrlMgmr(String domain){
		if(Ayo.isEmpty(domain) || !domain.startsWith("http") || !domain.endsWith("/")){
			throw new IllegalArgumentException("域名" + domain + "不符合规范，必须以http开头，以/结尾");
		}
		this.domain = domain;
	}
	
	private String domain;
	
	/**
	 * 原始图片的url
	 * @param key
	 * @return
	 */
	public String getUrl(String key)
	{
		return domain + key;
	}
	
	public String getThumbUrl(String key, int width, int height)
	{
		String url = "";
		if(key.contains("jpg_") || key.contains(".jpg") || key.contains(".png")){
			url = domain + key + "?imageView2/1/w/{w}/h/{h}";
		}else if(key.contains("gif_") || key.contains(".gif")){
			url = domain + key + "?imageMogr2/format/jpg/thumbnail/{w}x{h}";
		}else if(key.contains("vedio_") || key.contains(".vedio")){
//			vframe/<Format>
//		      /offset/<Second>
//		      /w/<Width>
//		      /h/<Height>
//		      /rotate/<Degree>
			url = domain + key + "?vframe/jpg/offset/2/w/{w}/h/{h}/rotate/auto";
			System.out.println("视频--" + url);
		}else{
			url = domain + key + "?imageView2/1/w/{w}/h/{h}";
		}
		
		return url.replace("{w}", width+"").replace("{h}", height+"");
	}
	
	
	///私有空间的图片
//	public String getOriginalUrlSafe(String key)
//	{
//		return getSafeUrl(domain + key);
//	}
//	
//	public String getThumbUrlSafe(String key)
//	{
//		return getSafeUrl(domain + key + "?imageView2/1/w/140/h/140");
//	}
//	
//	public String getSafeUrl(String url){
//		//DownloadUrl = 'http://developer.qiniu.com/resource/flower.jpg?e=1451491200'
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		long expireTime = 0;
//		try {
//			expireTime = sdf.parse("2025-01-01").getTime();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		String url2 = url + "?e=" + expireTime;
//		
//		try {
//			String hmac = HMACSHA1.getSignature(url2, Config.qiniu.sk_image);
//			
//			String base64Signature = UrlSafeBase64.encodeToString(hmac);
//			
//			String token = base64Signature + ":" + Config.qiniu.ak_image;
//			
//			url2 = url2 + "&token=" + token;
//			return url2;
//		} catch (InvalidKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return "";
//	
//	}
//	
}
