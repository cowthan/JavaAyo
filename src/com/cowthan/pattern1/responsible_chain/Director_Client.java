package com.cowthan.pattern1.responsible_chain;

public class Director_Client {
	public static void main(String[] args) {
		//===先构造责任链，然后把第一个暴露给用户， 这部分在实际中经常封装在另一个
		//类中，将h1返回给用户
		Handler h1 = new Handler_Level_1();
		Handler h2 = new Handler_Level_2();
		Handler h3 = new Handler_Level_3();
		
		h1.setNext(h2);
		h2.setNext(h3);
		
		//===用户创建Request
		Request req = new Request();
		req.setLevel(2);
		
		//===用户传入请求
		Response resp = h1.handleMsg(req);
		System.out.println(resp.info);
	}
}
