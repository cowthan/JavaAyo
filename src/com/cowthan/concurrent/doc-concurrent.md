第一课 多线程入门
=========================================


1 基本入门：Thread + Runnable

一个任务：
Runnable：要的是那个run方法
Callable和Future：要的是那个call方法，future里放的是子线程的返回结果，get方法会阻塞等待返回，就是等call方法返回

一个线程：Thread

LiftOff1 launch = new LiftOff1();
new Thread(launch, "thread-1").start();


一个线程承载了一个任务


线程的状态：
——new：已经创建完毕，且已经start，资源分配完毕，等待分配时间片了，这个状态只会持续很短的时间，下一步就会进入运行或者阻塞
——run：就绪状态，只要给了时间片，就会运行，在任一时刻，thread可能运行也可能不运行
——block：阻塞状态，程序本身能够运行，但有个条件阻止了它运行，调度器会忽略这个线程，直到跳出阻塞条件，重新进入就绪状态
——dead：run()方法返回，或者被中断


2 Java的线程管理：
Executor：一个接口，其定义了一个接收Runnable对象的方法executor，其方法签名为executor(Runnable command)
Executors：控制着一堆线程池

ExecutorService：一个接口，继承自Executor，具有服务生命周期的Executor，例如关闭，这东西知道如何构建恰当的上下文来执行Runnable对象
是一个比Executor使用更广泛的子类接口，其提供了生命周期管理的方法，以及可跟踪一个或多个异步任务执行状况返回Future的方法

ScheduledExecutorService：一个接口，继承自ExecutorService

AbstractExecutorService：ExecutorService执行方法的默认实现
ThreadPoolExecutor：继承自AbstractExecutorService，线程池，可以通过调用Executors以下静态工厂方法来创建线程池并返回一个ExecutorService对象

ScheduledExecutorService：一个可定时调度任务的接口


ScheduledThreadPoolExecutor：ScheduledExecutorService的实现，父类是ThreadPoolExecutor，一个可定时调度任务的线程池

用法：Executors的每个方法都可以传入第二个参数，一个ThreadFactory对象
ExecutorService exec = Executors.newCachedThreadPool(); //线程数总是会满足所有任务，所有任务都是并行执行，同时抢时间片，而旧线程会被缓存和复用
ExecutorService exec = Executors.newFixedThreadPool(2); //两个线程同时运行，其他的会排队等待
ExecutorService exec = Executors.newSingleThreadExecutor(); //1个线程同时运行，即多个任务会串行执行
ExecutorService exec = Executors.newWorkStealingPool();  //不知道啥意思
ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);//不知道啥意思
ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();//不知道啥意思

for(int i = 0; i < 5; i++){
	exec.execute(new LiftOff1()); //提交任务
}

//shutdown会关闭线程池入口，不能再提交新任务，但之前提交的，会正常运行到结束
//如果不关闭，线程池会一直开着，等待提交任务，进程也就不会关闭
exec.shutdown();  



下面多讲点：

public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) //后两个参数为可选参数






3 ThreadFactory

//设置ThreadFactory：只有当需要新线程时，才会来这里调用，就是说ThreadFactory本身不管理线程池，只是给线程池干活的
ExecutorService exec = Executors.newFixedThreadPool(2, new ThreadFactory() {
	
	private int threadCount = 0;
	
	@Override
	public Thread newThread(Runnable r) {
		threadCount++;
		Thread tr = new Thread(r, "thread-from-ThreadFactory-" + threadCount);
		//这里可以给线程设置一些属性
		tr.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){  
			@Override  
			public void uncaughtException(Thread t, Throwable e) {  
				e.printStackTrace();
			}  
		});  
		//tr.setDaemon(true);    //设置这个为true，则主线程退出时，子线程不管是否结束，都退出
		tr.setPriority(Thread.MAX_PRIORITY);  
		
		return tr;
	}
});


4 让出时间片

