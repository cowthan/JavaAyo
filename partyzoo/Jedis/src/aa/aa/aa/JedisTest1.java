package aa.aa.aa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class JedisTest1 {
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("114.215.101.174", 6379);

		//权限认证
//		jedis.auth("admin");  
		
		/**
		 * 1 字符串操作：get set
		 */
		String keys = "namesfsdf";
		// 删数据
		jedis.del(keys);
		// 存数据
		jedis.set(keys, "snowolfsfsdfsdf");
		// 取数据
		String value = jedis.get(keys);

		System.out.println("1 字符串操作：" + keys + "==>" + value);
		//多个键值对一块操作
		jedis.mset("name","liuling","age","23","qq","476777389");
		jedis.incr("age"); //进行加1操作
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
		
		/**
		 * 2 操作map：hmset hmget
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user",map);
		///参数1：map的key，参数2及其之后：map中的key，返回的是一个list，注意了
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println("2 Map操作：" + rsmap); 
		
		
		jedis.hdel("user","age");//删除map中的某个键值  
		System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
		System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
		System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true  
		System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  
		System.out.println(jedis.hvals("user"));//返回map对象中的所有value 
		
		/**
		 * 3 操作List：lpush rpush lrange
		 */
		//开始前，先移除所有的内容  
		jedis.del("java framework");  
		System.out.println("3 操作List：" + jedis.lrange("java framework",0,-1));  
		//先向key java framework中存放三条数据  
		jedis.lpush("java framework","spring");  
		jedis.lpush("java framework","struts");  
		jedis.lpush("java framework","hibernate");  
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
		System.out.println(jedis.lrange("java framework",0,-1)); 
		System.out.println("List长度：" + jedis.llen("java framework"));  
		jedis.del("java framework");
		jedis.rpush("java framework","spring");  
		jedis.rpush("java framework","struts");  
		jedis.rpush("java framework","hibernate"); 
		System.out.println(jedis.lrange("java framework",0,-1));
		
		/**
		 * 4 操作Set：sadd smembers
		 * 
		 */
		 //添加  
		String key = "set-key";
		jedis.sadd(key,"liuling");  
		jedis.sadd(key,"xinxin");  
		jedis.sadd(key,"ling");  
		jedis.sadd(key,"zhangxinxin");
		jedis.sadd(key,"who");  
		System.out.println("4 操作Set：");
		jedis.srem(key,"who");  //移除noname  
		System.out.println(jedis.smembers(key));//获取所有加入的value  
		System.out.println(jedis.sismember(key, "who"));//判断 who 是否是user集合的元素  
		System.out.println(jedis.srandmember(key));  
		System.out.println(jedis.scard(key));//返回集合的元素个数  
		
		/**
		 * 5 排序
		 */
		//jedis 排序  
		//注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
		jedis.del("a");//先清除数据，再加入数据进行测试  
		jedis.rpush("a", "1");  
		jedis.lpush("a","6");  
		jedis.lpush("a","3");  
		jedis.lpush("a","9");  
		System.out.println("5 排序：");
		System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]  
		System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果  
		System.out.println(jedis.lrange("a",0,-1));  
	}
	
	/**
	 * 对key的操作：
	 * 是否存在：jedis.exists("user")
	 * 
	 * 对值的操作：
	 * jedis.incr(key)：加1，只对整型有效，否则抛出异常
	 * 
	 * 对Map的操作：
	 * 删除map中的某个键值  jedis.hdel("user","age");
	 * 
	 * 对List的操作：
	 */
}
