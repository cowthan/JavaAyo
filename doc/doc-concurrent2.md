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

在当前线程c调用t.join()表示：
* c等待t执行完毕，期间c和t都可以被中断
* t必须是从c产生的线程


有类似需求，可以考虑CyclicBarrier，栅栏，可能比join更合适


第八课  共享受限资源
============================
什么时候会出现共享受限资源的冲突？
有一份数据摆在这里，多个worker线程都对其进行修改，状态就可能会乱了
如果只有两个线程，一个读，一个写，根本就没事，也他妈不一定

总之，每次访问一个资源时，从进去到出来，都要保证数据的一致性

基本上所有保护共享受限资源的方法，都是序列化对受限资源的访问（同步化），也就是程序到这里就变成并行了，加个锁保证同时只有一个线程访问，这种机制就叫互斥量

如果你改变一个对象的状态是一个复杂的过程：
* 这期间你最好保证不要出现任何被打断的可能


##原子类

原子操作被用来写无锁的代码，避免同步

原子操作不是同步化的，而是避免了同步化：
——普通的运算操作，如果要依赖原子性，要谨慎使用，至少编程思想里不推荐的，除非非常懂JVM，能编写JVM，编程思想就是这个意思
——但是可以使用Atom系列类来保证安全

有两部分内容：
——普通的运算操作的原子性，如加减乘除，这个很难搞懂，你知道a+b是不是原子操作？
——Atom系列类，提供了一套原子操作，基本还是有保障的

####（1） 原子操作

普通运算的原子性：暂时不做研究了
a++不是原子操作
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

* 都有什么
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
——synchronized引起的阻塞无法被interrupt方法中断，但ReentrantLock提供了可以被中断的机制
——ReentrantLock.lockInterruptly()：如果得不到锁（被其他地方占用），就会阻塞，但是这个阻塞可以被interrupt()

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

public static class ThreadLocalVariableHolder{
		
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
		private Random rand = new Random(47);
		protected synchronized Integer initialValue(){
			return rand.nextInt(1000);
		}
	};
	
	public static void increment(){
		value.set(value.get() + 1);
	}
	
	public static int get(){
		return value.get();
	}
}

虽然只有一个ThreadLocal<Integer> value的静态变量，而且看起来好像只能放进一个int值，
但实际上每个访问这个value对象的线程，都有了自己的一份拷贝，互不干扰。
所以用起来好像是一个Map，而且key是线程id之类的


第十课  终结任务
============================

##1 任务的自我检查

有些任务会在循环中检查状态值，如canceled之类的，会自己退出任务
但有时我们需要任务更突然的终止任务

注意：如果有标志位canceled，isRunning等，这个一般是volatile的

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



另外，如下的大循环也可以interrupt
while(!Thread.currentThread().isInterrupted()){
	ThreadLocalVariableHolder.increment();
	System.out.println(this);
	Thread.yield();
}

终结情形1：

//注意isCanceled是个boolean，而且一般会需要volatile修饰
public void run(){
	while(!isCanceled){
		//do some seriers work
	}
}

终结情形2：
//thread.interrupt()可以打断
//Executors得不到thread的引用，只能通过ExecutorService.shutdownNow()来打断
//如果能拿到Future，可以Future.cancel(true)来打断
//exec.execute(Runnable)看来是打断不了了，因为拿不到什么引用
//exec.submit()，还是能打断的，返回了Future
//本质上都是调用了thread.interrupt()

public void run(){
	while(!Thread.currentThread().isInterrupt()){   //或者用Thread.interrupted()判断
		//do some seriers work
	}
}

public void run(){
	while(true){
		Thread.sleep(1000);  //被interrupt会抛出异常，因为既然是阻塞，被意外终止，异常看似挺合理
		//do some seriers work
	}
}

终结情形3：终结不了
在等待synchronized的线程，不可以被interrupt
但是注意，Lock可以尝试获取锁，并可以指定阻塞等待锁的时间限制

终结情形4：ReentrantLock.lockInterruptly()
ReentrantLock.lockInterruptly()，在这里获取不到锁，会阻塞，但是可以被interrupt方法中断

看例子c10.Interrupting2类

终结情形5：IO密集型阻塞
在等待InputStream.read()的线程，不可以被interrupt
但是有个笨办法：关闭线程正在等待的底层IO资源，如关闭Socket
还有个更好的选择：nio，提供了更人性化的中断，被阻塞的nio通道会自动响应中断