Thread.yield();
通知并建议线程调度器，我已经做完了主要工作，时间片你可以分给别了
即使调用了这个，还是可能没有切换时间片，或者切换了，但是还是给了当前线程

Thread.sleep(1000);
TimeUnit.SECOND.sleep(1);
让当前线程进入睡眠状态，程序就阻塞在这里了
这个的表现应该是比yield良好多了

但这两个的特性，都不应该过于依赖，作者再三叮嘱了
因为系统对时间片的划分是不可依赖的
你的程序也不会对时间片的划分有什么依赖


5 线程属性：

`后台线程：`
tr.setDaemon(true);    //设置这个为true，则主线程退出时，子线程不管是否结束，都退出
后台线程表示在程序后台提供一种通用服务的线程，且不是程序不可或缺的部分
当所有非后台线程结束了，后台线程也就结束了
isDaemon()判断是否后台线程
从后台线程创建的线程，自动默认是后台线程

`优先级：`
tr.setPriority(Thread.MAX_PRIORITY);  
仅仅是执行频率较低，不会造成死锁（线程得不到执行）
JDK有十个优先级，但和操作系统映射的不是很好
windows有7个优先级，但不固定
Sun的Solaris有2的31次方个优先级
所以调用优先级时，安全的做法是只使用：MAX_PRIORITY, NORM_PRIORITY, MIN_PRIORITY

`线程名字：参数2`
Thread tr = new Thread(r, "thread-name-" + threadCount);

全局异常捕捉：全局异常是基于线程的，并且异常不能跨线程传递
tr.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){  
	@Override  
	public void uncaughtException(Thread t, Throwable e) {  
		e.printStackTrace();
	}  
});  

而Thread.setUncaughtExceptionHandler是给所有线程都设置一个全局异常捕捉


`isAlive()：线程是否还活着`
这个会影响join，见第七课


第二课   Executor深入分析
=========================================

public interface Executor {
    void execute(Runnable command);
}

看C2.java里的三个Executor的实现，取自java源码里的注释，这几行代码基本阐明了Executor的作用

Excutor能决定的事：
* 选择哪个线程
* 执行runnable

Excutor管不了的事：
* Callable，Future管不了
* 没有一个线程池，线程池可能需要自己写，跟Executor还是谁的没关系
* 没法延时，定时运行



第三课：Callable和Future
=========================================


Runnable不产生返回值，ExecutorService.execute(Runnable)，走的是run方法


Callable产生返回值，ExecutorService.submit(Callable)，走的是call方法

用法1：submit Callable and get a Future, block in future.get()

interface ArchiveSearcher { String search(String target); }
class App {
    ExecutorService executor = ...
    ArchiveSearcher searcher = ...
    
    void showSearch(final String target) throws InterruptedException {
    
      Future future = executor.submit(new Callable() {
	      public String call() {
	          return searcher.search(target);
	      }}
	  );
	  
      displayOtherThings(); // do other things while searching
      
      try {
        displayText(future.get()); // use future
      } catch (ExecutionException ex) { cleanup(); return; }
    }
}

用法2：execute a FutureTask, and get a 

FutureTask future = new FutureTask(new Callable<String>() {
    	public String call() {
    		return searcher.search(target);
		}
	}
);
executor.execute(future);

future.get()


关于Callable：能返回就给返回，不能返回就抛异常
public interface Callable<V> {
    V call() throws Exception;
}


关于Future：
public interface Future<V> {

    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)  throws InterruptedException, ExecutionException, TimeoutException;
}

1 boolean cancel(boolean mayInterruptIfRunning);

参数的意思：正在执行的task是否允许打断，如果是true，会打断，如果false，则允许in-progress的任务执行完

何时失败：
已经运行完的task
已经被cancel过的task
无法被中断的任务

怎么成功：
还没start的任务，比如在等待的，可以cancel
正在running的任务，参数mayInterruptIfRunning指定了是不是可以尝试interrupt

