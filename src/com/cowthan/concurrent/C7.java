package com.cowthan.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class C7 {
	
	public static class Sleeper implements Runnable{

		@Override
		public void run() {
			System.out.println("Sleeper：我先睡个5秒...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("Sleeper---谁他妈吵醒我！");
			}
			System.out.println("Sleeper：我醒了!");
		}
	}
	
	public static class Guest implements Runnable{
		
		Thread sleeperThread;
		
		public Guest(Thread sleeperThread){
			this.sleeperThread = sleeperThread;
		}
		
		@Override
		public void run() {
			System.out.println("Guest：我是客人，我在这坐着等Sleeper醒来再说话");
			try {
				sleeperThread.join();
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("Guest：我得等他睡醒了啊！");
			}
			System.out.println("Guest：这哥终于睡醒了！");
		}
		
	}
	
	public static void main(String[] args) {
		Thread sleeper = new Thread(new Sleeper());
		Thread guest = new Thread(new Guest(sleeper));
		sleeper.start();
		guest.start();
		
		///注掉下面这段，则sleeper会睡5秒，不注掉，3秒后就会打断sleeper的睡眠：被interrupt的是sleep方法
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
//		exec.schedule(new Runnable() {
//			public void run() {
//				sleeper.interrupt();
//			}
//		}, 3, java.util.concurrent.TimeUnit.SECONDS);
//		
//		///注掉下面这段，则guest会等5秒，不注掉，2秒后就会打断等待：被interrupt的是join方法
		exec.schedule(new Runnable() {
			public void run() {
				guest.interrupt();
			}
		}, 2, java.util.concurrent.TimeUnit.SECONDS);
	}
	
}
