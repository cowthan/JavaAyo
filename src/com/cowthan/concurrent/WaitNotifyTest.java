package com.cowthan.concurrent;

public class WaitNotifyTest {
	
	public static final Object lock = new Object();
	
	public static class Wait1 implements Runnable{
		public volatile boolean canRun = false;
		
		public void run(){
			System.out.println("Wait-1 running...");
			synchronized (lock) {
				
				while(!canRun){
					try {
						System.out.println("Wait-1 waiting...");
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Wait-1 waiting over!");
				}
			}
			System.out.println("Wait-1: 跳出wait循环了");
		}
	}
	
	public static class Wait2 implements Runnable{
		public volatile boolean canRun = false;
		
		public void run(){
			System.out.println("Wait-2 running...");
			synchronized (lock) {
				
				while(!canRun){
					try {
						System.out.println("Wait-2 waiting...");
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Wait-2 waiting over!");
				}
			}
			System.out.println("Wait-2: 跳出wait循环了");
		}
	}
	
	public static void main(String[] args) {
		
		final Wait1 w1 = new Wait1();
		final Wait2 w2 = new Wait2();
		Thread t1 = new Thread(w1);
		Thread t2 = new Thread(w2);
		t1.start();
		t2.start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
