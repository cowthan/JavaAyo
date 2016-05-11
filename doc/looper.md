#安卓的线程模型，消息机制
===============================

##1 UI线程是什么：

* 简介：
    * 是一个线程，里面是一个循环，对应一个队列
    * Looper的功能就是：轮询这个队列，取出里面的消息，处理
    * Looper是被实现为通用性很高的一个类，处理大循环，队列
    * Looper是线程本地变量，以ThreadLocal存储，一个线程就一个Looper
    * 主线程的Looper就是：Looper.getMainLooper()
    * 当前线程的Looper就是：Looper.myLooper()



```java
//判断是不是主线程：
if(Looper.myLooper() == Looper.getMainLooper()){

}
```

```java
//怎么自己创建个循环队列的线程？
class LooperThread extends Thread {
	public Handler mHandler;
	
	public void run() {
		Looper.prepare();   //给当前线程----创建Looper对象，创建消息队列

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
			// process incoming messages here
			}
		};
	Looper.loop();
}

```

####

prepare方法里：
```java
private static void prepare(boolean quitAllowed) {
    if (sThreadLocal.get() != null) {
        throw new RuntimeException("Only one Looper may be created per thread");
    }
    sThreadLocal.set(new Looper(quitAllowed));
}

//注意：这个线程局部变量，即Looper会以线程布局变量的形式存储
static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();


//对应下面两个方法，就能明白点
public static Looper myLooper(){
	return sThreadLocal.get();
}

public static Looper getMainLooper(){
	synchronized (Looper.class) {
        return sMainLooper;
    }
}
```


##2 怎么往UI线程发消息？


####（1）需要Handler

`构造Handler`  

Handler();
Handler(Looper.getMainLooper()或者myLooper());
Handler(Callback callback)
Handler(Looper looper, Callback callback)
Handler(boolean async)
Handler(Callback callback, boolean async)
Handler(Looper looper, Callback callback, boolean async)

* 你调哪个都行
    * 需要传入Looper的地方，如果是空，那就是当前线程的looper
    * 主线程的looper是Looper.getMainLooper()
    * 当前线程的looper是Looper.myLooper()
    * 传Callback可以避免继承Handler写个子类
    * async不知道什么意思


注意，这里有两个地方可以指定怎么处理消息：
```java
//给handler传入Callback，或者实现Handler的handleMessage
mHandler = new Handler() {
	public void handleMessage(Message msg) {
		// process incoming messages here
	}
};



`构造Message`  

```java

Message msg = Message.obtain();
obtain(Message orig)
obtain(Handler h)
obtain(Handler h, Runnable callback)   //这个指定了一个Runnable来处理消息，会屏蔽Handler带的处理方法
obtain(Handler h, int what) 
obtain(Handler h, int what, Object obj)
obtain(Handler h, int what, int arg1, int arg2)
obtain(Handler h, int what, int arg1, int arg2, Object obj)

//最后给出一个obtain的源码，主要是为了列出Message的所有有用字段
public static Message obtain(Handler h, int what, int arg1, int arg2, Object obj) {

    Message m = obtain();
    m.target = h;  //Handler
    m.what = what;  //int
    m.arg1 = arg1;  //int
    m.arg2 = arg2;  //int
    m.obj = obj;  //Object
    m.callback; //Runnable
    m.data; //Bundle， 对应setData(Bundle data)

    return m;
}

```




`发送消息`

* 有几个接口，注意，发送消息就插入消息队列，可以控制时间，插入位置
    * message其实也调用了handler的相关接口
    * message.sendToTarget();  //必须事先指定message.target
    * 时间控制
    * handler.sendMessage(Message m)
    * handler.sendMessageDelayed(Message msg, long delayMillis)
    * handler.sendMessageAtTime(Message msg, long uptimeMillis)
    * handler.sendEmptyMessage(int what)
    * handler.sendEmptyMessageDelayed(int what, long delayMillis)
    * 位置控制
    * sendMessageAtFrontOfQueue(Message msg)
    * post系列，其实也调用了sendMessage系列
    * post(Runnable r)--Runnable变成了Message的callback
    * postAtTime(Runnable r, long uptimeMillis)
    * postAtTime(Runnable r, Object token, long uptimeMillis)  token对应messge.obj
    * postDelayed(Runnable r, long delayMillis)


* 注意
    * delay超时时间和uptime触发时间：`uptimeMillis = SystemClock.uptimeMillis() + delayMillis`
    * 所有发消息的方法，都：
        * 调用了Handler.enqueueMessage(queue, msg, uptimeMillis)，往队列里插入消息
        * Handler的队列，就来自Looper
        


```java
//给主线程发消息
Handler h = new Handler(Looper.getMainLooper());
h.post(new Runnable() {
	@Override
	public void run() {
      	///处理消息：在UI线程里了已经
    }
});