副作用：
只要cancel被调用了且返回true，isDone和isCancelled一直返回true


2 get：取回结果，如有必要，可以阻塞

可以阻塞就可以被interrupt呗
get()没有超时时间
get(1, TimeUnit.Second)：表示最多阻塞1秒，过了一秒就抛出超时异常


---------------RunnableFuture，FutureTask

public interface RunnableFuture<V> extends Runnable, Future<V> {
    /**
     * Sets this Future to the result of its computation
     * unless it has been cancelled.
     */
    void run();
}


public class FutureTask<V> implements RunnableFuture<V>{

}

FutureTask的初始化：
FutureTask(Callable<V> callable)
FutureTask(Runnable runnable, V result)
注意：Runnable + 返回值就是一个Callable了啊，具体看RunnableAdapter

总之，FutureTask内部就有了一个Callable

-----------------
关于：FutureTask(Runnable runnable, V result)
调用了：Executors.callable()
public static <T> Callable<T> callable(Runnable task, T result) {
    if (task == null)
        throw new NullPointerException();
    return new RunnableAdapter<T>(task, result);
}

static final class RunnableAdapter<T> implements Callable<T> {
    final Runnable task;
    final T result;
    RunnableAdapter(Runnable task, T result) {
        this.task = task;
        this.result = result;
    }
    public T call() {
        task.run();
        return result;
    }
}


--------------------

下一步就是：
exec.execute(runnable)
exec.execute(FutureTask)   会调用run方法
Future<Result> future = exec.submit(Callable)
以及，future的get阻塞是怎么实现的

先分析FutureTask，因为ExecuteService还没说到：

这里面FutureTask.run()被调用，大体实现是：

void run(){
	Callable<V> c = callable;
	result = c.call();
	outcome = result;
	U.putOrderedInt(this, STATE, NORMAL); // final state
	finishCompletion();
}

此时对于futureTask.get()
public V get() throws InterruptedException, ExecutionException {
    int s = state;
    if (s <= COMPLETING)
        s = awaitDone(false, 0L);  ///在这无限循环等call运行结束，返回结果，如果状态是取消，中断等，抛出异常，还看超时时间，没事干会yield
    return report(s);  ///返回call的返回结果
}

--------------------------
下面再研究ExecutorService和AbstractExecutorService的submit方法：

public Future<?> submit(Runnable task) {
    if (task == null) throw new NullPointerException();
    RunnableFuture<Void> ftask = newTaskFor(task, null);
    execute(ftask);
    return ftask;
}

protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
    return new FutureTask<T>(runnable, value);
}

这不就顺起来了嘛，最后都归结到了execute和FutureTask


---------------------------


最后，FutureTask实现的是RunnableFuture，其实你完全可以不用FutureTask，
例如你想实现个MyFutureTask，这个不会在get方法里阻塞，而是基于异步IO，

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
 
    static class CustomTask implements RunnableFuture {...}
 
    protected  RunnableFuture newTaskFor(Callable c) {
        return new CustomTask(c);
    }
    protected  RunnableFuture newTaskFor(Runnable r, V v) {
        return new CustomTask(r, v);
    }
    // ... add constructors, etc.
  }


第四课 ExcecutorService和AbstractExecutorService深入分析
==========================================

submit相关功能，get阻塞等，都由FutureTask来负责了，那ExecutorService还比Executor多了什么呢

生命周期是在这里管理的？？

public static class DirectExecutorService implements ExecutorService{

	public void execute(Runnable command);
	public <T> Future<T> submit(Callable<T> task);
	public Future<?> submit(Runnable task);
	public <T> Future<T> submit(Runnable task, T result);
	
	public boolean isShutdown() ;
	public boolean isTerminated();
	public void shutdown();
	public List<Runnable> shutdownNow();
	
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
	
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit);
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks);
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit);
	
}

0 注意，AbstractExecutorService里面并没有对execute方法的实现，而是留给了子类，所以说线程池相关的东西这里是不负责的

