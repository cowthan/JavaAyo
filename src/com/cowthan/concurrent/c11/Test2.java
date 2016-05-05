package com.cowthan.concurrent.c11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {
	
	
	public static class Waiter implements Runnable{
		
		@Override
		public void run() {
			synchronized (this) {
				System.out.println("我拿到锁了，并且wait了，锁就释放了，并且等待锁");
				try {
					wait();
					System.out.println("wait拿到锁，返回了");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Waiter w = new Waiter();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(w);
		exec.shutdown();
		Thread.sleep(2000);
		synchronized (w) {
			System.out.println("拿到锁了");
			Thread.sleep(2000);
			w.notify();
			Thread.sleep(2000);
			System.out.println("notify了，notify不会释放锁，走到同步代码最后才释放锁");
		}
		
	}
	
}
