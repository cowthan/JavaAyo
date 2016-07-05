package com.cowthan.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T {
	public static void run(final Runnable task, final int maxCount, int intervalMillis){
		//final AtomicInteger atom = new AtomicInteger(0);
		final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
		es.scheduleAtFixedRate(new Runnable() {

			int count = 0;

			@Override
			public void run() {
				count++;
				System.out.println(count);
				if(count > maxCount){
					es.shutdown();
					return;
				}else{
					task.run();
				}
			}
		}, 0, intervalMillis, TimeUnit.MILLISECONDS);

	}
	public static void main(String[] args) {
		run(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}, 10, 300);
	}
}