1 shutdown系列

shutdown()：拒绝再接收新的task，但已有的task会执行到terminate
shutdownNow()：禁止再接收新的task，已有task，在waiting的不会再start，已经执行的会尝试stop掉

未shutdown状态：线程池还在运行，不管有没有running，waiting的task，都会一直等待add新task（通过execute或者submit）
shutdown状态：执行完现有task，就会terminate

2 awaitTermination


3 invokeAll和invokeAny


看方法doInvkeAny，大体套路是：

T doInvkeAny(Collection<Callable<T>> tasks, boolean timed, long nanos){

	int ntasks = tasks.size();
	ArrayList<Future<T>> futures = new ArrayList<Future<T>>(ntasks);
	
	ExecutorCompletionService<T> ecs = new ExecutorCompletionService<T>(this);
	Iterator<? extends Callable<T>> it = tasks.iterator();
	
	后面还有，没整明白是怎么添加的任务
}

涉及到了ExecutorCompletionService，CompletionService，LinkedBlockingQueue等等

这部分连是干什么的都没整明白


private <T> T doInvokeAny(Collection<? extends Callable<T>> tasks,
                              boolean timed, long nanos)
    throws InterruptedException, ExecutionException, TimeoutException {
    if (tasks == null)
        throw new NullPointerException();
    int ntasks = tasks.size();
    if (ntasks == 0)
        throw new IllegalArgumentException();
    ArrayList<Future<T>> futures = new ArrayList<Future<T>>(ntasks);
    ExecutorCompletionService<T> ecs =
        new ExecutorCompletionService<T>(this);

    // For efficiency, especially in executors with limited
    // parallelism, check to see if previously submitted tasks are
    // done before submitting more of them. This interleaving
    // plus the exception mechanics account for messiness of main
    // loop.

    try {
        // Record exceptions so that if we fail to obtain any
        // result, we can throw the last exception we got.
        ExecutionException ee = null;
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        Iterator<? extends Callable<T>> it = tasks.iterator();

        // Start one task for sure; the rest incrementally
        futures.add(ecs.submit(it.next()));
        --ntasks;
        int active = 1;

        for (;;) {
            Future<T> f = ecs.poll();
            if (f == null) {
                if (ntasks > 0) {
                    --ntasks;
                    futures.add(ecs.submit(it.next()));
                    ++active;
                }
                else if (active == 0)
                    break;
                else if (timed) {
                    f = ecs.poll(nanos, TimeUnit.NANOSECONDS);
                    if (f == null)
                        throw new TimeoutException();
                    nanos = deadline - System.nanoTime();
                }
                else
                    f = ecs.take();
            }
            if (f != null) {
                --active;
                try {
                    return f.get();
                } catch (ExecutionException eex) {
                    ee = eex;
                } catch (RuntimeException rex) {
                    ee = new ExecutionException(rex);
                }
            }
        }

        if (ee == null)
            ee = new ExecutionException();
        throw ee;

    } finally {
        for (int i = 0, size = futures.size(); i < size; i++)
            futures.get(i).cancel(true);
    }
}

4 关于ExecutorCompletionService，CompletionService

实现了CompletionService，将执行完成的任务放到阻塞队列中，通过take或poll方法来获得执行结果

///例4：（启动10条线程，谁先执行完成就返回谁）
public class CompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);        //创建含10.条线程的线程池
        CompletionService completionService = new ExecutorCompletionService(executor);
        for (int i =1; i <=10; i ++) {
            final  int result = i;
            completionService.submit(new Callable() {
                public Object call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));   //让当前线程随机休眠一段时间
                    return result;
                }
            });
        }
        System.out.println(completionService.take().get());   //获取执行结果
    }
}
//输出结果可能每次都不同（在1到10之间）



第五课：ThreadPoolExecutor的实现，AbstractExecutorService的子类，也是ExecutorService的实现类
===============================================

