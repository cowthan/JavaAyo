package com.cowthan.nio;

// Creating a very large file using mapping.
// {RunByHand}
import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class LargeMappedFiles {
	
	///文件大小是128M
	static int length = 0x8FFFFFF; // 128 MB

	public static void main(String[] args) throws Exception {
		
		//创建个文件，大小是128M
		MappedByteBuffer out = new RandomAccessFile("test.dat", "rw")
				.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
		
		//写入
		for (int i = 0; i < length; i++)
			out.put((byte) 'x');
		
		System.out.println("写入完毕");
		
		//读取
		for (int i = length / 2; i < length / 2 + 6; i++)
			System.out.println((char) out.get(i));
	}
} // /:~
