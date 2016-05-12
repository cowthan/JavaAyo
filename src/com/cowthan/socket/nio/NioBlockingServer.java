package com.cowthan.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioBlockingServer {
	
	private volatile boolean isCanceled = false;
	
	public void start(String host, int port) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(host, port));
		System.out.println("服务器开启：地址" + host + ", 端口" + port);
		while(!isCanceled && !Thread.interrupted()){
			SocketChannel sc = ssc.accept();
			System.out.println("客户端连接：" + sc.getRemoteAddress());
		}
		System.out.println("服务器关闭...");
	}
	
	
	
}
