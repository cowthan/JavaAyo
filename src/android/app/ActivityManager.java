package android.app;

import java.util.Stack;

import android.os.Handler;
import android.os.Looper;

public class ActivityManager {

	private static class SingleHolder{
		static ActivityManager am = new ActivityManager();
	}
	
	public static ActivityManager getDefault() {
		return SingleHolder.am;
	}
	
	private ActivityManager(){
		mHandler = new Handler(Looper.getMainLooper());
		stack = new Stack<Activity>();
	}
	
	private Handler mHandler;
	private Stack<Activity> stack;  //安卓里应该不是用stack，安卓的activity栈里，能拿出，但你不能把activity删了
	
	public void startActivity(Activity a){
		stack.push(a);
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				Activity a = nextActivity();
				if(a != null){
					a.onCreate();
				}
			}
		});
		
	}
	
	public Activity nextActivity(){
		if(stack.isEmpty()) return null;
		return stack.pop();
	}

}
