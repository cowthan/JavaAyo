package com.cowthan.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioUnBlockingClient {

	private SocketChannel sc;
	private SocketChannelManager scm;

	public void connect(String hostname, int port) throws IOException{
		sc = SocketChannel.open();
		sc.configureBlocking(false);
		sc.connect(new InetSocketAddress(hostname, port));  //在这里不阻塞，连接成功前，可能就返回了
		while(!sc.finishConnect()){
			System.out.println("连接中...");
		}
		
		System.out.println("连上了");
		
		scm = new SocketChannelManager();
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(scm);
		
		scm.add(hostname, sc);
	}
	
	public SocketChannelManager getSocketChannelManager(){
		return this.scm;
	}
	
	public void close() throws IOException{
		if(sc != null && sc.isOpen()){
			sc.close();
		}
	}
}
