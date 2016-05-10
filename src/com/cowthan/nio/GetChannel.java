package com.cowthan.nio;

import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.io.*;

public class GetChannel {
	private static final int BSIZE = 1024;

	public static void main(String[] args) throws Exception {
		
		// 写文件
		FileChannel fc = new FileOutputStream("data.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text ".getBytes()));  //
		fc.close();
		
		// 写文件：append
		fc = new RandomAccessFile("data.txt", "rw").getChannel();
		fc.position(fc.size()); // Move to the end
		fc.write(ByteBuffer.wrap("Some more".getBytes()));
		fc.close();
		
		// 读文件
		fc = new FileInputStream("data.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate((int) fc.size());
		fc.read(buff);
		buff.flip();
		
		
		System.out.println("读取1：");
		String str = new String(buff.array(), "utf-8");
		System.out.println(str);
		
		System.out.println("读取2：");
		CharBuffer cbuff = Charset.forName("utf-8").decode(buff);
		System.out.println(cbuff.toString());
		
		buff.rewind();
		System.out.println("读取3：");
		while (buff.hasRemaining()){
			System.out.print((char) buff.get());
		}
		
	}
} /*
 * Output: Some text Some more
 */// :~
