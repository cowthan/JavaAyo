注解
=============================

注解是元数据

##1 简介

###内置注解

提醒编译器的
@Override
@Deprecated
@SuppressWarnings 关闭警告


创建新注解的：下面说


###用法

啥也不说了


##2 自定义注解

`四大元注解`：

* @Target：注解能用在哪儿
    * @Target(ElementType.METHOD)
    * ElementType可能的值：
        * TYPE          用于class定义
        * CONSTRUCTOR   用于构造方法
        * METHOD        用于方法
        * FIELD         用于成员变量
        * LOCAL_VARIABLE 局部变量声明
        * PACKAGE       包声明
        * PARAMETER    方法参数声明
        * ANNOTATION_TYPE
        * TYPE_PARAMETER
        * TYPE_USE


* @Retention：注解信息在哪个阶段有效
    * @Retention(RetentionPolicy.RUNTIME)
    * RetentionPolicy可能的值：
        * SOURCE：源码阶段，编译时，一般是用来告诉编译器一些信息，将被编译器丢弃
        * CLASS：注解在class文件中可用，会被VM丢弃
        * RUNTIME：运行时，VM在运行时也保留注解，估计这个才能通过反射获取到


* Documented
	* 将次注解包含在JavaDoc中
	
* Inherited
	* 允许子类继承父类中的注解


`注解 处理器：通过反射拿到注解`





