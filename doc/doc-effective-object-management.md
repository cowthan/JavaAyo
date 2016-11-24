安卓从入门到放弃系列1：对象管理
=========================


* 目录
	* 1 静态工厂
		* 1.1 套路
		* 1.2 服务提供者框架------------给个实例？？？？、
	* 2 Builder模式：创建对象参数较多时用
		* 2.1 简单模式
		* 2.2 Builder接口
	* 3 单例模式
	* 4 工厂模式：对象成体系，横向分为产品族，纵向分为产品等级
		* 简单工厂模式
		* 抽象工厂：单工厂
		* 抽象工厂：多工厂
		* Builder + 抽象工厂
	* 5 对象池
		* 创建对象是个复杂的过程，并且又要重复创建时
	* 6 Flyweight享元模式
		* 对象数量太多，且可以划分为可变属性和不可变属性部分，可变部分可以由外部传入
	* 9 备忘录模式
	* 10 原型模式
	* 11 clone
	* 12 序列化
	* 7 四种引用
	* 8 垃圾回收
	* 13 对象缓存
		* 怎么存的问题
		* 怎么删的问题

	
	

###1 静态工厂

####1.1 套路

```java
public static class Boolean{
		
	//预定义的两个对象，某种意义上算是缓存，使用时可以省去创建对象的过程
	public static final Boolean True = new Boolean(true);
	public static final Boolean False = new Boolean(false);
	
	//静态工厂方法
	public static Boolean valueOf(boolean b){
		return new Boolean(b); //b ? True : False;
	}

	private boolean v;
	
	private Boolean(boolean b){
		v = b;
	}
	
}
```

* 静态工厂方法的好处是：
    * `优势1`：方法有名字，构造器没有，如BigInteger.probablePrime方法，这就是静态工厂，表示BitInteger(int, int, Random)返回的很可能是素数
    * 对于多种参数形式的构造器，可以各自对应一个静态工厂，并给不同名字，还是参考probablePrime
    * `优势2`：可以预先构建对象，如Boolean.TRUE和FALSE，类似Flyweight模式
	* 可以管理对象个数，如单例模式，也可以仿枚举，业务逻辑上的相等的值，只给一个对象
	* `优势3`：可以返回任意子类型的对象，返回的父类或者接口引用，具体实现类甚至可以对外隐藏，参考Java Collections Framework中集合接口的32个便利实现
	* （1）Collections里有unmodified, empty, checked, synchronized各8个方法，对应8种不同的集合类型
	* 这8种集合类型就是：Collection, List, Map, SortedMap, NavigableMap, Set, SortedSet, NavigableSet，注意这是接口类型，对外的
	* 返回的实际类型是什么呢，都是以private static class的形式实现的，并未对外公开，所以可以jdk随时修改，提升性能或修改实现
	* （2）EnumSet：其静态工厂方法会根据底层枚举类型大小，返回RegalarEnumSet对象或者JumboEnumSet对象，而且这对外部用户是隐藏的
	* （3）这里又能引出服务器提供者的概念
	* `优势4`：简化Map<String, String> m = new HashMap<String, String>()这种繁琐的调用


* 命名套路
	* 静态工厂方法终究也只是普通的static方法，文档中并不会特别对待，这是个缺点
	* 所以这里有些命名套路，还是应该遵守的
	* valueOf：类似Boolean.valueOf()，实际是类型转换，参数和返回的实例有相同的值
	* of：valueOf的简洁写法，在EnumSet中流行起来
	* getInstance：参数可选
	* newInstance：隐含的意思是每次都是new一个新的
	* getType, newType
	


####1.2 服务提供者框架：Service Provider Framework

这是从静态工厂好处3里引出来的，对外提供一个接口，客户端依赖于此接口实例，但并不关心具体实现

* 三大组件：以JDBC为例
	* 服务接口：Service Interface，提供者实现，如Connection
	* 提供者注册API：Provider Registreation API，用来注册实现，让客户端访问，如DriverManager.registerDriver()
	* 服务访问API：Service Access API，客户端用来获取服务实例，这里就是灵活的静态工厂，如DriverManager.getConnection()
	* 服务提供者接口，Service Provider Interface，可选，用来创建服务实例，如果没有这个，就得按照类名注册，并通过反射实例化，如Driver就是这个角色


```java

/**
 * ================服务接口：Service Interface==================
 * 一个对外提供服务的接口，并且不同情况，会产生不同的Service对象，
 * 即通过Service的不同实现，对外提供不同的服务
 *
 */
public interface Service {
	
	void doService();

}


/**
 * ================服务提供者接口==================
 * 用来生成Service对象，注意，如果不使用Provider，则注册到Services的就得是Service实现类的Class对象，
 * newInstance也只能通过反射来了
 * 问题就是Provider实现类应该有几个
 *
 */
public interface Provider {
	Service newService();
}

public class Services {

	private Services(){}
	
	//================提供者注册API==================//
	//这里要么注册provider对象，要么注册Service实现类的Class，你选吧
	private static final Map<String, Provider> providers = new ConcurrentHashMap<>();
	public static final String DEFAULT_PROVIDER_NAME = "<def>";
	
	public static void registerDefaultProvider(Provider p){
		registerProvider(DEFAULT_PROVIDER_NAME, p);
	}

	public static void registerProvider(String defaultProviderName, Provider p) {
		providers.put(defaultProviderName, p);
	}
	
	//================服务访问API==================//
	public static Service newInstance(){
		return newInstance(DEFAULT_PROVIDER_NAME);
	}

	public static Service newInstance(String name) {
		Provider p = providers.get(name);
		if(p == null){
			throw new IllegalArgumentException("No provider registered with name + " + name);
		}
		return p.newService();
	}
}

```