注意：
1 如果一个Runnable没有cancel类的标志位检查，也没有检查isInterrupt()，调用interrupt()会怎么地？
    * 参考c10.NothingToInterrupt，是关不掉的，但这种形式的无限循环，一般不会出现在真实场景里
    * 真实场景什么样？一般是一个无限循环，但里面会阻塞（等待条件成熟），有跳出条件
2 线程被中断之后，会发异常InterrupttedException，有时需要清理资源，参考类c10.InterruptingIdiom
	


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

-----------------------

程序可能在某个地方必须等待另一个线程完成任务，如果用无限循环来检查，这叫忙等待，很耗CPU


##1 wait和notify，notifyAll


###（1）简介
obj.wait()和obj.notify()的作用：
——wait释放obj上的锁，所以必须先持有锁了，通过synchronized
——程序在这里开始阻塞，发出的信息就是：我在obj上等待，释放了obj的锁，并且等待notify
——别的程序此时可以拿到obj上的锁了
——notify也会先释放obj的锁，所以也得先拿到锁，obj.notify()会通知在obj上wait的对象
——此时wait的地方会再拿到锁，继续往下执行

c11.Test类，总结：
要从wait中恢复，也就是让wait返回，必须满足两个条件：
——有人在同一个对象上notify过
——同一对象的锁被释放
——而notify也需要操作锁，所以也必须持有锁，但这个操作不是释放锁，也就是说notify之后，wait返回之前，还可以执行代码，只要在同步块里，这个时机就是锁释放之前

###（2）套路
一般用法是：

在一个线程里：
synchronized(obj){
	while(condition不符合){
		obj.wait(); //等到condition符合
	}
	//处理condition符合之后的逻辑
}

----
注意，这里有个有缺陷的wait的用法
while(condition不符合){
	//Point-1：在这里，
	）线程可能切换了，切到另一个线程，并且导致了condition符合了（并notify，但此时这里并未，然后切换回来，wait了，就死锁了
	synchronized(obj){
		obj.wait(); 
	}
}

----

在另一个线程里：
synchronized(obj){
	///处理condition，让其符合条件
	obj.notify();
	//做些wait返回之前可能需要做的事
	//锁在这准备释放了，wait复活的两个条件都满足了
}

notify会唤醒最后一个在obj上wait的线程
notifyAll会唤醒所有在obj上wait的线程


可以接受时间参数的wait：
——给wait个超时时间


例子：c11.WaxOMatic
例子很好的说明了wait和notify怎么用

* 总结：
    * 这个例子有点太场景化，也就是太特殊，如果要用同步队列来实现：
    * 上蜡和抛光可以看做是两条流水线，并且原则是：一条流水线处理一个队列里的消息
    * 等待上蜡的队列，等待抛光的队列，而两条流水线，就变成了在queue.take()或者poll()时阻塞，思路就清楚多了
    * 但是注意，不是所有的业务模型都可以映射到队列模型

###（3）为什么要在while(true)里wait
* 再说notifyAll和while(true){wait();}的问题
	* 一个或多个任务在一个对象上等待同一个条件，不管谁被唤醒之后，条件可能已经改变了，所以wait醒来之后还是要对条件进行检查
	* 在一个对象上wait的线程可能有多个，而且可能是针对不同的条件，所以被唤醒之后，需要检查一下是否自己需要的条件
	* 总之，wait醒来之后，需要检查自己需要的条件是否满足，不满足的话还得继续wait
	* 所以，在while(true)中wait通常是比较合理的做法
		* notify只能唤醒一个


###（4）notify和notifyAll的区别

没整明白呢



##2 Condition，await和signal

参考c11.WaxOMatic2.java


和这里要用到的锁就是ReentranLock



##3 同步队列：接口BlockingQueue


###（1） 简介

总而言之，同步队列可以使很多业务模型得以简化，处理问题的思路更简单

java提供了大量的BlockingQueue接口的实现，例子参考BlockingQueueTest

public interface BlockingQueue<E> extends Queue<E> {
		
	/**
	 * 添加，如果没有空间，会阻塞等待
	 * @param e
	 * @throws InterruptedException
	 */
	void put(E e) throws InterruptedException;
	
