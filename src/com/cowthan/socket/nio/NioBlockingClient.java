package com.cowthan.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class NioBlockingClient {

	private SocketChannel sc;

	public void connect(String hostname, int port) throws IOException{
		sc = SocketChannel.open();
		sc.configureBlocking(true);
		sc.connect(new InetSocketAddress(hostname, port)); //在这里阻塞，直到连接成功
		System.out.println("连上了");
	}
	
	
	
	
	public void close() throws IOException{
		if(sc != null && sc.isOpen()){
			sc.close();
		}
	}
}
