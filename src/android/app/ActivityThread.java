package android.app;

import android.os.Looper;

/**
 *  理想情况下，这个类是仿安卓的ActivityThread，当然不用那么多功能，在这里主要就是：
 *  1 程序入口
 *  2 准备大循环，就是开启Looper，并记录ThreadLocal
 *  3 初始化Async相关库，在安卓里是初始化了AsyncTask
 *  4 开始大循环
 *
 */
public class ActivityThread {

	public static void main(String[] args) {
		
		//系统初始化
		
		
		//准备大循环
		Looper.prepareMainLooper();
	
		
		//系统初始化2：大循环是开了，但谁给大循环发消息呢？？这他妈是个问题
		/*
		 可以这么想：无所谓对错，思路大体可以就行
		 ——大循环开了之后，第一个消息是什么？是Application的初始化
		 ——然后开启第一个Activity
		 
		 在这里虽然有个ActivityManager，但并不具备任务栈管理的功能
		 
		 所以：
		 1 这个假安卓只让处理一个Activity，多了虽然也没事，但并没有任何机制可以让你像用户操作手机一样在Activity之间切换
		 2 每个主循环对应一个Application对象，Application对象接口给用户
		 3 你的逻辑直接写在Application里就行，也不用继承什么，因为没有一个binder机制，和远程的ActivityManager，WindowManager等
		 东西，所以没法通过远端给主循环发消息，只能强制加载一个入口，就是Application.onCreate()
		 
		 最终决定，Activity在这里是多余的，去掉
		 
		 所以注意：Activity和ActivityManager这两个类暂时没用，请将你的所有逻辑写在Application里
		 
		 */
		Thread.currentThread().setName("main-thread");
		ActivityThread thread = new ActivityThread();
		thread.attach(false);
		Application.getDefault().start();  //这里往队列发了一条消息，loop开始后会轮询到这条消息
		
		//开启大循环
		Looper.loop();
		throw new RuntimeException("Main thread loop unexpectedly exited");
	}
	
	
	private ActivityManager mgr;
	private void attach(boolean system){
		
		//初始化WindowManager
		
		//初始化ActivityManager
		mgr = ActivityManager.getDefault();
		
	}
	
}
