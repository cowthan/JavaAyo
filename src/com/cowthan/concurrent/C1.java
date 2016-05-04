package com.cowthan.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class C1 {
	
	public static class LiftOff1 implements Runnable{
		
		protected int countDown = 10;
		private static int taskCount = 0;
		private final int id = taskCount++;
		
		public LiftOff1(){}
		public LiftOff1(int countDown){
			this.countDown = countDown;
		}
		
		public String status(){
			return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + ")==>" + Thread.currentThread().getName();
		}

		@Override
		public void run() {
			while(countDown-- > 0){
				System.out.println(status());
				//Thread.yield();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		///~~~1 入门
//		LiftOff1 launch = new LiftOff1();
//		LiftOff1 launch2 = new LiftOff1();
//		LiftOff1 launch3 = new LiftOff1();
//		LiftOff1 launch4 = new LiftOff1();
//		LiftOff1 launch5 = new LiftOff1();
//		new Thread(launch, "thread-1").start();
//		new Thread(launch2, "thread-2").start();
//		new Thread(launch3, "thread-3").start();
//		new Thread(launch4, "thread-4").start();
//		new Thread(launch5, "thread-5").start();
//		System.out.println("Waiting for LiftOff==>" + Thread.currentThread().getName());
		
		///~~~2 Executor
//		ExecutorService exec = Executors.newCachedThreadPool(); //线程数总是会满足所有任务，所有任务都是并行执行，同时抢时间片，而旧线程会被缓存和复用
//		ExecutorService exec = Executors.newFixedThreadPool(2); //两个线程同时运行，其他的会排队等待
//		ExecutorService exec = Executors.newSingleThreadExecutor(); //1个线程同时运行，即多个任务会串行执行
//		ExecutorService exec = Executors.newWorkStealingPool(); 
//		ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
//		
//		//设置ThreadFactory：只有当需要新线程时，才会来这里调用，就是说ThreadFactory本身不管理线程池，只是给线程池干活的
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
		
		for(int i = 0; i < 5; i++){
			exec.execute(new LiftOff1()); //提交任务
		}
		
		//shutdown会关闭线程池入口，不能再提交新任务，但之前提交的，会正常运行到结束
		//如果不关闭，线程池会一直开着，等待提交任务
		exec.shutdown();  
	}

}