	/**
     * 移除并返回，如果empty，则阻塞等待
     */
    E take() throws InterruptedException;
    
    /**
     * 移除并返回，如果empty，会等待指定时间
     * @param timeout
     * @param unit
     * @return
     */
    E poll(long timeout, TimeUnit unit);
    
    /**
     * Returns the number of additional elements that this queue can ideally
     * (in the absence of memory or resource constraints) accept without
     * blocking, or {@code Integer.MAX_VALUE} if there is no intrinsic
     * limit.
     *
     * <p>Note that you <em>cannot</em> always tell if an attempt to insert
     * an element will succeed by inspecting {@code remainingCapacity}
     * because it may be the case that another thread is about to
     * insert or remove an element.
     *
     * @return the remaining capacity
     */
    int remainingCapacity();
    
    public boolean contains(Object o);
    
    /**
     * 把队列里的元素都移到Collection里
     * @param c
     * @return
     */
    int drainTo(Collection<? super E> c);
    int drainTo(Collection<? super E> c, int maxElements);
    
}

java.util.concurrent.BlockingQueue<Message> queue = 
			//new ArrayBlockingQueue<BlockingQueueTest.Message>(10, true); //true是access policy，表示FIFO，先进先出
			//new LinkedBlockingDeque<BlockingQueueTest.Message>(10);
			//new DelayQueue<BlockingQueueTest.Message>();
			//new PriorityBlockingQueue<BlockingQueueTest.Message>();
			//new SynchronousQueue<BlockingQueueTest.Message>(true);
			//new LinkedTransferQueue<BlockingQueueTest.Message>();

常规：			
ArrayBlockingQueue
LinkedBlockingDeque

延迟队列：
DelayQueue

优先级队列
PriorityBlockingQueue

不懂的队列：
SynchronousQueue
LinkedTransferQueue

###（2）例子


####例子1：PriorityQueue，优先级队列

代码：c11.PriorityQueueDemo.java

* 简介：
	* 基于优先级，优先级的排序规则，由你来实现，就是实现方法compareTo
	* 用到了add和take方法，和take配对的不是put吗
	* 队列的元素类型Task，必须实现Comparable来对优先级进行排序，以保证按优先级顺序来

PriorityBlockingQueue代码1000多行呢


####例子2：DelayQueue，延迟队列

代码：c11.DelayQueueDemo.java

* 要点：
	* 队列的元素类型：需要实现Delayed接口，包括getDelay()和compareTo()两个方法
	* getDlay决定了任务是否到时可以运行
	* compareTo决定了队列会排序，并且队列头就是第一个应该被运行的任务
	


* 带着问题看代码：
	* 普通队列在take()这里阻塞，有新任务put进来，则被唤醒，所以只要有任务，就不会阻塞
	* DelayQueue即使有消息，也会阻塞，表示任务都没到时间，这种阻塞，何时被唤醒？
		* DelayQueue内部有个PriorityQueue
		* 参考下面的代码，就是DelayQueue的take方法实现
			* 取出队列头（peek，看看，不删除）
			* 如果是null，说明队列空，则Condition.await()
			* 如果不空，则根据任务的int delay = getDelay()方法，计算出delay时间，即多久之后任务可运行
				* 注意队列是根据你实现的compareTo排好序的，队列头就应该是第一个可以被运行的任务
				* getDelay()方法也是你实现的，在任务内部，你要记录延时时间（时间间隔），触发时间（具体时刻，计算出来并存好），所以随时随刻你都可以在getDelay()方法中计算出任务多久之后应该被触发
				* 如果delay <= 0，就返回(queue.poll())，任务可以执行了
				* 如果delay > 0，说明还没到时，则Condition.awaitNanos(delay)，阻塞指定时间后，再开始新一轮for循环，此时队列头的任务应该到时了
				* leader不知道是干啥的啊，先当成一直是null来理解的
	* ReentrantLock决定了多个线程在队列上take时，同一时刻只有一个线程会进入，所以只会有一个线程在await上阻塞，其他会在ReentrantLock上阻塞

