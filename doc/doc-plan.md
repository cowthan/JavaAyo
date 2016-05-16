1 netty


2 nio深入



3 并发深入


4 jvm


* tools.jar
    * 默认不引入，引入就说明必须得用sun的HotSpot虚拟机，也可以显式的引入jar包
	* 里面是一些强效的工具，可以监控程序运行
	* 并且大部分功能和jdk的命令行（bin下）工具对应，其实就是对tools.jar的封装
	* 所以这个jar包下的东西还是可以研究研究的
	* 具体参考深入理解JVM的77页
	
![](./img/11111.png)


5 Classloader

OSGi

6 字节码生成

动态代理

javac命令就是字节码生成技术的老祖宗

CGLib，Javassist，ASM




空过：
JVM第4章：java调优工具，tools.jar包
JVM第5章：几个案例
JVM第10章：编译期优化，早期优化
JVM第11章：运行期优化，晚期优化

JVM 12章：内存模型和线程
JVM 13章：线程安全和锁优化