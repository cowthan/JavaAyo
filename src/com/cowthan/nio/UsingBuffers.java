package com.cowthan.nio;

import java.nio.*;

public class UsingBuffers {
	/**
	 * 给一个字符串，交换相邻的两个字符
	 */
	private static void symmetricScramble(CharBuffer buffer) {
		while (buffer.hasRemaining()) {
			buffer.mark();
			char c1 = buffer.get();
			char c2 = buffer.get();
			buffer.reset();
			buffer.put(c2).put(c1);
		}
	}
	
	private static void symmetricScramble2(CharBuffer buffer) {
		while (buffer.hasRemaining()) {
			int position = buffer.position();
			char c1 = buffer.get();
			char c2 = buffer.get();
			buffer.position(position);
			buffer.put(c2).put(c1);
		}
	}

	public static void main(String[] args) {
		char[] data = "12345678".toCharArray();
		ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
		CharBuffer cb = bb.asCharBuffer();
		cb.put(data);
		System.out.println(cb.rewind());
		
		symmetricScramble(cb);
		System.out.println(cb.rewind());
		
		symmetricScramble2(cb);
		System.out.println(cb.rewind());
	}
} /*
 * Output: UsingBuffers sUniBgfuefsr UsingBuffers
 */// :~
