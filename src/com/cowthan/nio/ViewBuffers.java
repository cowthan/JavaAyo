package com.cowthan.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 视图缓冲器
 *
 */
public class ViewBuffers {
	
	public static void main(String[] args) {
		
		ByteBuffer buff = ByteBuffer.wrap(new byte[]{
				0x01, 0x02, 0x03, 0x04, 
				0x05, 0x05, 0x05});
		
		buff.rewind();
		while(buff.hasRemaining()){
			System.out.print(buff.get() + ", ");
		}
		System.out.println();
		
		
		//读取时，后三个因为拼不成一个int，所以读不到
		buff.rewind();
		IntBuffer ibuff = buff.asIntBuffer();
//		while(ibuff.hasRemaining()){
//			System.out.print(ibuff.get() + ", ");
//		}
//		System.out.println();
//		
//		ibuff.flip();
		ibuff.put(100);
//		ibuff.flip();
		buff.rewind();
		while(buff.hasRemaining()){
			System.out.print(buff.get() + ", ");
		}
		System.out.println();
		
	}
	
}
