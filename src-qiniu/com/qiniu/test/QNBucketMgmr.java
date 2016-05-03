package com.qiniu.test;

import java.util.ArrayList;
import java.util.List;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

/**
 * 空间和文件管理
 * @author cowthan
 *
 */
public class QNBucketMgmr {

	public static QNBucketMgmr newInstance(String ak, String sk){
		return new QNBucketMgmr(ak, sk);
	}
	
	private QNBucketMgmr(String ak, String sk){
		this.ak = ak;
		this.sk = sk;
		auth = Auth.create(ak, sk);
		bucketManager = new BucketManager(auth);
	}
	
	private Auth auth;
	private String ak;
	private String sk;
	private BucketManager bucketManager;
	
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
