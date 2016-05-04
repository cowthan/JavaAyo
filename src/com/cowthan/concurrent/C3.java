package com.cowthan.concurrent;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class C3 {

	public static class TaskWithResult implements Callable<String>{

		private int id = 0;
		public TaskWithResult(int id) {
			this.id = id;
		}
		
		
		@Override
		public String call() throws Exception {
			if(id == 2){
				Thread.sleep(1000);
			}else{
				Thread.sleep(3000);
			}
			return "result from callable--" + id;
		}
		
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(2);
		
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		results.add(exec.submit(new TaskWithResult(1)));
		results.add(exec.submit(new TaskWithResult(2)));
		results.add(exec.submit(new TaskWithResult(3)));
		
		for(Future<String> future: results){
			try {
				//会在这阻塞，也就是说在线程1上等待时，线程2的callable即使已经返回了，也不会立即拿到结果，必须等线程1的阻塞结束了，才会去看线程2
				String res = future.get();  
				System.out.println(res);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		exec.shutdown();
	}
	
}
