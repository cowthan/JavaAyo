package com.ayo.nutzdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.EntityMaker;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.EntityHolder;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.jdbc.JdbcExpert;

public class DBTest {
	
	public static void main(String[] args) {
		
		/*
 前提：
1 有个数据库：
CREATE database if not exists nutz_test CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use nutz_test;
 
2 有个表：
CREATE TABLE t_person (
 id  SERIAL PRIMARY KEY,
 name  VARCHAR(50) NOT NULL UNIQUE,
 age INT
)engine=innodb default charset=utf8 auto_increment=1;
 
3 定义POJO，注意注解的用法：
 
 类级注解：表相关
@Table("t_person")  声明对应的表
@PK( {"masterId", "petId"} )  复合主键，其中masterId和petId两个字段不需要其他的注解来标识是列名了
 
 字段级注解：列相关

主键：不需要再单独用column指定
@Id       表示该字段为一个自增长的Id,注意,是数据库表中自增!!
@Id(auto=false) 不是自增长的id 

@Name
@Name(casesensitive=false)  忽略大小写

普通列：
@Column

其他的字段，不被认为是数据库列
		 
		 */
		
		org.apache.commons.dbcp.BasicDataSource dataSource = new org.apache.commons.dbcp.BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/tieta1?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setMaxActive(50);
		dataSource.setMaxIdle(30);
		dataSource.setMaxWait(1000);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(-1);
		
		/**
		 * 1 使用Dao和NutDao类
		 * 
		 * 提供了基本的增删改查，建表
		 * 
		 * 
		 *  插入	Insert	一条 SQL 插入一条记录或者多条记录，id
			插入	FastInsert	一条 SQL ,通过batch插入多条记录
			
			删除	Delete	一条 SQL 删除一条记录
			清除	Clear	一条 SQL 根据条件删除多条记录

			更新	Update	一条 SQL 更新一条或者多条记录
			
			获取	Fetch	一条 SQL 获取一条记录
			查询	Query	一条 SQL 根据条件获取多条记录
		 */
		Dao dao = new NutDao(dataSource);
		Person p = new Person();
		p.setName("name-" + System.currentTimeMillis());
		p.setAge(20);
		dao.insert(p);
		System.out.println("新插入的id是：" + p.getId());
		
		/*
-----------先研究插入
dao.insert(obj);
1 参数可以是POJO，也可以是POJO的数组，Collection，Map ，即插入多个
2 对于每一个POJO，都会考虑两个事情：
——插入之前，会检查声明了 '@Prev(@SQL("SELECT ..."))' 的字段，预先执行 SQL 为字段设置。 
——插入之后，会检查声明了 '@Next(@SQL("SELECT ..."))' 的字段，通过执行 SQL 将值取回，对于自增的@Id，这个是默认行为，
即总是会取回新增的id

dao.fastInsert(obj)
1 基本和insert一样，只是@Prev，@Next不起作用，也不会自动查询select max(id)来获取新增的id

------------再研究where和order子句：对应Condition接口
因为其他的sql语句都接受where条件

Condition.toSql(org.nutz.dao.entity.Entity)由NutDao来调用，获取where和order的那个sql字符串
——注意，如果返回的字符串不以where开头，则会自动加上

怎么拼sql？
1 Cnd工具类：硬编码
Cnd.wrap("name LIKE 'J%' AND age>20");
Cnd.wrap("name LIKE 'J%' AND age>20 ORDER BY name ASC");

注意：这样写，如果数据库里字段名改了，这里的代码也要改，但是这样写对于生产环境来说，速度快

2 Cnd工具类：拼sql语句
Condition c = Cnd.where("age",">",30).and("name", "LIKE", "%K%").asc("name").desc("id");
对应：
WHERE age>30 AND name LIKE '%K%' ORDERBY name ASC, id DESC

注意这里的age：
——如果数据库和POJO里名字都是age，那就无所谓
——如果数据库里没有age，则找到POJO里字段名是name的成员，根据 @Column(value = "age_fuck")来找到
数据库里的age_fuck这一列
——如果数据库和POJO都找不到age，就报错

or的拼法：
SqlExpressionGroup e1 = Cnd.exps("name", "LIKE", "P%").and("age", ">", "20");
SqlExpressionGroup e2 = Cnd.exps("name", "LIKE", "S%").and("age", "<", "30");
Condition c = Cnd.where(e1).or(e2).asc("name");
对应：
WHERE (name LIKE 'P%' AND age>'20') OR (name LIKE 'S%' AND age<'30') ORDER BY name ASC

in的拼法：
Criteria cri = Cnd.cri();
cri.where().andIn("id", 3,4,5).andIn("name", "Peter", "Wendal", "Juqkai");
cri.where().andLT("id", 9);
cri.where().andLike("name", "%A%");
cri.getOrderBy().asc("name").desc("id");
List<MyObj> list = dao.query(MyObj.class, cri, null);
注意：
Criteria继承自接口Condition
LT : 小于 (LessThan)
GTE : 大于等于 (GreatThanEqual)
LTE : 小于等于 (LessThanEqual)
		
------------再研究分页
List<T> query(Class<T> classOfT, Condition condition, Pager pager);
这里的Pager就是分页
标准用法：
public QueryResult getPetList(Dao dao, int pageNumber, int pageSize){
    Pager pager = dao.createPager(pageNumber, pageSize);
    List<Pet> list = dao.query(Pet.class, null, pager);
    pager.setRecordCount(dao.count(Pet.class));
    return new QueryResult(list, pager);
}
——注意分页四大要素：
当前页数 -- 第几页
页大小 -- 每页有多少条记录
总页数 -- 一共多少页
总记录数 -- 如果不分页，一共有多少条记录

前两个决定的返回的数据
总页数决定了页面上显示的翻页按钮，这个是根据总记录数算出来
总记录数：自己设置进去，为什么，因为有时不用，所以让你自己决定设不设置


		------------update
		update哪些字段
		
		更新一条：全部字段
		int affectRow = dao.update(p);
		
		更新一条：指定字段，通过正则来匹配列名
		int affectRow = dao.update(p, "name|age|id");
		
		更新多条：
		dao.update(Person.class, Chain.make("age",35), Cnd.where("age",">",150));
		dao.update("表名", Chain.make("age",35), Cnd.where("age",">",150));
		
		其他的：
		dao.updateIgnoreNull(obj);
		dao.updateLinks(obj, regex);
		dao.updateRelation(classOfT, regex, chain, cnd);
		dao.updateWith(obj, regex);
		
		动态实体：
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(".table", "t_person");
		map.put("name", "abc");
		map.put("age", 18);
		dao.update(map, "age");
		注意：
		——这个会把t_person表中所有age字段设置为18
		——不是很灵活，没法设置Condition
		 	
		
		
		------------delete
		dao.delete(obj)  根据@Id, @Name, @PK来
		dao.delete(Person.class,"Peter");
		dao.delete(Person.class,2);
		dao.deletex(classOfT, pks);
		
		dao.clear(classOfT 或者 表名); 全删
		dao.clear(class, Condition); 按条件删
		
		其他的
		dao.deleteLinks(obj, regex);
		dao.deleteWith(obj, regex);
		
		------------fetch和query
		fetch：返回一条，如果有多条结果，则只返回第一条，可接受的参数：
		p = dao.fetch(Person.class, 1);   ///通过@Id的列来查，id为整型时使用
		p = dao.fetch(Person.class, "@Name");  ///通过@Name的列来查，id为字符串时使用
		p = dao.fetchx(Pet.class, 23, 12); //通过@PK( {"masterId", "petId"} )指定的列来查，注意顺序要一致
		p = dao.fetch(classOfT, Condition cnd);
		
		query：返回多条
		List<T> query(Class<T> classOfT, Condition condition, Pager pager);
		
		 */
		//修改：
//		p.setName("我擦嘞3");
//		p.setAge(40);
//		int affectRow = dao.update(p, "name|age|id");
		
		///查询：select * from table
		List<Person> list = dao.query(Person.class, Cnd.where("age2", ">=", 20));
		System.out.println("获取全部person，个数：" + list.size());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(".table", "t_person");
		map.put("name", "abc");
		map.put("age", 18);
		dao.update(map); 		
//		dao.update(Person.class, Chain.make("age","35"), Cnd.where("age",">",20));
//		dao.update(tableName, chain, cnd);
//		dao.updateIgnoreNull(obj);
//		dao.updateLinks(obj, regex);
//		dao.updateRelation(classOfT, regex, chain, cnd);
//		dao.updateWith(obj, regex);
		
		///查询：select * from table where id = 1;   根据标为@Id的字段来
		p = dao.fetch(Person.class, 1);
		System.out.println("获取第一条记录，id是" + p.getId());
		
		///查询：select * from table where name = 'name值'，其中，name是字符串类型的主键，用@Name标识
		p = dao.fetch(Person.class, list.get(0).getName());
		System.out.println("获取name为记录，id是" + p.getId());
		
		//dao.fastInsert(obj)
		
	}
	
}
