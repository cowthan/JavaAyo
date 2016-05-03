package com.ayo.nutzdb;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("t_person")   // 声明了Person对象的数据表
public class Person { //无需继承任何Nutz的类

    @Id       // 表示该字段为一个自增长的Id,注意,是数据库表中自增!!
    private int id;

    @Name    // 表示该字段可以用来标识此对象，或者是字符型主键，或者是唯一性约束
    private String name;

    @Column(value = "age")	  // 表示该对象属性可以映射到数据库里作为一个字段
    private int age2;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age2;
    }

    public void setAge(int age) {
        this.age2 = age;
    }


}