先看怎么构造：
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,   //使用ThreadFactory创建新线程，默认使用defaultThreadFactory创建线程
                          RejectedExecutionHandler handler) 


corePoolSize：核心线程数，如果运行的线程少于corePoolSize，则创建新线程来执行新任务，即使线程池中的其他线程是空闲的
maximumPoolSize:最大线程数，可允许创建的线程数，corePoolSize和maximumPoolSize设置的边界自动调整池大小：
corePoolSize <运行的线程数< maximumPoolSize:仅当队列满时才创建新线程
corePoolSize=运行的线程数= maximumPoolSize：创建固定大小的线程池
keepAliveTime:如果线程数多于corePoolSize,则这些多余的线程的空闲时间超过keepAliveTime时将被终止
unit:keepAliveTime参数的时间单位
workQueue:保存任务的阻塞队列，与线程池的大小有关：
  当运行的线程数少于corePoolSize时，在有新任务时直接创建新线程来执行任务而无需再进队列
  当运行的线程数等于或多于corePoolSize，在有新任务添加时则选加入队列，不直接创建线程
  当队列满时，在有新任务时就创建新线程
threadFactory:使用ThreadFactory创建新线程，默认使用defaultThreadFactory创建线程
handle:定义处理被拒绝任务的策略，默认使用ThreadPoolExecutor.AbortPolicy,任务被拒绝时将抛出RejectExecutorException


再看Executors里一堆new方法怎么用的：

public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  //使用同步队列，将任务直接提交给线程
                                  new SynchronousQueue<Runnable>());
}


//线程池：指定线程个数
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  //使用一个基于FIFO排序的阻塞队列，在所有corePoolSize线程都忙时新任务将在队列中等待
                                  new LinkedBlockingQueue<Runnable>());
}


//单线程：基于一个固定个数的线程池，不管在哪里，实现串行执行，都是基于一个其他的线程池和一个队列
public static ExecutorService newSingleThreadExecutor() {
   return new FinalizableDelegatedExecutorService
                     	//corePoolSize和maximumPoolSize都等于，表示固定线程池大小为1
                        (new ThreadPoolExecutor(1, 1,
                                                0L, TimeUnit.MILLISECONDS,
                                                new LinkedBlockingQueue<Runnable>()));
}



分析：

private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
BlockingQueue<Runnable> workQueue;

public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    /*
     * Proceed in 3 steps:
     *
     * 1. If fewer than corePoolSize threads are running, try to
     * start a new thread with the given command as its first
     * task.  The call to addWorker atomically checks runState and
     * workerCount, and so prevents false alarms that would add
     * threads when it shouldn't, by returning false.
     *
     * 2. If a task can be successfully queued, then we still need
     * to double-check whether we should have added a thread
     * (because existing ones died since last checking) or that
     * the pool shut down since entry into this method. So we
     * recheck state and if necessary roll back the enqueuing if
     * stopped, or start a new thread if there are none.
     *
     * 3. If we cannot queue task, then we try to add a new
     * thread.  If it fails, we know we are shut down or saturated
     * and so reject the task.
     */
    int c = ctl.get();
    
    //当前正在工作的线程数 < 允许的线程数，则创建新线程，运行task
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))  ///有个Worker内部类，内部会调用ThreadFactory.newThread()
            return;
        c = ctl.get();
    }
    if (isRunning(c) && workQueue.offer(command)) {   
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);             //调用RejectedExecutionHandler的handler.rejectedExecution(command, this);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);   //return false;
    }
    else if (!addWorker(command, false))
        reject(command);  //handler.rejectedExecution(command, this);
}




第六课：定时，延时--Schduled
======================================

//使用newScheduledThreadPool来模拟心跳机制
public class HeartBeat {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);  //5是corePoolSize
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("HeartBeat.........................");
            }
        };
        executor.scheduleAtFixedRate(task,5,3, TimeUnit.SECONDS);   //5秒后第一次执行，之后每隔3秒执行一次
    }
}
