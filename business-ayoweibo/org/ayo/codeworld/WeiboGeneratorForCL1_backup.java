package org.ayo.codeworld;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ayo.codeworld.entity.AyoResponseTimeline;
import org.ayo.codeworld.entity.AyoTimeline;
import org.ayo.codeworld.entity.AyoUser;




/**
 * 微博--美女
 * 
 * 每页10条
 * 
 * 第一页
 *
 */
public class WeiboGeneratorForCL1_backup {
	
	public static <T> List<T> newList(T...t){
		ArrayList<T> r = new ArrayList<T>();
		if(t == null || t.length == 0){
			return r;
		}else{
			for(T a: t){
				r.add(a);
			}
			return r;
		}
	}
	
	public static void main(String[] args) {
		
		int idStart = 1001;
		String dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\cl";
		
		AyoResponseTimeline resp = new AyoResponseTimeline();
		resp.statuses = new ArrayList<AyoTimeline>();
		
		AyoUser user = new AyoUser();
		user.id = "1";
		user.name = "则不达";
		user.avatar_large = "http://www.sxdaily.com.cn/NMediaFile/2013/1219/SXRB201312190816000348764917079.jpg"; 
		
		AyoTimeline t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第一篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第2篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第3篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第4篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第5篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第6篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第7篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第8篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第9篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-001ab9d18668e2fb6a55e656a8106778.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-0026621ce20037cf5a25e14cec23f7e8.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-01175423cddfb54ac9950dadeb4e51ae.jpg"
		);
		resp.statuses.add(t);
		
		t = new AyoTimeline();
		t.id = "" + (++idStart);
		t.text = "这是第10篇微博，虽然只是代码上的一小步，但却是互联网的一大步";
		t.user = user;
		t.source = "未知第三方";
		t.created_at = new Date().getTime() + "";
		t.type = 1;
		t.urls = newList(
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-08bf5c3009c9c27bc6ecfd0844741a06.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-08c1a365e89765736c1f474b90885729.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1ac7e795b9e29ec5c247c569d825b301.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1773769d88989a20fd2ee1f4ce738871.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1875f9a3daa9c8a83ac48b8dfb168118.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1e2bb41dbc3daa12954f8e0aa4c694af.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1e0d3bbe531cb91fbb1747c966f759c8.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-1cae742607bdfa5c88a0e5c629c9513d.gif",
				"http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-3c783854caef67b90591c7393c85f35b.gif"
		);
		resp.statuses.add(t);
		
		Utils.save(resp, dirPath);
		
	}
	
}
