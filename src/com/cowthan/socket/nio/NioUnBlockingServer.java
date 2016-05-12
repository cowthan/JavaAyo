package com.cowthan.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioUnBlockingServer {
	
	private volatile boolean isCanceled = false;
	private SocketChannelManager scm;
	
	public void start(String host, int port) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(host, port));
		ssc.configureBlocking(false);
		System.out.println("服务器开启：地址" + host + ", 端口" + port);
		
		scm = new SocketChannelManager();
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(scm);
		
		while(!isCanceled && !Thread.interrupted()){
			SocketChannel sc = ssc.accept();  //在这不阻塞，因为
			if(sc != null){
				System.out.println("客户端连接：" + sc.getRemoteAddress());
				scm.add(sc.getRemoteAddress().toString(), sc);
			}else{
				//System.out.println("没有新连接，just wait...");
			}
			
			
		}

		System.out.println("服务器关闭...");
	}
	
	public SocketChannelManager getSocketChannelManager(){
		return this.scm;
	}
	
}
