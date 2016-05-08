package com.cowthan.concurrent.c11;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {
	
	public interface Queue<E> extends Collection<E> {
	    boolean add(E e);
	    boolean offer(E e);

	    /**
	     * 移除并返回，空则抛异常
	     */
	    E remove();

	    /**
	     * 移除并返回，空则返回null
	     */
	    E poll();

	    /**
	     * 返回，不移除
	     */
	    E element();

	    /**
	     * 返回，并移除，空则返回null
	     */
	    E peek();
	}
	
	public interface BlockingQueue<E> extends Queue<E> {
		
		/**
		 * 添加，如果没有空间，会阻塞等待
		 * @param e
		 * @throws InterruptedException
		 */
		void put(E e) throws InterruptedException;
		
		/**
	     * 移除并返回，如果empty，则阻塞等待
	     */
	    E take() throws InterruptedException;
	    
	    /**
	     * 移除并返回，如果empty，会等待指定时间
	     * @param timeout
	     * @param unit
	     * @return
	     */
	    E poll(long timeout, TimeUnit unit);
	    
	    /**
	     * Returns the number of additional elements that this queue can ideally
	     * (in the absence of memory or resource constraints) accept without
	     * blocking, or {@code Integer.MAX_VALUE} if there is no intrinsic
	     * limit.
	     *
	     * <p>Note that you <em>cannot</em> always tell if an attempt to insert
	     * an element will succeed by inspecting {@code remainingCapacity}
	     * because it may be the case that another thread is about to
	     * insert or remove an element.
	     *
	     * @return the remaining capacity
	     */
	    int remainingCapacity();
	    
	    public boolean contains(Object o);
	    
	    /**
	     * 把队列里的元素都移到Collection里
	     * @param c
	     * @return
	     */
	    int drainTo(Collection<? super E> c);
	    int drainTo(Collection<? super E> c, int maxElements);
	    
	}
	
	public static class Message implements Delayed{

		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	public static void main(String[] args) {
		//java.util.concurrent.BlockingQueue<Message> queue = 
				//new ArrayBlockingQueue<BlockingQueueTest.Message>(10, true); //true是access policy，表示FIFO，先进先出
				//new LinkedBlockingDeque<BlockingQueueTest.Message>(10);
				//new DelayQueue<BlockingQueueTest.Message>();
				//new PriorityBlockingQueue<BlockingQueueTest.Message>();
				//new SynchronousQueue<BlockingQueueTest.Message>(true);
				//new LinkedTransferQueue<BlockingQueueTest.Message>();
				
	
	}

}
