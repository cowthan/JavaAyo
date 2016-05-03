package org.ayo.codeworld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.ayo.codeworld.entity.AyoResponseTimeline;
import org.ayo.codeworld.entity.AyoTimeline;
import org.ayo.codeworld.entity.AyoUser;
import org.ayo.lang.Ayo;
import org.ayo.lang.JsonUtilsUseFast;

import com.qiniu.storage.model.FileInfo;
import com.qiniu.test.QNBucketMgmr;
import com.qiniu.test.QNUrlMgmr;

public class Utils {
	
	public static void save(AyoResponseTimeline resp, String dirPath){
		
		String json = JsonUtilsUseFast.toJson(resp);
		//System.out.println(json);
		//把json写进文件，目录固定，文件名：n.json  n从1递增，n就是页数
		
		File dir = new File(dirPath);
		File[] jsonFiles = dir.listFiles();
		
		//获取当前最大页数
		int max = 1;
		if(jsonFiles == null || jsonFiles.length == 0){
			max = 0;
		}else{
			for(File f: jsonFiles){
				String sPage = f.getName();
				sPage = sPage.replace(".json", "");
				System.out.println("页数：" + sPage);
				int page = Integer.parseInt(sPage);
				if(page > max){
					max = page;
				}
			}
		}
		String nextFilename = (max + 1) + ".json";
		System.out.println("下一个文件名：" + nextFilename);
		
		//写文件
		File newFile = new File(dir, nextFilename);
		try {
			FileOutputStream os = new FileOutputStream(newFile);
			os.write(json.getBytes("utf-8"));
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得七牛的所有文件，并拼出url
	 * @param ak
	 * @param sk
	 * @param bucket
	 * @param host
	 * @return
	 */
	public static List<String> getQiniuImageList(String ak, String sk, String bucket, String host){
		
		QNBucketMgmr bucketMgmr = QNBucketMgmr.newInstance(ak, sk);
//		String[] buckets = bucketMgmr.getBuckets();
//		if(buckets == null){
//			System.out.println("3 获取空间列表：失败");
//		}else{
//			//System.out.println("3 获取空间列表：\n" + JsonUtilsUseFast.toJson(buckets));
//		}
		
		///4 获取指定空间下的文件列表
		List<FileInfo> files = bucketMgmr.getFileList(bucket, "");
		Collections.sort(files, new Comparator<FileInfo>() {

			@Override
			public int compare(FileInfo o1, FileInfo o2) {
				return new Long(o1.putTime).compareTo(new Long(o2.putTime));
			}
			
		});
		//System.out.println("4 获取文件列表：\n" + JsonUtilsUseFast.toJson(files));
		///转成url，好显示
		if(files != null){
			List<String> images = new ArrayList<String>();
			for(FileInfo f: files){
				String url = QNUrlMgmr.newInstance(host).getUrl(f.key);
				images.add(url);
			}
			//System.out.println("文件列表：(" + images.size() +")\n" + JsonUtilsUseFast.toJson(images));
			return images;
		}
		
		return null;
	}
	
	public static void process(String ak, String sk, String bucket, String host, String dirPath, int type){
		List<String> list = Utils.getQiniuImageList(ak, sk, bucket, host);
		if(list == null) return;
		
		List<List<String>> data = Utils.splitByFileType(list);
		if(data == null || data.size() == 0){
			System.out.println("数据为空--1");
			return;
		}
		
		List<String> jpgList = data.get(0);
		List<String> gifList = data.get(1);
		List<String> vedioList = data.get(2);
		java.util.Random r = new java.util.Random();
		r.setSeed(System.currentTimeMillis());
		
		//jpg
		if(Ayo.isEmpty(jpgList)){
			System.out.println("图片为空");
		}else{
			if(type == 1 || type == 4) Utils.generateTimeLine(dirPath, jpgList, 9, r);
		}
		
		//gif
		if(Ayo.isEmpty(gifList)){
			System.out.println("gif为空");
		}else{
			if(type == 2 || type == 4) Utils.generateTimeLine(dirPath, gifList, 9, r);
		}
		
		//vedio
		if(Ayo.isEmpty(vedioList)){
			System.out.println("vedio为空");
		}else{
			if(type == 3 || type == 4) Utils.generateTimeLine(dirPath, vedioList, 1, r);
		}
	}
	
	
	/**
	 * 七牛下来的文件有3个类型，jpg，gif，视频，依据这个拆分成3个List
	 * @param list
	 * @return
	 */
	public static List<List<String>> splitByFileType(List<String> list){
		
		List<String> jpgList = new ArrayList<String>();
		List<String> gifList = new ArrayList<String>();
		List<String> vedioList = new ArrayList<String>();
		
		
		for(String s: list){
			String s2 = s.toLowerCase();
			if(s2.endsWith(".gif")){
				gifList.add(s);
			}else if(s2.endsWith("jpg") || s2.endsWith("jpeg") || s2.endsWith("png") || s2.endsWith("bmp")){
				jpgList.add(s);
			}else if(s2.endsWith("mp4") || s2.endsWith("wmv") || s2.endsWith("flv") || s2.endsWith("swf") || s2.endsWith("avi")){
				vedioList.add(s);
			}
		}
		
		List<List<String>> res = new ArrayList<List<String>>();
		res.add(jpgList);
		res.add(gifList);
		res.add(vedioList);
		
		return res;
		
	}
	
	
	/**
	 * 把一个列表分割成n个列表，每个列表元素个数为size
	 * @param list
	 * @return
	 */
	public static List<List<String>> splitBySize(List<String> list, int size){
		List<List<String>> res = new ArrayList<List<String>>();
		
		if(list.size() <= size){
			res.add(list);
		}else{
			int pointer = 0;
			while(pointer < list.size()){
				int start = pointer;
				int end = pointer + size;
				if(end > list.size()){
					end = list.size();
				}
				List<String> subList = list.subList(start, end);
				res.add(subList);
				pointer += size;
			}
		}
		
		return res;
	}
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static AyoUser getDefaultUser(){
		AyoUser user = new AyoUser();
		user.id = "1";
		user.name = "则不达";
		user.avatar_large = "http://ww1.sinaimg.cn/crop.7.22.1192.1192.1024/5c6defebjw8epti0r9noaj20xc0y1n0x.jpg"; 
		return user;
	}
	
	public static void generateTimeLine(String dirPath, List<String> list, int size, java.util.Random r){
		
		AyoResponseTimeline resp = new AyoResponseTimeline();
		resp.statuses = new ArrayList<AyoTimeline>();
		
		List<List<String>> subLists = Utils.splitBySize(list, size);
		if(!Ayo.isEmpty(subLists)){
			for(List<String> subList: subLists){
				AyoTimeline t = new AyoTimeline();
				t.id = System.currentTimeMillis() + "";
				t.text = Data.getTextForCL(r.nextInt(Data.Texts_For_Cl.length));
				t.user = Utils.getDefaultUser();
				t.source = "则不达微博";
				t.created_at = new Date().getTime() + "";
				t.type = 1;
				t.urls = subList;
				resp.statuses.add(t);
				
				if(resp.statuses.size() == 10){
					//10个存一次，因为满一页了
					Utils.save(resp, dirPath);
					
					//重新创建一页
					resp = new AyoResponseTimeline();
					resp.statuses = new ArrayList<AyoTimeline>();
				}
					
				Utils.sleep(10);
			}
			
			if(resp != null && resp.statuses.size() > 0){
				Utils.save(resp, dirPath);
			}
		}
	}
	
	
	public static void generateTimeLine2(String dirPath, List<List<String>> lists, int size, java.util.Random r){
		
		AyoResponseTimeline resp = new AyoResponseTimeline();
		resp.statuses = new ArrayList<AyoTimeline>();
		
		List<List<String>> subLists = lists;
		if(!Ayo.isEmpty(subLists)){
			for(List<String> subList: subLists){
				AyoTimeline t = new AyoTimeline();
				t.id = System.currentTimeMillis() + "";
				t.text = Data.getTextForCL(r.nextInt(Data.Texts_For_Cl.length));
				t.user = Utils.getDefaultUser();
				t.source = "则不达微博";
				t.created_at = new Date().getTime() + "";
				t.type = 1;
				t.urls = subList;
				resp.statuses.add(t);
				
				if(resp.statuses.size() == 10){
					//10个存一次，因为满一页了
					Utils.save(resp, dirPath);
					
					//重新创建一页
					resp = new AyoResponseTimeline();
					resp.statuses = new ArrayList<AyoTimeline>();
				}
					
				Utils.sleep(10);
			}
			
			if(resp != null && resp.statuses.size() > 0){
				Utils.save(resp, dirPath);
			}
		}
	}
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		
		
		List<List<String>> res = splitBySize(list, 2);
		System.out.println(JsonUtilsUseFast.toJson(res));
	}

}
