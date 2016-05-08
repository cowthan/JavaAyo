package com.cowthan.concurrent.c10;

public class NothingToInterrupt {
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println("go on...");
				}
			}
		});
		
		t.start();
		
		Thread.sleep(2000);
		t.interrupt();
		
		while(true){
			Thread.yield();
		}
	}
	
	

}
