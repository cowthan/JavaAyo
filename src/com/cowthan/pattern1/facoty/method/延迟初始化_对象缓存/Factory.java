package com.cowthan.pattern1.facoty.method.延迟初始化_对象缓存;

import java.util.HashMap;
import java.util.Map;
/**
 * 存在意义：
 * 1、Message.obtain()应该就是这种模式
 * 2、如果new一个对象涉及到很多工作，如硬件交互，就可以通过延迟加载来降低对象的
 * 产生和销毁
 * 3、这个模式为什么叫延迟加载，不知道，怎么不叫对象缓存
 * 
 * @author qiaoliang
 *
 */
public class Factory {
	//===这个map用来存放所有已经new出的对象，好提供现成的
	//===还可以用来管理每种对象的最大实例化个数
	private static final Map<String, Product> map = new HashMap<String, Product>();
	
	public static synchronized Product createProduct(String type)
	{
		Product pro = null;
		
		if(map.containsKey(type)){
			pro = map.get(type);
			//可能需要将对象初始化到初始状态
			//可能需要设置对象是不是正在被使用，如果没有空闲的，那就new一个或者等一等
		}else{
			if(type.equals("product-1")){
				//pro = new product-1; 
			}else if(type.equals("product-2")){
				//pro = new product-2
			}//.....
			map.put(type, pro);
		}
		return pro;
	}
}
