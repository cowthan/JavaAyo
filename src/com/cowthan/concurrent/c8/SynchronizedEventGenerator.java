package com.cowthan.concurrent.c8;

public class SynchronizedEventGenerator extends IntGenerator {
	private int currentEvenValue = 0;

//	public synchronized int next() {
//		++currentEvenValue; // Danger point here!
//		++currentEvenValue;
//		return currentEvenValue;
//	}
	
	public int next() {
		synchronized (this) {
			++currentEvenValue; 
			++currentEvenValue;
			return currentEvenValue;
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEventGenerator());
	}
}
