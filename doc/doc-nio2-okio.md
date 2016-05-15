Okio
================================

github: https://github.com/square/okio

只是对旧IO的封装，没用到Channel，也没用到ByteBuffer

##1 简介：

* 基本接口
	* Source：接口，like InputStream 
		* 输入流，输入到内存，从Source读
		* long read(Buffer sink, long byteCount) throws IOException
			* 返回-1表示EOF，写到sink里
		* Timeout timeout()
			* 返回这个source的超时信息
		* void close() throws IOException
	* Sink：接口，like OutputStream
		* 输出流，从内存输出，往Sink写
		* void write(Buffer source, long byteCount) throws IOException
			* 从source读到sink
		* Timeout timeout();
		* void close() throws IOException;
	* BufferedSource：接口，extends Source
		* 输出缓冲流
		* 提供了一系列读方法
		* 实现类：RealBufferedSource，需要传入一个Source，所以这是一个包装类
	* BufferedSink：接口，extends sink
		* 输入缓冲流
		* 提供了一系列写方法
		* 实现类：RealBufferedSink，需要传入一个Sink，所以这是一个包装类
	* Sink和Source只有3个接口，实现方便，而BufferedSource和BufferedSink提供了一堆便利方法
	* Timeout：读写时有Timeout，主要给Socket用
	* byte stream和char stream的读写没有什么区别，当做byte[], utf8 String，big-endian,little-endian都行，不再用InputStreamReader了
	* Easy to test. The Buffer class implements both BufferedSource and BufferedSink so your test code is simple and clear.
	* 互操作：Source和InputStream可以互换，Sink和OutputStream可以互换，无缝兼容
	
	
* 实用类：
	* DeflaterSink，InflaterSource
	* ForwardingSink，ForwardingSource
	* GzipSink，GzipSource
	* HashingSink，HashingSource

* ByteString和Buffer
	* ByteString：处理字符串
		* 一个不可变的byte序列，immutable sequence of bytes
		* String是基本的，ByteString是String的long lost brother
		* 提供便利方法处理byte
		* 能decode和encode，处理hex, base64, and UTF-8
	* Buffer：处理byte流
		* 一个可变的byte序列，mutable sequence of bytes，像个ArrayList
		* 读写时行为像Queue，write to end，read from front
		* 不需要考虑大小，屏蔽了ByteBuffer的capacity，limit，position等
	* 缓存：把一个utf-8 String decode成ByteString，会缓存，下次再decode，就快了
	* Buffer是一个Segment的LinkedList，所以拷贝不是真的拷贝，只是移动，所以更快
		* 多线程工作时就有优势了，连接network的线程可以迅速的把数据发给work线程（without any copying or ceremony）
	

* 工具
	* AsyncTimeout
	* Base64
	* Options
	* Timeout
	* Util
	* Okio

* Segment相关
	* Segment
	* SegmentPool
	* SegmentedByteString
	
##2 使用

###（1）构造BufferedSink和BufferedSource

```java
//创建Source
Source source = Okio.source(final InputStream in, final Timeout timeout);
source(InputStream in); //new Timeout()
source(File file);
source(Path path, OpenOption... options); //java7
source(Socket socket);

//创建Sink
Sink sink = Okio.sink(OutputStream out);
sink(final OutputStream out, final Timeout timeout);
sink(File file)
appendingSink(File file)
sink(Path path, OpenOption... options)
sink(Socket socket)

//创建BufferedSource：
BufferedSource pngSource = Okio.buffer(Source source); //返回RealBufferedSource对象
BufferedSink pngSink = Okio.buffer(Sink sink); //返回RealBufferedSink对象

//从BufferedSource读取
看例子吧

//往BufferedSink写入
看例子吧

//ByteString
暂停

//Buffer
暂停
```

暂停是因为okio和nio没多大瓜葛啊


##3 例子：来自官网

```java
package com.cowthan.nio.okio;

import java.io.IOException;
import java.io.InputStream;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public class Test1_png {

	public static void main(String[] args) throws IOException {
		InputStream in = Test1_png.class.getResourceAsStream("/com/demo/1.png");
		decodePng(in);
	}

	private static final ByteString PNG_HEADER = ByteString
			.decodeHex("89504e470d0a1a0a");

	public static void decodePng(InputStream in) throws IOException {
		BufferedSource pngSource = Okio.buffer(Okio.source(in));

		ByteString header = pngSource.readByteString(PNG_HEADER.size());
		if (!header.equals(PNG_HEADER)) {
			throw new IOException("Not a PNG.");
		}

		while (true) {
			Buffer chunk = new Buffer();

			// Each chunk is a length, type, data, and CRC offset.
			int length = pngSource.readInt();
			String type = pngSource.readUtf8(4);
			pngSource.readFully(chunk, length);
			int crc = pngSource.readInt();

			decodeChunk(type, chunk);
			if (type.equals("IEND"))
				break;
		}

		pngSource.close();
	}

	private static void decodeChunk(String type, Buffer chunk) {
		if (type.equals("IHDR")) {
			int width = chunk.readInt();
			int height = chunk.readInt();
			System.out.printf("%08x: %s %d x %d%n", chunk.size(), type, width,
					height);
		} else {
			System.out.printf("%08x: %s%n", chunk.size(), type);
		}
	}

}
```
	
	
	