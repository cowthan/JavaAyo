java nio
===========================

* 主题：
	* 通道和缓冲器：提高读写速度，Channel，ByteBuffer等
	* 异步IO：提高线程的利用率，增加系统吞吐量，selector，key等，但以牺牲实时性为代价（折衷是永恒不变的主题）


##1 通道和缓冲器


###（1） 简介

java.nio.*包的引入是为了提高速度，并且旧的IO包已经用nio重新实现过，所以即使你不用nio，也已经收益了

下面的格式可能比较乱，需要配合GetChannel例子来理解

* 如何理解：
    * 你要读数据的地方，可能说的是IO流，可以看做一个煤矿，煤就是数据
    * 通道：Channel，包含煤层的矿藏，就是一个煤矿里有煤的地方
    * 缓冲器：ByteBuffer，可以看做运煤的车，注意这里装车和卸车也是有意义的动作
    * 我们能做的就是派运煤的车去通道，也就是将缓冲器ByteBuffer和Channel连起来，往里送煤（写），往外运煤（读）
    * -------缓一缓-------
    * ByteBuffer是很底层的类，直接存储未加工的字节
    	* 初始化：
    		* 要写数据时，已经有数据了，所以可以得到byte[]
    		* ByteBuffer.wrap(byte[])   //相当于wrap(array, 0, array.length);
    		* ByteBuffer.wrap(byte[], offset, length) //offset + length不能超出byte数组的长度
    		* 要读数据时，最多只能拿到ByteBuffer可能需要大小
    		* ByteBuffer buff = ByteBuffer.allocate((int) fc.size());
    	* 接口：byte的读写，不支持对象，连String也不支持
    	* 将数据放到ByteBuffer里：装车
    		* 上面的wrap方法
    		* 一系列put方法，只支持基本类型
    	* 将数据从ByteBuffer中转出来：卸车
    		* 一系列get方法，只支持基本类型，注意flip
    		* String str = new String(buff.array(), "utf-8")，buff.array()，跟ByteBuffer指针无关
    	* ByteBuffer内部指针：
    		* ByteBuffer里有个指针
    		* fc.read(buff)会从往ByteBuffer里写（装车），从哪儿写，总有个起始位置，就是ByteBuffer指针的位置
    		* 写完，指针直到最后，也就是第一个空白可写区域
    		* 读取里面的信息（卸车），就需要回到起始位置
    			* flip一下
    		* positon操作可以跳到任意位置
    * FileChannel：FileInputStream, FileOutputStream, RandomAccessFile这三个旧类被修改了，以支持channel
    	* Reader和Writer不直接支持Channel，但Channel里提供了便利方法来支持他们
		* 获得FileChannel：
			* FileChannel fc = new FileOutputStream("data.txt").getChannel();  //写
			* FileChannel fc = new FileInputStream("cl1024.json").getChannel(); //读
			* FileChannel fc = new RandomAccessFile("data.txt", "rw").getChannel(); //可读可写
		* 移动文件指针：append写时，断点续传时能用
			* fc.position(fc.size()); // Move to the end
		* 写，将一个ByteBuffer写到Channel里：
			* fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		* 读，将一个channel里的内容，读到ByteBuffer里，读多少，由ByteBuffer的长度决定
			* fc.read(buff);
			* buff.flip(); 读出来的ByteBuffer一般需要再次解析出来，通过getInt,getFloat等操作，读写切换时，需要flip一下
			* flip怎么理解：fc.read(buff)，ByteBuffer里有个指针
				* fc.read(buff)会从往ByteBuffer里写，从哪儿写，总有个起始位置，就是ByteBuffer指针的位置
				* 写完，指针直到最后，也就是第一个空白可写区域
				* 所以现在就好理解了，读完文件，也就是往ByteBuffer写完，指针指向ByteBuffer最后，你再读取里面的信息，就需要回到起始位置
				
				
* 总结：
	* FileInputStream，FileOutputStream，这相当于煤矿
		* 以前你直接操作stream的read，write，参数是byte[]
		* read，write直接操作煤矿
		* 直接通过byte[]读写，相当于用铁锨铲煤
	* 在new io里，你不能直接操作煤矿了，而是获取一个通道：FileChannel
		* 通过channel的read，write来操作数据，position，seek等，就是移动指针（文件指针）
		* read，write的参数是ByteBuffer
		* 通过ByteBuffer来包装数据，相当于用车拉煤
	* 由于把byte[]用ByteBuffer包装起来，又面临一个装车和卸车的问题
		* 装车：写文件（wrap, put等方法），读文件（channel.read(buff)）
		* 卸车：读文件（get各种基本类型），写文件（channel.write(buff)）
		* 全车操作：array
		* 注意flip的问题，读写切换时，需要flip一下，而且这还不确定就是指针操作
		* 注意rewind的问题，读着读着，想回头从头再读，就得rewind，这个肯定是指针操作
		* buff.hasRemaining()，指针是否到头了
	* 可以看出，Channel和ByteBuffer提供的接口都比较低级，直接和操作系统契合，说是这就是快的原因




####
例子，代码比较短，直接贴过来
```java
package com.cowthan.nio;

import java.nio.*;
import java.nio.channels.*;
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
		
		
		System.out.println("读取：");
		String str = new String(buff.array(), "utf-8");
		System.out.println(str);
		
		System.out.println("读取2：");
		while (buff.hasRemaining()){
			System.out.print((char) buff.get());
		}
	}
} /*
 * Output: Some text Some more
 */// :~

```

###（2）更多