```


####（2）使用AsyncTask




##3 循环取出消息，处理消息


####主循环

看 Looper.loop()
```java
public static void loop() {
    final Looper me = myLooper();
    if (me == null) {
        throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
    }
    final MessageQueue queue = me.mQueue;

    // Make sure the identity of this thread is that of the local process,
    // and keep track of what that identity token actually is.
    Binder.clearCallingIdentity();
    final long ident = Binder.clearCallingIdentity();

    for (;;) {
        Message msg = queue.next(); // might block
        if (msg == null) {
            // No message indicates that the message queue is quitting.
            return;
        }

        // This must be in a local variable, in case a UI event sets the logger
        Printer logging = me.mLogging;
        if (logging != null) {
            logging.println(">>>>> Dispatching to " + msg.target + " " +
                    msg.callback + ": " + msg.what);
        }

        msg.target.dispatchMessage(msg);

        if (logging != null) {
            logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
        }

        // Make sure that during the course of dispatching the
        // identity of the thread wasn't corrupted.
        final long newIdent = Binder.clearCallingIdentity();
        if (ident != newIdent) {
            Log.wtf(TAG, "Thread identity changed from 0x"
                    + Long.toHexString(ident) + " to 0x"
                    + Long.toHexString(newIdent) + " while dispatching to "
                    + msg.target.getClass().getName() + " "
                    + msg.callback + " what=" + msg.what);
        }

        msg.recycleUnchecked();
    }
}

//简化一下
public static void loop() {
    final Looper me = myLooper();
    final MessageQueue queue = me.mQueue;
    for (;;) {   //是个无限循环，只要有消息，就不会退出
        Message msg = queue.next(); // ！！！！可能阻塞！！！！
		msg.target.dispatchMessage(msg);
        msg.recycleUnchecked();
    }
}
```


####处理消息

msg.target.dispatchMessage(msg);

target就是Handler

```java
//Handler里的方法
public void dispatchMessage(Message msg) {
    if (msg.callback != null) {
        handleCallback(msg); //message.callback.run();
    } else {
        if (mCallback != null) {
            if (mCallback.handleMessage(msg)) {
                return;
            }
        }
        handleMessage(msg);
    }
}
```
* 就看这个方法，其实有3个地方可以处理message，并且有优先级，前面会屏蔽后面的
    * 1 最高优先级，Message对象自己带的callback，其实就是创建Message对象时，传进个Runnable
    * 2 第二优先级，创建Handler对象时，传进个Callback对象，按源码意思，可以避免写Handler子类，这个如果返回true，表示消耗了message，不会往下走了
    * 3 第三优先级，Handler.handleMessage，也就是你继承Handler需要实现的那个方法，默认空实现


####延时时间是怎么回事，MessageQueue是个什么样的队列？

MessageQueue是个自定义消息队列，Message按照时间来排序，影响next方法

####Message的setAsynchronous是什么情况，有什么用

不知道


##4 AsyncTask分析


AsyncTask内部机制


####（1）首先：有个ThreadFactory，决定了需要新线程时，如何创建

```java
//注意，这部分在线程池里的作用是创建新线程，是否需要，就另说了
private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
```

####（2）然后，创建Executor：就是线程池


* 主要疑问：
    * 线程个数怎么定？
    * RxJava分为IO线程和CPU密集型线程，个数怎么定的？
    * AsyncTask为什么还做了个串行化的线程池，并作为默认？

```java
public static final Executor THREAD_POOL_EXECUTOR
	 = new ThreadPoolExecutor(
		CORE_POOL_SIZE, 
		MAXIMUM_POOL_SIZE, 
		KEEP_ALIVE,
		 TimeUnit.SECONDS, 
		sPoolWorkQueue, 
		sThreadFactory);

private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();  //有几个CPU
private static final int CORE_POOL_SIZE = CPU_COUNT + 1;    //线程池当前数量
private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;  //线程池最大数量
private static final int KEEP_ALIVE = 1;   //保持活动的线程
```
* 所以：
    * 对于双核电脑，最多有5个线程
	* CORE_POOL_SIZE：就是CPU个数，这有何意义？
	* 保持活动的就1个


####

`而AsyncTask又提供了一个Executor，就是串行化的线程池`

```java
public static final Executor SERIAL_EXECUTOR = new SerialExecutor();

private static class SerialExecutor implements Executor {
    final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
    Runnable mActive;

    public synchronized void execute(final Runnable r) {
        mTasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (mActive == null) {
            scheduleNext();
        }
    }

    protected synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
            THREAD_POOL_EXECUTOR.execute(mActive);
        }
    }
}
```

这个被设置为默认线程池：
`private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;`

可以设置默认线程池，但这个方法在文档中隐藏了？？？
```java
/** @hide */
public static void setDefaultExecutor(Executor exec) {
    sDefaultExecutor = exec;
}
```

所以AsyncTask基于一个5个线程的线程池，且又做了一层封装，实现串行执行，
即默认情况下任务是一个接一个的执行


###你调用execute方法时

`AsyncTask<Params, Progress, Result> execute(Params... params)`

```java
@MainThread
public final AsyncTask<Params, Progress, Result> execute(Params... params) {
    return executeOnExecutor(sDefaultExecutor, params);
}
```


（3）AsyncTask用法：

用法1：
自己实现doInBackground和onPostExecute()
调用方法execute

用法2：静态方法
public static void execute(Runnable runnable) {
    sDefaultExecutor.execute(runnable);
}

AsyncTask.execute(new Runnable(){});






