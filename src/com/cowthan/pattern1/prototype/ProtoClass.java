package com.cowthan.pattern1.prototype;

import java.util.ArrayList;

/**
 * 原型模式：
 * 
 * 利用java的Clonable接口，直接从内存中拷贝二进制流创建一个新的对象：
 * 1、不会调用构造方法，需要重写Object的clone()方法
 * 2、基本类型都会被拷贝
 * 3、浅拷贝：引用类型只会被引用值，还是指向同一个对象
 * 4、深拷贝：需要显式的调用引用类型的clone()方法
 * 5、final成员和原型模式冲突，会报错，所以应该去掉final修饰符
 * 6、String虽然是引用类型，但是java希望你把它当做基本类型看待，在原型模式中，也当成
 *    基本类型看待就行了
 * 
 * 使用：
 * 1、原型模式一般不会单独被使用，而是和工厂模式一起使用，在工厂中拷贝，然后返回
 * 2、当new一个对象需要耗费过多资源，或者对象需要被大量创建时，就用原型模式
 *
 * new：就是生一个孩子，从小养到大
 * clone：就是找一个成人，拿他DNA直接拷贝出一个成人
 */
public class ProtoClass implements Cloneable{
	int i;
	ArrayList<String> list = new ArrayList<String>();
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ProtoClass proto = null;
		try {
			proto = (ProtoClass) super.clone();  //这要是调this.clone()就无限递归了
			//为了深拷贝
			proto.list = (ArrayList<String>) this.list.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proto;
	}
}
