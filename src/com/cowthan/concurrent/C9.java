package com.cowthan.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 线程本地存储 
 *
 */
public class C9 {

	public static class Accessor implements Runnable{
		
		private final int id;
		public Accessor(int idn){
			id = idn;
		}
		
		@Override
		public void run() {
			
			while(!Thread.currentThread().isInterrupted()){
				ThreadLocalVariableHolder.increment();
				System.out.println(this);
				Thread.yield();
			}
			
		}
		
		public String toString(){
			return "#" + id + ": " + ThreadLocalVariableHolder.get();
		}
	}
	
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
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++){
			exec.execute(new Accessor(i));
		}
		java.util.concurrent.TimeUnit.SECONDS.sleep(3);
		exec.shutdownNow();
	}
	
}