###2 构建器：建造者模式，Builder

* 使用场景：
    * 原始模式：构造器或者静态工厂形式太多，参数多
    * JavaBeans模式：new一个空对象然后set各种参数，代码不宜管理，set过程中，对象也可能处于不一致状态
    * 把JavaBeans的一组set封装起来，就是一个建造者模式的原始形态，但里面依旧需要对对象的一致性负责
    	* 这里说的一致性问题，意思是new完对象，还需要一组set之后，对象才能正常工作，
    	* 但set期间，对象已经有了，缺不能正常工作，这就是一个危险的状态
    	* 下面的Builder模式，就不存在这个不一致的状态，因为对象最终还是通过一个构造器出来后就已经可以正常工作了
    * 所以这时使用Builder模式，既能保证JavaBeans的可读性，又能保证原始模式的安全性
    * ImageLoader的初始化，AlertDialog的初始化，都使用了这种模式，参数很多，有些必填（放到Buidler的构造方法），有些选填（作为单独方法）


####2.1 简单模式


```java
public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	
	public NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
	
	public static class Builder{
		
		//必填的参数，无默认值
		private final int servingSize;
		private final int servings;
		
		//选填的参数，有默认值
		private int calories = 0;
		private int fat      = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		public Builder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder calories(int val){ calories = val; return this; }
		public Builder fat(int val){ fat = val; return this; }
		public Builder carbohydrate(int val){ carbohydrate = val; return this;}
		public Builder sodium(int val){ sodium = val; return this; }
	
		public NutritionFacts build(){
			return new NutritionFacts(this);
		}
		
	}
	
}

public static void main(String[] args) {
	NutritionFacts cocacola = new NutritionFacts.Builder(240,  8)
			.calories(100)
			.sodium(35)
			.carbohydrate(27)
			.build();
}


```


####2.2 Builder接口

将Builder抽取出来单独做一个接口，这个接口的对象可以：
* 创建任意多的对象
* 其功能就类似于直接传Class对象
* 但比Class对象的newInstance方法多了类型检查，构造方法保证等
* 缺点就是要创建N多个Builder类


```java
public interface Builder<T> {

	public T build();
	
}


package com.cowthan.object_management;

public class NutritionFacts2 {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	
	public NutritionFacts2(MyBuilder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
	
	public static class MyBuilder implements Builder<NutritionFacts2>{
		
		//必填的参数，无默认值
		private final int servingSize;
		private final int servings;
		
		//选填的参数，有默认值
		private int calories = 0;
		private int fat      = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		public MyBuilder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public MyBuilder calories(int val){ calories = val; return this; }
		public MyBuilder fat(int val){ fat = val; return this; }
		public MyBuilder carbohydrate(int val){ carbohydrate = val; return this;}
		public MyBuilder sodium(int val){ sodium = val; return this; }
	
		public NutritionFacts2 build(){
			return new NutritionFacts2(this);
		}
		
	}
	
	public static void main(String[] args) {
		Builder<NutritionFacts2> builder = new NutritionFacts2.MyBuilder(240,  8)
				.calories(100)
				.sodium(35)
				.carbohydrate(27);
		
		NutritionFacts2 cocacola = builder.build();
	}
}

/*
这里的Builder<NutritionFacts2> builder对象，可以传给任意的抽象工厂方法

*/

```


###3 单例模式

* 怎么能破坏单例的限制
	* 反射：反射出私有构造方法，Enum可自然解决，其他方式得强写检查代码
	* 序列化：将单例序列化，再反序列化，出来就是一个新对象，Enum可自然解决，其他方式使用readResolve方法
	* 安卓里的多进程，会产生多个Application



####3.1 饿汉：共有域，或静态工厂

```java
public class Singleton {
	
	public static final Singleton INSTANCE = new Singleton();
	private Singleton(){}
	private Object readResolve(){ return INSTANCE; }
	
	public void provideService(){
		
	}

}
```

```java
public class Singleton {
	
	private static final Singleton INSTANCE = new Singleton();
	private Singleton(){}
	public static Singleton getInstance(){ return INSTANCE; }
	private Object readResolve(){ return INSTANCE; }
	
	public void provideService(){
		
	}

}
```



####3.2 懒汉：双保险模式



####3.3 懒汉：内部类模式



####3.4 枚举

按照effective java书里说法，这个方法虽然没流行起来，但这个是最佳方式，第2版15页

```java
//直接就能防止反射，防止序列化时生成新类
//是否延迟加载不知道
public enum Singleton {
	
	INSTANCE;
	
	public void provideService(){
		
	}

}
```













