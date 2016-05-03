package android.os;

public class Looper {
	
	public static void prepare(){
		prepare(true);
	}
	
	public static void prepare(boolean quitAllowed){
		if (sThreadLocal.get() != null) {
            throw new RuntimeException("一个线程只能有一个looper");
        }
        sThreadLocal.set(new Looper(quitAllowed));
	}
	
	public static void prepareMainLooper(){
		prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("已经有了一个main looper了");
            }
            sMainLooper = myLooper();
        }
	}
	
	public static Looper myLooper(){
		return sThreadLocal.get();
	}
	
	public static Looper mainLooper(){
		synchronized (Looper.class) {
            return sMainLooper;
        }
	}
	
	public static Looper getMainLooper(){
		synchronized (Looper.class) {
            return sMainLooper;
        }
	}
	
	public static void loop(){
		final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;

        // Make sure the identity of this thread is that of the local process,
        // and keep track of what that identity token actually is.
        //Binder.clearCallingIdentity();
        //final long ident = Binder.clearCallingIdentity();

        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }

            // This must be in a local variable, in case a UI event sets the logger
            //Printer logging = me.mLogging;
//            if (logging != null) {
//                logging.println(">>>>> Dispatching to " + msg.target + " " +
//                        msg.callback + ": " + msg.what);
//            }

            msg.target.dispatchMessage(msg);

//            if (logging != null) {
//                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
//            }

            // Make sure that during the course of dispatching the
            // identity of the thread wasn't corrupted.
//            final long newIdent = Binder.clearCallingIdentity();
//            if (ident != newIdent) {
//                Log.wtf(TAG, "Thread identity changed from 0x"
//                        + Long.toHexString(ident) + " to 0x"
//                        + Long.toHexString(newIdent) + " while dispatching to "
//                        + msg.target.getClass().getName() + " "
//                        + msg.callback + " what=" + msg.what);
//            }

            msg.recycleUnchecked();
        }
	}
	

	
	//---------------------------------------------
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
    private static Looper sMainLooper;  // guarded by Looper.class

    final MessageQueue mQueue;
    final Thread mThread;

	
	private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }
	
	
}
