package com.cowthan.socket.nio;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	static String serverHost = "127.0.0.1";
	static int port = 9999;
	
	public static void main(String[] args) throws IOException{
		testUnBlocking();
	}
	
	
	private static void testUnBlocking() throws IOException{
		
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		
		//每隔3秒来一个新连接
		ses.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				NioUnBlockingClient client = new NioUnBlockingClient();
				try {
					client.connect(serverHost, port);
					
					ses.scheduleAtFixedRate(new Runnable() {
						
						@Override
						public void run() {
							client.getSocketChannelManager().send(serverHost, "haha，你好");
						}
					}, 3, 3, TimeUnit.SECONDS);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 1000, 3000, TimeUnit.MILLISECONDS);
		
		NioUnBlockingServer server = new NioUnBlockingServer();
		server.start(serverHost, port);
		
	}
	
	
	
	private static void testBlocking() throws IOException{
		
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				NioBlockingClient client = new NioBlockingClient();
				try {
					client.connect(serverHost, port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 1000, 3000, TimeUnit.MILLISECONDS);
		
		NioBlockingServer server = new NioBlockingServer();
		server.start(serverHost, port);
		
	}
	
	
	
}
