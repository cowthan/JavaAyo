第七课  join
============================

见C7中的例子

想join到线程t，就得调用t.join()，这个方法类似sleep，会阻塞在这里，也可以interrupt

join()：挂起当前线程，等待目标线程， t.join()，这里t是目标线程
join(millis，nano)：超时参数，如果过了超时时间还是没等到，join就强制返回

sleeper和guest，guest需要在sleeper的线程对象上join，即sleeper.join()
直到sleeper的run方法返回，线程执行完毕，才会激活join，guest才退出阻塞，继续往下执行
sleeper结束时，sleeper.isAlive()为false

一个线程可以join到其他多个线程上，等到都结束了才继续执行

有类似需求，可以考虑CyclicBarrier，栅栏，可能比join更合适


第八课  共享受限资源：原子类
============================
什么时候会出现共享受限资源的冲突？
有一份数据摆在这里，多个worker线程都对其进行修改，状态就可能会乱了
如果只有两个线程，一个读，一个写，根本就没事，也他妈不一定

总之，每次访问一个资源时，从进去到出来，都要保证数据的一致性




第九课  共享受限资源：Synchronized临界区
============================



第十课  共享受限资源：锁和信号（Semaphore）
============================




第十一课  线程本地存储
============================




第十二课  终结任务
============================

阻塞时终结
中断



第十三课 线程通信，线程协作
==========================

sleep和yield算是协作，我让你让大家让，但太底层，而且顺序根本不可控，完全不能依赖
join算是协作，等待嘛
wait和notify，算是第一次出现的像样的协作
队列
Piper

生产者和消费者模型
——wait和notify版
——队列模型

----java.utis.concurrent中的构件----
CoundDownLatch
CyclicBarrier
DelayQueue
PriorityBlockingQueue
Exchanger


第十四课 Volatile
============================

http://www.cnblogs.com/MOBIN/p/5407965.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io


第十五课 死锁
============================



第十六课 
============================




第十七课 示例
============================
