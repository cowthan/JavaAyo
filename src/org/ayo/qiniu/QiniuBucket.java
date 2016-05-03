package org.ayo.qiniu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛所有的接口都由这一个类管理
 * 
 * 每一个对象对应一组ak，sk，domain，bucket
 *
 */
public class QiniuBucket {
	
	private Auth auth;
	private String bucket;
	private BucketManager bucketManager;
	UploadManager uploadManager;
	private String domain;
	
	QiniuBucket(String bucket, String domain, Auth auth, BucketManager bm){
		
		this.bucket = bucket;
		this.auth = auth;
		this.bucketManager = bm;
		this.domain = domain; 
		uploadManager = new UploadManager();
	}
	
	/**
	 * 刷新并返回token，token的有效期默认是10年
	 * @return
	 */
	public String refreshToken(){
		try {
			return auth.uploadToken(bucket, null, 10 * 365 * 24 * 60 * 60, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 上传文件
	 * @param data
	 * @param key
	 * @param token
	 * @return
	 */
	public QNUploadRespModel upload(File data, String key, String token) {
	    try {
	        Response res = uploadManager.put(data, key, token, getParams(), "", false);
	        QNUploadRespModel ret = res.jsonToObject(QNUploadRespModel.class);
	        return ret;
	    } catch (QiniuException e) {
	        Response r = e.response;
	        throw new RuntimeException(JsonUtilsUseFast.toJson(r));
	    }
	}
	
	private StringMap getParams(){
	    return new StringMap().put("x:foo", "foo");
	}
	
	/**
	 * 原始图片的url
	 * @param key
	 * @return
	 */
	public String getUrl(String key)
	{
		return domain + key;
	}
	
	/**
	 * 获取缩略图的url
	 * @param key
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	
	/**
	 * 获取以prefix命名的文件列表
	 * @param bucket
	 * @param prefix
	 * @return
	 */
	public List<FileInfo> getFileList(String bucket, String prefix){
		List<FileInfo> list = new ArrayList<FileInfo>();
		BucketManager bucketManager = new BucketManager(auth);
		BucketManager.FileListIterator it = bucketManager.createFileListIterator(bucket, "");
		while (it.hasNext()) {
		    FileInfo[] items = it.next();
		    if(items != null){
		    	for (int i = 0; i < items.length; i++) {
					list.add(items[i]);
				}
		    }
		}
		return list;
	}
	
	/**
	 * 获取文件属性
	 * @param bucket
	 * @param key
	 * @return
	 */
	public FileInfo getFileInfo(String bucket, String key){
		FileInfo info = null;
		try {
			info = bucketManager.stat(bucket, key);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	/**
	 * 复制文件
	 * @param srcBucket
	 * @param srcKey
	 * @param destBucket
	 * @param destKey
	 */
	public void copy(String srcBucket, String srcKey, String destBucket, String destKey){
		try {
			bucketManager.copy(srcBucket, srcKey, destBucket, destKey);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 移动文件：剪切
	 * @param srcBucket
	 * @param srcKey
	 * @param destBucket
	 * @param destKey
	 */
	public void move(String srcBucket, String srcKey, String destBucket, String destKey){
		try {
			bucketManager.move(srcBucket, srcKey, destBucket, destKey);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重命名
	 * @param srcBucket
	 * @param srcKey
	 * @param destKey
	 */
	public void rename(String srcBucket, String srcKey, String destKey){
		try {
			bucketManager.rename(srcBucket, srcKey, destKey);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除图片
	 * @param bucket
	 * @param key
	 */
	public void delete(String bucket, String key){
		try {
			bucketManager.delete(bucket, key);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 抓取网络资源
	 * 要求url可公网正常访问，不指定 key 时以文件的 hash 值为 key
	 */
	public boolean fetch(String srcUrl, String destBucket, String key){
		try {
			bucketManager.fetch(srcUrl, destBucket, key);
			return true;
		} catch (QiniuException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	* 根据前缀获取文件列表的迭代器
	*
	* @param bucket    空间名
	* @param prefix    文件名前缀
	* @param limit     每次迭代的长度限制，最大1000，推荐值 100
	* @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
	* @return FileInfo迭代器
	*/

	//BucketManager.FileListIterator it = bucketManager.createFileListIterator(bucket, prefix)

//	BucketManager.FileListIterator it = bucketManager.createFileListIterator(bucket, prefix, 100, null);
//
//	while (it.hasNext()) {
//	    FileInfo[] items = it.next();
//	    if (items.length > 1) {
//	        assertNotNull(items[0]);
//	    }
//	}
	
	/*
	 批量操作

当您需要一次性进行多个操作时, 可以使用批量操作.

BucketManager.Batch ops = new BucketManager.Batch()
        .copy(TestConfig.bucket, TestConfig.key, TestConfig.bucket, key)
        .move(TestConfig.bucket, key1, TestConfig.bucket, key2)
        .rename(TestConfig.bucket, key3, key4)
        .stat(TestConfig.bucket, array)
        .stat(TestConfig.bucket, array[0])
        .stat(TestConfig.bucket, array[1], array[2])
        .delete(TestConfig.bucket, array1)
        .delete(TestConfig.bucket, array1[0])
        .delete(TestConfig.bucket, array1[1], array1[2]);
try {
    Response r = bucketManager.batch(ops);
    BatchStatus[] bs = r.jsonToObject(BatchStatus[].class);
    for (BatchStatus b : bs) {
        assertEquals(200, b.code);
    }
} catch (QiniuException e) {
    Response r = e.response;
    // 请求失败时简单状态信息
    log.error(r.toString());
    try {
        // 响应的文本信息
        log.error(r.bodyString());
    } catch (QiniuException e1) {
        //ignore
    }
}
	 */
	
	
}
