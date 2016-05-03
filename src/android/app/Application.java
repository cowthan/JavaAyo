package android.app;

import android.os.Handler;
import android.os.Looper;

public class Application {

	private static class SingleHolder{
		static Application app = new Application();
	}
	
	public static Application getDefault() {
		return SingleHolder.app;
	}
	
	public void start(){
		Handler h = new Handler(Looper.getMainLooper());
		h.post(new Runnable() {
			
			@Override
			public void run() {
				onCreate();
			}
		});
	}
	
	
	
	///---------------------你的逻辑写在这
	public void onCreate(){
		System.out.println(Thread.currentThread().getName());
		System.out.println("app入口被调用了");
		
		Handler h = new Handler(Looper.getMainLooper());
		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("延迟发出来的消息被处理 了");
			}
		}, 3000);
		
		//
	}
	
	
}
