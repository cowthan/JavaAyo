package com.cowthan.socket.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理一个，或者一堆SocketChannel，一个manager运行在一个Thread里
 * @author Administrator
 *
 */
public class SocketChannelSelector implements Runnable{

	//private List<SocketChannel> channelList = new LinkedList<SocketChannel>();
	private Map<String, SocketChannel> map = new ConcurrentHashMap<>(); 
	private boolean isClosed = false;
	private Selector selector;
	
	public SocketChannelSelector() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 Exception in thread "pool-2-thread-1" java.util.ConcurrentModificationException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at com.cowthan.socket.nio.SocketChannelManager.run(SocketChannelManager.java:50)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
	 */
	public SocketChannelSelector add(String key, SocketChannel sc){
		if(map.keySet().contains(key)){
			SocketChannel scOld = map.get(key);
			if(scOld != null && scOld.isOpen() && scOld.isConnected()){
				System.out.println(key + "已经有通道了");
			}else{
				if(scOld != null){
					try {
						scOld.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				map.put(key, sc);
				
				try {
					SelectionKey key2 = sc.register(selector, SelectionKey.OP_READ);
				} catch (ClosedChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				
			}
		}else{
			map.put(key, sc);
		}
		return this;
	}
	
	@Override
	public void run() {
		while(!isClosed && !Thread.interrupted()){
			for(String key: map.keySet()){
				SocketChannel sc = map.get(key);
				
				ByteBuffer buf = ByteBuffer.allocate(1024);
				try {
					int bytesRead = sc.read(buf);
					buf.flip();
					if(bytesRead <= 0){

					}else{
						System.out.println("收到消息（来自" + key + "）：" + Charset.forName("utf-8").decode(buf));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String key, String msg){
		SocketChannel sc = map.get(key);
		if(sc == null || !sc.isConnected() || !sc.isOpen()){
			throw new RuntimeException(key + "对应的SocketChannel无效");
		}else{
			ByteBuffer buf = ByteBuffer.allocate(msg.length() * 2);
			try {
				buf.put(msg.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			buf.flip();
			
			try {
				while(buf.hasRemaining()){
					sc.write(buf);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
	}
	
	public boolean close(String key){
		SocketChannel sc = map.get(key);
		if(sc != null){
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		map.remove(key);
		return true;
	}

}
