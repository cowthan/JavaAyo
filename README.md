# JavaTest
java的测试项目，可以看做是java的ApiDemo，主要用于学习，也经常用来测试极光，七牛等服务器api，
实际上有什么好代码都可以加进来，直到这个工程不堪其重
----------------------------------

	

目录：
================

* 目录
	* [java高级]
		* [Ayo库]
		* 集合
			* [JDK集合](./doc/doc-collections.md)
			* [Guava集合]()
			* ConcurrentMap原理：https://github.com/edagarli/java-route/blob/master/concurrenthashmap_de_shi_xian_yuan_li.md?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* 并发
			* [并发-1](./doc/doc-concurrent.md)
			* [并发-2](./doc/doc-concurrent2.md)
			* [并发-3](./doc/doc-concurrent3.md)
			* [ThreadLocal](./doc/doc-threadlocal.md)
		    * java并发编程--Executor框架(一)  http://www.cnblogs.com/MOBIN/p/5436482.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		    * 怎么理解Condition http://www.importnew.com/19666.html
			* JAVA细粒度锁实现的几种方式: http://toutiao.io/posts/srrm8v
		* NIO,IO 
			* [nio-1](./doc/doc-nio.md)
			* [okio](./doc/doc-nio2-okio.md)
		* 注解
			* [笔记]
		    * dagger2：http://toutiao.io/posts/vahxj5
			* Retrofit和ButterKnife的注解怎么实现
		* 反射
			* JOOR
		* [ClassLoader](./doc/doc-classloader.md)
		    * [ClassLoader==>字节码，apk]
		* Socket==>openfire==>netty
		* [对象管理](./doc/doc-effective-object-management.md)
		* 设计模式：都在源码里
		* 更好的使用java：http://blog.smoker.cc/translation/20160511.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io		 
	    * [Json]
	    	* Gson：http://www.jianshu.com/p/e740196225a4#?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    * [xml==>webservice]
	    * [Rxjava]
	    	* http://www.wangchenlong.org/2016/03/20/1603/207-rxjava-first/?f=tt&hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* http://www.easydone.cn/2016/03/29/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* http://toutiao.io/posts/guri0a
	    * [Okhttp]
	    	* https://segmentfault.com/a/1190000004148636?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* http://www.jianshu.com/p/3141d4e46240?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* http://toutiao.io/posts/cmbhv8
	    * [Retrofit]
	    	* http://www.jianshu.com/p/45cb536be2f4?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* http://toutiao.io/posts/jf391t
	    	* http://gank.io/post/56e80c2c677659311bed9841?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    * [Guava]
	    * [数据库访问：原生==>nutz==>其他]
	    	* [数据库优化]
	    * [java GUI]
	    	* [twinkle]
	    	* [jcommunique]
	    * [java OpenGL：JOGL](http://blog.csdn.net/sidihuo/article/details/44034465)
	    * [java cocos2d：CDK]
	    	* cdk：(http://www.tuicool.com/articles/NNJbyu)
	    	* cdk2：(https://github.com/makeapp/cocoseditor-java-samples)
	    	* gdx版
	    * [redis]
	    	* http://www.importnew.com/19321.html
	    * [推送]
	    	* Android推送平台调研报告： http://www.jianshu.com/p/d650d02a1c7a?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    * jsoup
	    	* https://jsoup.org/apidocs/org/jsoup/select/Selector.html
	    * 异常：
	    	* http://lrwinx.github.io/2016/04/28/%E5%A6%82%E4%BD%95%E4%BC%98%E9%9B%85%E7%9A%84%E8%AE%BE%E8%AE%A1java%E5%BC%82%E5%B8%B8/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	    	* 11
	* [套路：Effective Java笔记]
		* [对象管理：创建和销毁](./doc/doc-effective-object-management.md)
		* 垃圾收集：http://www.wxtlife.com/2016/04/25/java-jvm-gc/
		* 安卓跨进程单例：https://mp.weixin.qq.com/s?__biz=MzI1NjEwMTM4OA==&mid=2651231757&idx=1&sn=2b881142e851264d7c44ce6dbca51f59&scene=1&srcid=0427RCeIg99OyeMLCF5646KZ&key=b28b03434249256bf33cda558e6748eb9b28ad7caaea7573280436b84839e1f7a3cc9218de4130d7ed69d9ea975e3238&ascene=0&uin=NTkyMjI3NQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=xoE7jU883QbJlGggF7yDrkyVciTuIdC6cWJXbH4ZzV8%3D
		* 安卓的enum到底占多少内存：http://toutiao.io/posts/se32l4
	* [工具]
		* [命令行]
		* [csv读写]
		* [java环境变量设置：windows]
		* [gif生成和拆分]
		* [exif读写]
		* [keystore分析和签名]
		* [加密解密](http://tool.oschina.net/encrypt)
	* [算法学习]
		* [排序]
		* [先选本教材]
		* 自己实现超小编译器：https://github.com/1c7/the-super-tiny-compiler?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	* [设计模式]
		* [pattern1包里]
	* [第三方]
		* [极光：推送]
		* [七牛：文件云存储]
		* [微信开放平台]
		* [微信公众号]
		* [微博]
	* [项目用]
		* [CodeWorld]
	* [安卓]
		* 性能调优：
			* http://mp.weixin.qq.com/s?__biz=MzA4NTQwNDcyMA==&mid=2650661382&idx=1&sn=8cd37062b5a67bb60db48aa1fe72b5a9&scene=0#wechat_redirect
			* 微信为什么这么省流量：http://www.habadog.com/2015/10/23/wechat-use-less-data/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* 后台保活
			* http://mp.weixin.qq.com/s?__biz=MzA3ODg4MDk0Ng==&mid=403254393&idx=1&sn=8dc0e3a03031177777b5a5876cb210cc&scene=0#wechat_redirect
			* http://toutiao.com/a6268596289538998529/?tt_from=mobile_qq&utm_campaign=client_share&app=news_article&utm_source=mobile_qq&iid=3823768336&utm_medium=toutiao_ios
			* http://www.jianshu.com/p/63aafe3c12af
		* 适配
			* http://mp.weixin.qq.com/s?__biz=MzA4MjU5NTY0NA==&mid=404220168&idx=1&sn=8a05266b5b7a5a886db3cf881d9f2edb&scene=0#wechat_redirect
		* apk优化：
			* http://jayfeng.com/2016/03/01/Android-APP%E7%BB%88%E6%9E%81%E7%98%A6%E8%BA%AB%E6%8C%87%E5%8D%97/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* 分析apk：http://toutiao.com/i6256184508563849730/?tt_from=mobile_qq&utm_campaign=client_share&ADSESSION=1463657200&app=news_article&utm_source=mobile_qq&ADUIN=279800561&iid=3521906016&utm_medium=toutiao_ios&ADPUBNO=26510&ADTAG=CLIENT.QQ.5431_.0
			* studio 2.2页支持apk解析，估计就是classyshark
		* 其他：不应该在这
			* webview：http://www.jianshu.com/p/3fcf8ba18d7f?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* 一个底部导航栏：http://www.jianshu.com/p/8e6b75e11a3d?utm_campaign=maleskine&utm_content=note&utm_medium=mobile_a
			* 从LinearLayout源码讲起：http://www.jianshu.com/p/f9b9f05222a8?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* 换肤：http://www.jianshu.com/p/af7c0585dd5b?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* Scroll解析：https://segmentfault.com/a/1190000004982933?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* MotionEvent：http://ztelur.github.io/2016/03/16/Android-MotionEvent%E8%AF%A6%E8%A7%A3/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* ViewDraggerHelper：http://www.jianshu.com/p/0372488cebcf?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* 动画1：http://p.codekk.com/blogs/detail/559623d8d6459ae793499787
			* Fragment：http://m.blog.csdn.net/article/details?id=51435236
			* 混淆：http://www.jianshu.com/p/f3455ecaa56e?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
			* 仿微博弹出：http://m.myexception.cn/android/1880112.html
			* RecyclerView：添加header和footer，https://github.com/blipinsk/RecyclerViewHeader
			* 滑动显示和隐藏顶部底部栏：https://github.com/NashLegend/Auto-Hide-ListView
			* recyclerview
				* header和footer：参考XRecycleView的实现，LinearLayoutManager不影响，GridLayout控制span，其他不支持，Adapter被wrap一下以返回header和footer，position和下标不对应了
				* 下拉：参考ultra，下拉的效果整理
				* 上拉：自己实现
				* type分组：Grid的type怎么支持呢
				* sticky + section index：怎么搞呢
				* swipe：在Item上就行，在Item上操作的都不是问题
				* drag：不会
				* 这些功能怎么组织
					* 标准模板：header，footer，上拉，下拉，type，
					* sticky模板
				* 类组织：
					* 基类：StatusUI,配合StatusUIManager，控制status，提供onloadok，fail，refresh接口，缓存，状态切换--控制刷新，停止刷新等
						* 甚至还需要考虑加载过程是否可提示进度，横条还是圈
						* 状态提供回调，可以同时控制外部
					* 一级功能类
						* 考虑界面如何填充，如列表，详情等，这里可以决定放什么content view
						* 也就列表可以提前清楚数据怎么填充，其他都是具体业务类了啊
						* 注意，到这一层，都还可以算是MVP的V层
					* 实现类：
						* 主要是P层的功能了
						* 考虑数据怎么获取
						* 可以添加上拉，下拉，自动刷新等操作了
						* Recycler列表，带上拉，下拉，header，footer等
						* sticky recycler列表
						* 
	* [其他]
		* [markdown语法](./README)
		* github静态网站：https://segmentfault.com/a/1190000002765287
		* pandoc：markdown转换--https://github.com/phodal/fullstack-toolbox/blob/master/documents.md?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* Graphviz：流程图--https://github.com/phodal/fullstack-toolbox/blob/master/documents.md?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* 图片转换：ImageMagick--https://github.com/phodal/fullstack-toolbox/blob/master/documents.md?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* Gitbook：markdown文档们转成静态网站
		* java开发小工具集合：http://www.hollischuang.com/archives/1459?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	* 架构师
		* http://mp.weixin.qq.com/s?__biz=MzA4NTU2MTg3MQ==&mid=407250191&idx=1&sn=abbda20219694844554d8318fba23693&scene=0#wechat_redirect
		* http://blog.zhaiyifan.cn/2016/01/29/android-app-architecture-2015/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* 安卓项目基本规则：http://www.jianshu.com/p/d9e4ddd1c530
	* web的高并发，数据库优化，中间件，规范等等等等
		* http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=410775314&idx=1&sn=7c7cc94f8f42c6df81b721919593f1c2&scene=0#wechat_redirect
		* http://mp.weixin.qq.com/s?__biz=MzAwMDU1MTE1OQ==&mid=404669856&idx=1&sn=fd2856ac13112416833e41093f66735c&scene=1&srcid=0318Nke8n2DezrrSWlg7Mqct&from=groupmessage&isappinstalled=0#wechat_redirect
		* https://zhuanlan.zhihu.com/p/20034107?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* 读写分离：http://blog.jobbole.com/100081/
		* http://toutiao.io/posts/95qo0b
		* http://www.importnew.com/19413.html
		* 陌陌feed流，流量过亿：http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=2659597071&idx=1&sn=cd8df9f8c52dfbfb54e65adbe19fae27&scene=0#wechat_redirect
		* 所有可插入head标签的东西：https://github.com/joshbuchea/HEAD?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* http://wetest.qq.com/lab/view/?id=80?from=ads_test2_qqtips&sessionUserType=BFT.PARAMS.190890.TASKID&ADUIN=345766454&ADSESSION=1463734346&ADTAG=CLIENT.QQ.5473_.0&ADPUBNO=26569
	* git：
		* http://leoray.leanote.com/post/git?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
		* https://ourai.ws/posts/working-with-git-in-team/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
================================


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
 
 
 
 
