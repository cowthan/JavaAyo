package com.cowthan.serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/*

这里的场景是：对象通过无参构造器被创建之后，并无法直接投入工作（x和y没有效值），
但用户又必须通过initailize来将对象设置到可运行状态。
但在无参构造器和initailize方法之间，其他线程可能会来访问对象，这是一种危险情况。

下面用了一个原子引用来保证了对象的完整性，是一个很好的"线程安全状态机"

 */
public class AbstractFoo {
	
	private int x, y;
	
	private enum State{ NEW, INITIALIZING, INITIALIZED};
	private final AtomicReference<State> init = new AtomicReference<AbstractFoo.State>();
	
	protected AbstractFoo() {}
	
	protected final void initailize(int x, int y){
		if(!init.compareAndSet(State.NEW, State.INITIALIZING)){
			throw new IllegalStateException("Already initialized");
		}
		this.x = x;
		this.y = y;
		init.set(State.INITIALIZED);
	}
	
	protected final int getX(){ checkInit(); return x;}
	protected final int getY(){ checkInit(); return y;}
	
	private void checkInit(){
		if(init.get() != State.INITIALIZED){
			throw new IllegalStateException("Uninitialized");
		}
	}
	

}

//一个可序列化的子类
class Foo extends  AbstractFoo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException{
		s.defaultReadObject();
		
		//手动反序列化父类状态
		int x = s.readInt();
		int y = s.readInt();
		initailize(x, y);
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException{
		s.defaultWriteObject();
		
		//手动序列化父类状态
		s.writeInt(getX());
		s.writeInt(getY());
	}
	
	//Constructor does not use the fancy mechanism
	public Foo(int x, int y){ super.initailize(x, y); }
}
