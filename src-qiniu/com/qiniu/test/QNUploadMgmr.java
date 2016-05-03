package com.qiniu.test;

import java.io.File;

import org.ayo.lang.JsonUtilsUseFast;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.test.bean.QNUploadRespModel;
import com.qiniu.util.StringMap;

/**
 * 管理七牛的文件上传功能
 * @author cowthan
 *
 */
public class QNUploadMgmr {
	
	private static QNUploadMgmr instance;
	
	public static QNUploadMgmr getDefault(){
		if(instance == null){
			instance = new QNUploadMgmr();
		}
		return instance;
	}
	
	private QNUploadMgmr(){
		uploadManager = new UploadManager();
	}
	
	
	UploadManager uploadManager;
	
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
	 * 上传数据
	 *
	 * @param data     上传的数据 byte[]、File、filePath
	 * @param key      上传数据保存的文件名
	 * @param token    上传凭证
	 * @param params   自定义参数，如 params.put("x:foo", "foo")
	 * @param mime     指定文件mimetype
	 * @param checkCrc 是否验证crc32
	 * @return
	 * @throws QiniuException
	 */
	public Response put(File data, String key, String token, StringMap params,
	                    String mime, boolean checkCrc) throws QiniuException
	{
		return null;
	}
}
