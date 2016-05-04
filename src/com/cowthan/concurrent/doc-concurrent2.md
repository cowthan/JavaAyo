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


第八课  共享受限资源
============================
什么时候会出现共享受限资源的冲突？
有一份数据摆在这里，多个worker线程都对其进行修改，状态就可能会乱了
如果只有两个线程，一个读，一个写，根本就没事，也他妈不一定

总之，每次访问一个资源时，从进去到出来，都要保证数据的一致性

基本上所有保护共享受限资源的方法，都是序列化对受限资源的访问（同步化），也就是程序到这里就变成并行了，加个锁保证同时只有一个线程访问，这种机制就叫互斥量



##原子类

原子操作被用来写无锁的代码，避免同步

原子操作不是同步化的，而是避免了同步化：
——普通的运算操作，如果要依赖原子性，要谨慎使用，至少编程思想里不推荐的，除非非常懂JVM，能编写JVM，编程思想就是这个意思
——但是可以使用Atom系列类来保证安全

有两部分内容：
——普通的运算操作的原子性，如加减乘除，这个很难搞懂
——Atom系列类，提供了一套原子操作，基本还是有保障的

####（1） 原子操作

普通运算的原子性：暂时不做研究了
x++不是原子操作
+-*/也不是原子操作
x = x + 1  =也不是原子操作

想了解更多，再看一遍编程思想

#### （2）原子操作和Volatile

没整明白

#### （3）原子类

原子类是可以信赖的，可以用来做性能调优，避免写同步代码，避免序列化访问资源

例子在c8包下的类，用了个AtomicInteger


原子操作就是一步能完成的操作：
AtomicInteger currentEvenValue = new AtomicInteger(0);
return currentEvenValue.addAndGet(2);  //这里给value增加了2，并返回其值

* 注意：
    * 说是原子操作被用来构建Concurrent包，不建议你用
    * 用了原子操作，就省了很多加锁操作

* 都由什么
	* AtomicInteger
	* AtomicLong
	* AtomicReference


##Synchronized临界区

例子： c8包里的SynchronizedEventGenerator

public synchronized int next() {
	++currentEvenValue; // Danger point here!
	++currentEvenValue;
	return currentEvenValue;
}

相当于：
public int next() {
	synchronized (this) {
		++currentEvenValue; 
		++currentEvenValue;
		return currentEvenValue;
	}
}

* synchronized的锁始终是加在一个对象上
    * 直接修饰一个方法时，就是this
    * 如果多个对象访问同一资源，锁就得加到一个外部的静态对象上
    * 作用于静态方法/属性时，锁住的是存在于永久的Class对象

* synchronized的原理：
    * 每个object对象都有一个内置的锁
    * 所有对象都自动含有单一的锁，JVM负责跟踪对象被加锁的次数
    * 在任务（线程）第一次给对象加锁的时候， 计数变为1
    * 每当这个相同的任务（线程）在此对象上获得锁时，计数会递增
    * 只有首先获得锁的任务（线程）才能继续获取该对象上的多个锁
    * 每当任务离开时，计数递减，当计数为0的时候，锁被完全释放
    * 在HotSpot中JVM实现中，锁有个专门的名字：对象监视器
    * 更深入的讲：
    * 当多个线程同时请求某个对象监视器时，对象监视器会设置几种状态用来区分请求的线程
    * Contention List：所有请求锁的线程将被首先放置到该竞争队列，是个虚拟队列，不是实际的Queue的数据结构
    * Entry List：EntryList与ContentionList逻辑上同属等待队列，ContentionList会被线程并发访问，为了降低对 ContentionList队尾的争用，而建立EntryList
    * Contention List中那些有资格成为候选人的线程被移到Entry List 
    * Wait Set：那些调用wait方法被阻塞的线程被放置到Wait Set
    * OnDeck：任何时刻最多只能有一个线程正在竞争锁，该线程称为OnDeck
  
  
########

wait,notify和synchronized
    

##锁


例子： c8包里的MutexEventGenerator

包java.util.concurrent.locks.*

private Lock lock = new ReentrantLock();

public int next() {
	lock.lock();
	try {
		++currentEvenValue;
		Thread.yield(); // Cause failure faster
		++currentEvenValue;
		return currentEvenValue;
	} finally {
		lock.unlock();
	}
}

比synchronized多了什么特性：
——可以尝试获取锁，不必非得阻塞在这
——提供了比synchronized更细粒度的控制
——在实现链表遍历节点时，有个节点传递的加锁机制（锁耦合），在释放这个节点的锁之前，必须捕获下个节点的锁

例子：c8下AttemptLocking.java

boolean captured = lock.tryLock();//不会阻塞，不管有没有得到锁，都往下执行
captured = lock.tryLock(2, TimeUnit.SECONDS); //会阻塞2秒，然后不管有没有得到锁，都往下执行


public void untimed() {
	boolean captured = lock.tryLock();
	try {
		System.out.println("tryLock(): " + captured);
	} finally {
		if (captured)
			lock.unlock();
	}
}

public void timed() {
	boolean captured = false;
	try {
		captured = lock.tryLock(2, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
	try {
		System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
	} finally {
		if (captured)
			lock.unlock();
	}
}


##信号（Semaphore）




第九课  线程本地存储
============================




第十课  终结任务
============================

##1 任务的自我检查

有些任务会在循环中检查状态值，如canceled之类的，会自己退出任务
但有时我们需要任务更突然的终止任务

FutureTask提供的取消应该就是这种情况

##2 阻塞时终止

任务除了自我检查状态，也可能阻塞在sleep中，此时可能也需要将其终结

线程的状态：
——new：已经创建完毕，且已经start，资源分配完毕，等待分配时间片了，这个状态只会持续很短的时间，下一步就会进入运行或者阻塞
——run：就绪状态，只要给了时间片，就会运行，在任一时刻，thread可能运行也可能不运行
——block：阻塞状态，程序本身能够运行，但有个条件阻止了它运行，调度器会忽略这个线程，直到跳出阻塞条件，重新进入就绪状态
——dead：run()方法返回，或者被中断

都哪些方式可以进入block状态：
——sleep：等时间到
——wait：等notify
——等待IO
——等待synchronized锁
——等待Lock锁
——


阻塞时终结
中断



第十一课 线程通信，线程协作
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


第十二课 Volatile
============================

http://www.cnblogs.com/MOBIN/p/5407965.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io

Volatile和原子性没什么关系
如果变量被同步代码保护了，就不必考虑volatile

怎么引出这个问题呢

public abstract class IntGenerator {
  private volatile boolean canceled = false;  
  public abstract int next();
  // Allow this to be canceled:
  public void cancel() { canceled = true; }
  public boolean isCanceled() { return canceled; }
} 

* 分析
    * 看这个类的canceled字段，在这里一个IntGenerator对象可以被多个EventChecker对象调用cancel()
    * 这样就在每个EventChecker的线程里，保留了一份对canceled的本地缓存，这个本地缓存可能是每个CPU一个
    * 在每个线程里调用修改cenceled的值，首先会保存到本地缓存，没有同步到主存里
    * 但是其他线程通过isCanceled()读取它的值，不管从本地缓存读，还是从主存里读，都没被改变
    * 所以用volatile来修饰，保证每次对它的修改，都会同步到主存，据说读取操作就是发生在缓存里
    * 整了半天，还是挺麻烦，推荐首选用同步来解决问题，volatile适用于只有一个字段可变的情况


第十三课 死锁
============================



第十四课 
============================




第十五课 示例
============================