* 更多
	* ByteBuffer.allocate(len)的大小问题，大块的移动数据是快的关键，所以长度很重要，但没啥标准，根据情况定吧，1024（1K）小了
	* ByteBuffer.wrap(byte[])，不会再复制数组，而是直接以参数为底层数组，快
	* 复制文件时，一个ByteBuffer对象会不断从src的channel来read，并写入dest的channel，注意：
		* src.read(buff);  buff.flip();  dest.write(buff); buff.clear()
		* ByteBuffer必须clear了，才能重新从Channel读
	* ByteBuffer.flip(), clear()比较拙劣，但这正是为了最大速度付出的代价
	
```java
///复制文件的部分代码（更优化的复制文件是用transfer接口，直接通道相连）
ByteBuffer buff = ByteBuffer.allocate(1024); //1K
while(src.read(buff) != -1){
	buff.flip(); //准备卸车
	dest.write(buff); //卸车了
	buff.clear(); //其实这才是真正的卸车，并送回通道那头（可以再次read(buff)了）
}
```

###（3）连接通道

上面说过，nio通过大块数据的移动来加快读写速度，前面这个大小都由ByteBuffer来控制，
其实还有方法可以直接将读写两个Channel相连

这也是实现文件复制的更好的方法

```java
public class TransferTo {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("arguments: sourcefile destfile");
			System.exit(1);
		}
		FileChannel in = new FileInputStream(args[0]).getChannel(), out = new FileOutputStream(
				args[1]).getChannel();
		in.transferTo(0, in.size(), out);
		// 或者：
		// out.transferFrom(in, 0, in.size());
	}
} // /:~
```
	
###（4）字符流

ByteBuffer是最原始的，其实就是字节流，适用于二进制数据的读写，图片文件等

但我们更常用的，其实是字符串

* 字符串涉及到的类：
	* CharBuffer：注意，Channel是直接和ByteBuffer交流，所以CharBuffer只能算是上层封装
	* Charset：编码相关，字节流到字符串，肯定会有编码相关的问题
	* CharBuffer.toString()：得到字符串

* 怎么得到CharBuffer
	* 方法1：ByteBuffer.asCharBuffer()，局限在于使用系统默认编码
	* 方法2：Charset.forName("utf-8").decode(buff)，相当于new String(buff.array(), "utf-8")的高级版
		* 相对的，Charset.forName("utf-8").encode(cbuff)，返回个ByteBuffer，就相当于String.getBytes("utf-8)

* CharBuffer读写
	* put(String)：写
	* toString()：读，就拿到了字符串


`====先休息一下，说说怎么得到编码相关的一些信息吧====`

```java
//打印系统支持的所有编码，及其别名
import java.nio.charset.*;
import java.util.*;

public class AvailableCharSets {
	public static void main(String[] args) {
		SortedMap<String, Charset> charSets = Charset.availableCharsets();
		Iterator<String> it = charSets.keySet().iterator();
		while (it.hasNext()) {
			String csName = it.next();
			System.out.print(csName);
			Iterator aliases = charSets.get(csName).aliases().iterator();
			if (aliases.hasNext())
				System.out.print(": ");
			while (aliases.hasNext()) {
				System.out.print(aliases.next());
				if (aliases.hasNext())
					System.out.print(", ");
			}
			System.out.println();
		}
	}
}
/*
部分输出：
KOI8-U: koi8_u
Shift_JIS: shift_jis, x-sjis, sjis, shift-jis, ms_kanji, csShiftJIS
TIS-620: tis620, tis620.2533
US-ASCII: ANSI_X3.4-1968, cp367, csASCII, iso-ir-6, ASCII, iso_646.irv:1983, ANSI_X3.4-1986, ascii7, default, ISO_646.irv:1991, ISO646-US, IBM367, 646, us
UTF-16: UTF_16, unicode, utf16, UnicodeBig
UTF-16BE: X-UTF-16BE, UTF_16BE, ISO-10646-UCS-2, UnicodeBigUnmarked
UTF-16LE: UnicodeLittleUnmarked, UTF_16LE, X-UTF-16LE
UTF-32: UTF_32, UTF32
UTF-32BE: X-UTF-32BE, UTF_32BE
UTF-32LE: X-UTF-32LE, UTF_32LE
UTF-8: unicode-1-1-utf-8, UTF8
windows-1250: cp1250, cp5346
windows-1251: cp5347, ansi-1251, cp1251
windows-1252: cp5348, cp1252
windows-1253: cp1253, cp5349
*/
```

`=====ByteBuffer.asCharBuffer()的局限：没指定编码，容易乱码=====`

* 这个一般情况下不能用，为何：
    * asCharBuffer()会把ByteBuffer转为CharBuffer，但用的是系统默认编码
    

####（5）各种Buffer

ByteBuffer.asLongBuffer(), asIntBuffer(), asDoubleBuffer()等一系列


####（6）视图缓冲器

* 不多说：
    * ByteBuffer底层是一个byte[]，get()方法返回一个byte，1字节，8bit，10字节可以get几次？10次
    * ByteBuffer.asIntBuffer()得到IntBuffer，底层是一个int[]，get()方法返回一个int，还是10字节，可以get几次？
	* 同理，还有ShortBuffer, LongBuffer, FloatBuffer, DoubleBuffer，这些就是ByteBuffer的一个视图，所以叫视图缓冲器
	* asIntBuffer时，如果ByteBuffer本身有5个byte，则其中前4个会变成IntBuffer的第0个元素，第5个被忽略了，但并未被丢弃
	* 往新的IntBuffer放数据（put(int)），默认时会从头开始写，写入的数据会反映到原来的ByteBuffer上



###2 异步IO