```java
public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        for (;;) {
            E first = q.peek();
            if (first == null)
                available.await();
            else {
                long delay = first.getDelay(NANOSECONDS);
                if (delay <= 0)
                    return q.poll();
                first = null; // don't retain ref while waiting
                if (leader != null)
                    available.await();
                else {
                    Thread thisThread = Thread.currentThread();
                    leader = thisThread;
                    try {
                        available.awaitNanos(delay);
                    } finally {
                        if (leader == thisThread)
                            leader = null;
                    }
                }
            }
        }
    } finally {
        if (leader == null && q.peek() != null)
            available.signal();
        lock.unlock();
    }
    }
```


######
`Thread leader`的作用：没明白
Thread designated to wait for the element at the head of
the queue.  This variant of the Leader-Follower pattern
(http://www.cs.wustl.edu/~schmidt/POSA/POSA2/) serves to
minimize unnecessary timed waiting.  When a thread becomes
the leader, it waits only for the next delay to elapse, but
other threads await indefinitely.  The leader thread must
signal some other thread before returning from take() or
poll(...), unless some other thread becomes leader in the
interim.  Whenever the head of the queue is replaced with
an element with an earlier expiration time, the leader
field is invalidated by being reset to null, and some
waiting thread, but not necessarily the current leader, is
signalled.  So waiting threads must be prepared to acquire
and lose leadership while waiting.


###（3）更多

* 一些实践中的应用
    * 安卓的Looper和Handler可以作为一个通用的
    * UI框架的大循环，是一个阻塞式的，循环处理的其实是UI上用户操作产生的事件，界面不会频繁刷新
    * 游戏框架的大循环，是一个非阻塞式的，每一轮循环必须完成刷新界面的操作，也必须处理用户的输入和游戏逻辑，并且有被限制的帧率
    * 有些特定的业务模型，可以用队列来简化，如抢单：（本来打算写写抢单逻辑，但发现单单从同步队列来考虑还不够）
    	* Model层的repo负责存储订单，提供增删改查操作，并对外通过事件总线发出通知
        * 线程A负责接收服务器来的订单消息，repo.add()
        * 线程B负责不断遍历所有订单，在后台刷新每个订单的倒计时等恒变状态，并调用repo.update(), repo.delete()
        * 主线程负责：思路断了
        * 重新调整思路：
        	* Model层不变
        	* 线程A不变
        	* 现在说谁能改变订单状态：
        		* 后台任务，主要是各种倒计时
        		* 用户操作
        	* 编不下去了


##4 管道：PiperWriter和PiperReader

这个io在read上的阻塞是可以interrupt的，与之相比，System.in.read()就不可中断


第十二课 Volatile
============================

例子：c8包里的VolatileTest

Volatile和原子性没什么直接关系
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
    * 在每个线程里调用修改cenceled的值，首先会保存到本地缓存，然后也会同步到主存里，据说这是规定，必须的
    * 但是其他线程通过isCanceled()读取它的值，是从本地缓存读，没被改变，即不可见，它已经看不见主存里的值了
    * 所以用volatile来修饰，保证每次对它的修改，都会同步到主存的同时，也会对所有其他线程的内存可见，或者就是保证对于volatile变量，不会在工作内存中拷贝一份，都是在主存中读写 
    * 整了半天，还是挺麻烦，推荐首选用同步来解决问题，volatile适用于只有一个字段可变的情况


下面的内容来自网页：http://www.cnblogs.com/MOBIN/p/5407965.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io


* 摘要
    * Volatile是Java提供的一种弱同步机制，当一个变量被声明成volatile类型后编译器不会将该变量的操作与其他内存操作进行重排序。
    * 在某些场景下使用volatile代替锁可以减少代码量和使代码更易阅读

* Volatile的特性
    * 可见性：当一条线程对volatile变量进行了修改操作时，其他线程能立即知道修改的值，即当读取一个volatile变量时总是返回最近一次写入的值
    * 原子性：对于单个voatile变量其具有原子性(能保证long double类型的变量具有原子性)，但对于i ++ 这类复合操作其不具有原子性(见下面分析)

* Volatile使用的前提
    * 对变量的写入操作不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值
    * 该变量不会与其他状态变量一起纳入不变性条件中
    * 在访问变量时不需要加锁

原理：
原因：Java内存模型(JMM)规定了所有的变量都存储在主内存中，主内存中的变量为共享变量，
而每条线程都有自己的工作内存，线程的工作内存保存了从主内存拷贝的变量，
所有对变量的操作都在自己的工作内存中进行，完成后再刷新到主内存中，
回到例1，第18行号代码主线程(线程main)虽然对isRunning的变量进行了修改且有刷新
回主内存中（`《深入理解java虚拟机》中关于主内存与工作内存的交互协议提到变量在工作
内存中改变后必须将该变化同步回主内存`），但volatileThread线程读的仍是自己工作内存
的旧值导致出现多线程的可见性问题，解决办法就是给isRunning变量加上volatile关键字。

* volatile内存语义总结如下
    * 当线程对volatile变量进行写操作时，会将修改后的值刷新回主内存
    * 当线程对volatile变量进行读操作时，会先将自己工作内存中的变量置为无效，之后再通过主内存拷贝新值到工作内存中使用。



* Synchronized与volatile区别 
    * volatile只能修饰变量，而synchronized可以修改变量，方法以及代码块
    * volatile在多线程中不会存在阻塞问题，synchronized会存在阻塞问题
    * volatile能保证数据的可见性，但不能完全保证数据的原子性，synchronized即保证了数据的可见性也保证了原子性
    * volatile解决的是变量在多个线程之间的可见性，而sychroized解决的是多个线程之间访问资源的同步性










第十三课 死锁
============================



第十四课 java提供的并发构件
============================


##1 CountDownLatch

参考：c11.CountDownLatchDemo.java

* 适用于：
	* 一组子任务并行执行，另一组任务等待着一组完成才进行，或等待某个条件完成才进行
		* 并行执行的任务数，或者等待的这个条件，可以抽象成倒数，倒数到0，则另一组任务就可以继续执行
    * 一个任务会被分解成多个子任务x，y，z
    * 其中一个子任务B会等待其他几个子任务完成才会继续执行
    * 所以提供一个CountDownLatch对象，并设置初始值
    	* 任务B在CountDownLatch对象上await：latch.await();
    	* 每完成一个子任务，就在CountDownLatch对象上倒数一次：latch.countDown();
		* 直到倒数到0，await的对象就会被唤醒
		* 任务B可以有多个

* 限制：
	* 只能用一次，如果要用多次，参考CyclicBarrier


##2 CyclicBarrier

例子参考：赛马模拟，c11.CyclicBarrierDemo.java

* 适用于：
    * 某个人物要等待多个任务并行进行，直到都完成，才会执行
    * 可以重用
    * 不得不说，CyclicBarrier还有点不好理解，看了demo代码还是没整明白
    	* 怎么是horse在barrier上await呢
    	* CyclicBarrier构造怎么还得传入必须await的线程个数呢

* 介绍
	* 构造：barrier = new CyclicBarrier(n, new Runnable(){})
		* 参数1：计数值，当有线程在barrier上await时，计数减一，n个线程都await了，计数就成0了，栅栏动作就会执行
		* 参数2：叫做栅栏动作，计数到0时，会自动执行

* CyclicBarrierDemo讲解：
	* 栅栏动作做的事情是：
		* 打印线路，打印终点
		* 打印每匹马当前的位置
		* 判断是否有马走到终点，有则提示夺冠，并结束所有线程（shutdownNow)
	* 栅栏动作执行完后，计数又会重置，此时
		* 每匹马再向前走一步，距离是随机数
		* 走完之后，await一下
		* 所有马都走完一步，await倒数计数值又是0了，再激活栅栏动作
	* 如此循环
	* 所以，栅栏动作等所有子任务都await了，才运行，此时所有子任务都阻塞，子任务等栅栏动作完成，计数自动重置，再被唤醒

* 总结：
	* 构造时，传入计数值和栅栏动作
	* 计数值减一操作由子任务的await完成
	* 栅栏动作在计数值为0时激活，并且运行完会自动重置计数值，并唤醒await的线程们


* 更多：
	* 思考：如果没有CyclicBarrier，仿真赛马你会怎么实现？
	* 你的实现会考虑起始和终结的情况吗？宣布夺冠之后，所有的马都能立即停止前进吗？统计开始和结束时，所有马的状态保持前后一致吗？
	* 提示1：CyclicBarrier的子任务，会在await上等待栅栏动作的结束，并且await是可以被interrupt的
	* 提示2：赛场统计是由栅栏动作完成的，此动作会在每一批马都前进一步之后，所有马都await，栅栏动作开始统计


第十五课 示例
============================
