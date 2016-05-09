# JavaTest
java的测试项目，可以看做是java的ApiDemo，主要用于学习，也经常用来测试极光，七牛等服务器api，
实际上有什么好代码都可以加进来，直到这个工程不堪其重
----------------------------------

src==>android：这里是仿安卓系统的东西，学习安卓原理用，注意，本工程里引入了android-4.1.1.4，忘了给okhttp还是rxjava还是retrofit用的了，自己仿安卓代码时，可能有些类你没写，但已经引进来了，所以建议不要使用原生安卓包名

Looper和Handler高仿

==================================

alert，toast风格的提示框：
src-twinkle：一个基于swing的桌面版的notify框
src-jcommunique：也是一个基于swing的桌面版的notify框

一个平台无关的UI库：BeautyEye
https://github.com/JackJiang2011/beautyeye
好像还可以构件安卓界面，希望你思想也是仿安卓的
——比想象的差

SmartInvoke，也是一个UI库，好像还提供了更多访问硬件的功能
http://www.oschina.net/p/invokeui
——好像是给flash用的

FengGUI，基于OpenGL的GUI框架，可以学习OpenGL用
http://www.oschina.net/p/fenggui
——没看

==================================

src==>com.cowthan：学习用，小工具，或者对一些东西的测试，如极光，加密解密，csv读写等，设计模式和排序算法的学习笔记也在里面
对应的lib应该在lib-tools下找

部分测试代码放到了src-test下，包括nutz，qiniu，retrofit，rxjava，这里的代码就截止于2016/5/3，不再往里加了，都应该放到com.cowthan下



1 csv读写

配合QQ手机助手，备份通讯录

忘了基于哪个jar包

2 极光

里面有美滴滴的推送测试

jar包：
jpush-client-3.2.3.jar
不知道还需不需要依赖于别的jar包啊

3 rsa

公钥加密，私钥解密

和php服务器配合时，php的加密解密有个100多位字符的限制，所以需要把字符串拆成几分，各自加密解密

这里我觉得应该整理一下各个支付平台的key配置，和测试demo

jar包：
org.apache.commons.lang.jar
sun.misc.BASE64Decoder.jar


4 sort

排序算法学习



5 pattern

设计模式学习

6 keystore

给apk签名
获取keystore的签名


7 json

json的学习，FastJson，Gson各种库的对比，和通用套路


8 cmd

java执行命令行

9 image

Exif读写
gif和jpeg互转

jar包：
jimi.jar：gif互转
Exif读写：metadata-extractor-2.7.0.jar

10 socket

socket学习，非nio的

例子1：
一个聊天室

客户端是JFrame小界面，以applet形式提供
可以作为桌面应用，也可以嵌入到网页里


服务器端就是单纯命令行，没啥，写的还挺好


好像没用到nio，如果要学习nio，可以从这个开始


WebContent配合ChatServlet用，如果要用，请做成web项目发布


11 netty

netty学习，netty是基于nio的库

可以实现各种协议，如http，TCP，UDP，WebSocket

nio干什么用：用实时性换来吞吐量，用一个线程里的轮询，换来一台主机可以管理更多连接，轮询的谁？轮询的连接，实时性怎么损失了？轮询啊，连接2有消息了，不能实时发现，必须等轮询到它，
不nio会怎么样？不nio就得用一个线程管理一个连接，一台主机又能开几个线程呢
既然是轮询所有连接，那每个单独的连接，就不能阻塞，所以叫nio

12 codeworld

代码屋的服务器端

13 nio

nio学习

应该基于share service

14 concurrent

并行库学习



====================================

src==>org.ayo：通用库，这里的代码应该保持干净，应该能直接被别的工程引入，或者直接拷到别的地方用，
这里有些库可能代码太多，就会单独开一个目录

* 现有的
    * 常用类 ：
        * Ayo：相当于Lang
        * JsonUtils：用的是Fastjson
        * 集合相关：Lists，Maps，OnWalk
        * 字符串相关：Strings
    * IO相关
        * Files：管理文件读写，路径获取，流操作
    * RxJava：
    	* src-rxjava
    * Http相关：
    	* src-okhttp3
    	* src-okhttp3-ws
    	* src-retrofit
    	* 当然也有Rxjava的依赖，这几个库官方的sample都拷过来了
    * DB的orm库
        * 用的是nutz，在src-nutz下
	* 日志
	    * 调试日志：JLog，以json打印所有log
	* 七牛相关
	    * 因为七牛对我来说经常使用，所以放到通用库里，七牛唯一的问题是现在的版本基于okhttp2，但这里学的是okhttp3
	    * org.ayo.qiniu是对七牛官方sdk的封装，官方代码在src-qiniu
	    * src-qiniuz下有个test，这是以前对七牛的封装，有代码用，但以后不要再用了
	    
	    
====================================

## 1 nutz轻量级web mvc

在spring盛行的年代，一些个人小项目可以尝试一些其他的轻量级框架，nutz就是其中比较清新脱俗的一个  

代码我已经不记得是哪个版本的了，拷过来就是为了研究，当然里面也有一些工具类，可以一用


* 这里代码好像被我精简了，还剩的功能：
    * java语法糖
    * 数据库操作，orm的，这个是主打
    * 其他一些工具类，应该仔细研究研究，基于这个做一个纯java版工具库，以后和spring也能完美兼容
	* 配合netty写个http服务器的话，这个工程本身就可以转为一个web工程了
	
	

* 后期还要添加的功能，虽然我已经心有余力不足
    * 安卓模板代码生成
    * 安卓xml生成
    * json转bean
    * 接口调试，swagger



## 2 okhttp2

jar包引了个2.6.0，主要是给七牛用  

我自己也写了个测试类，只不过还没开始就结束了，所以说okhttp我根本没好好学过

## 3 okhttp3

jar包引了个3.2.0  

ok2和3的出入还是比较大的，3应该作为学习的重点  

3也是为了okhttputils和okhttpserver这两个工具库存在的，这两个库很好，不过是依赖于安卓的，  

所以你能看到代码报错，但只是线程方面依赖于安卓，可以想办法转为普通java工程，毕竟在这里  

测试是比较方便的

## 4 src-android的野心就比较大了

是为了写一套高仿安卓framework的代码，纯粹是为了没事写着玩，当然，有空的可能性不大

## 5 src-socket

一个聊天室，一个线程管理socket，没有用nio，可以学一学，改成nio版的，吞吐量增加，你就学到了  

另外，通过这个你也可以学一学java桌面应用的开发，没事写个简单的小工具什么的都行


## 6 src-netty

netty学习笔记，还没开始学


## 7 design-pattern

设计模式学习代码


## 8 rxjava

测试demo


## 9 retrofit

学习示例

## 10 sort

一堆排序算法的实现

## 11 rsa

加密解密相关

## 12 jpush

极光后台，配置一下key，就可以测推送

## 13 src-qiniu

七牛的java接口，可以控制你的七牛空间


## 14 其他

* 此外还有：
    * redis的java sdk
    * mqtt相关
    * guava相关
    * ClassyShark：apk反编译，谷歌官方出品，亲测good
    * 安卓性能分析工具
    * 安卓apk解析工具
    * 安卓keystore文件解析工具
    * java并发终极学习
    
 #####
 
 ## 15 jar包整理
 
 
 
 
