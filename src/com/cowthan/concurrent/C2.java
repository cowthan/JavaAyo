package com.cowthan.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Executor深入分析
 *
 */
public class C2 {
	
	public static class MyExecutors{
		
		public static Executor newDirectThreadPool(){
			return new DirectExecutor();
		}
		
		public static Executor newPerTaskPerThreadThreadPool(){
			return new ThreadPerTaskExecutor();
		}
		
		public static Executor newSerialThreadPool(){
			return new SerialExecutor(new DirectExecutor());
		}
	}
	
	/**
	 * an executor can run the submitted task immediately in the caller's thread
	 */
	public static class DirectExecutor implements Executor {
	    public void execute(Runnable r) {
	    	r.run();
	    }
	}
	
	/**
	 * spawns a new thread for each task
	 */
	public static class ThreadPerTaskExecutor implements Executor {
	    public void execute(Runnable r) {
	    	new Thread(r).start();
	    }
	}
	
	/**
	 * serializes the submission of tasks to a second executor
	 * 类似安卓的AsyncTask里的串行化实现
	 */
	public static class SerialExecutor implements Executor {
	    final Queue<Runnable> tasks = new ArrayDeque<>();
	    final Executor executor;
	    Runnable active;
	 
	    SerialExecutor(Executor executor) {
	      this.executor = executor;
	    }
	 
	    public synchronized void execute(final Runnable r) {
	      tasks.add(new Runnable() {
	        public void run() {
	          try {
	            r.run();
	          } finally {
	            scheduleNext();
	          }
	        }
	      });
	      if (active == null) {
	        scheduleNext();
	      }
	    }
	 
	    protected synchronized void scheduleNext() {
	      if ((active = tasks.poll()) != null) {
	        executor.execute(active);
	      }
	    }
	  }
	
	public static void main(String[] args) {
		Runnable task = new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("running on thread " + Thread.currentThread().getName());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		MyExecutors.newDirectThreadPool().execute(task);
		MyExecutors.newPerTaskPerThreadThreadPool().execute(task);
		MyExecutors.newSerialThreadPool().execute(task);
		
	}
	
}
