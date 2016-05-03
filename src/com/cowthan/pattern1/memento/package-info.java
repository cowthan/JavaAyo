/**
 *  备忘录模式：
 *  对象状态能够回滚到之前的某个时间点，
 *  在不破坏封装的前提下，捕获一个对象的状态，并在该对象之外保存这个状态，
 *  这样以后就可以恢复该对象到保存的状态
 *  
 *  从定义就能看出最大的问题就是如何实现状态的存储
 *  
 *  先设想对于一个类Boy，你要记录其历史状态以便回滚，方式有2：
 *  1、就由你来调用boy，使用另一个对象记录其状态，然后进行动作，适时回滚，
 *  这样的设计就是将备忘的功能交给你来做，不太合理
 *  2、改变Boy的定义，让其自己控制备忘，这样也不太好，因为违反了单一职责的原则
 *  结论：使用一个单独的类，备忘录，在boy中提供create和restore的方法，高层只需要
 *  得到备份的对象Memento，并拿着这个对象去还原，但这样也不行，迪米特法则说只与
 *  你需要交流的人交流，高层确实需要和Memento交流吗？
 *  不用，高层模块只需要告诉boy去备份和恢复，至于中间对象是什么我们不关心
 *  解决：
 *  ——不能在boy中维护Memento对象，为什么，不知道
 *  ——现在需要一个备忘录管理员的角色，每次高层模块想备份boy时，就去找
 *  管理员就行，这个比较合理
 *  
 *  具体看代码吧
 *  
 *  备忘录模式的意义在于让后悔药在程序代码中可行，弥补了真实世界的缺陷
 *  
 *  
 *  备忘录模式的扩展和变形：
 *  1、clone：通过clone保存对象状态，简单而直接，具体参考原型模式
 *  2、在对象内部维护自己的备忘录对象，省掉一些类的定义
 *  3、多个状态需要保存时，使用BeanUtils工具把这些状态映射成map，存在
 *  Memento里，自己上网去找一些这样的工具，appache工具集commons就有
 *  
 *  此时Memento的成员变成了：
 *  private HashMap<String, Object> statemap;
 *  ——由这个保存所有状态
 *  
 *  4、多个备份：
 *  ——这个就需要改MementoMgmr这个类，改成map，提供个key，可以是时间点或其他
 *  private HashMap<String, Memento> memMap;
 *  
 *  扩展：
 *  例子中Boy提供的create和restore，如果boy中所有需要备份的状态都是可访问的
 *  ，这两个方法就放到MementoMgmr中应该更好
 *  ——就算不是可访问的，也可以通过BeanUtils进行暴力反射
 *  
 *  深入：
 *  备份数据的意义：备份的数据是完全的，绝对不能修改的，并且可访问性也是应该
 *  收到限制的，应该将其可能被污染的可能性降到最低，需要缩小备份数据的阅读权限，
 *  应该只有发起人能够读，也就是被备份的对象，要做到这一点，就需要：
 *  ——内置类： private class，这个就推翻了上面扩展那部分的分析，create和restore还是
 *  要在boy类中，至于为什么，看代码：
 *  
 *  双接口：
 *  1、窄接口：先提供一个空接口，IMemento，给外部容器用，也就是MementoMgmr，代码中可以看出
 *  MementoMgmr只需要空接口就行了，这就是双接口设计，这个空接口叫做窄接口，提供给外部，
 *  相对比较安全（不可能绝对安全，可以通过暴力反射，但不考虑这个）
 *  
 *  2、宽接口：这个在Boy里面实现，并作为private的内部类，只给Boy访问，提高安全性
 *  class Boy{
 *  	....
 *  
 *  	//==备忘录相关：
 *  	createMemento(){...}
 *  	restore(){...}
 *  
 *  	//==宽接口：
 *  	private class Memento implements IMemento{
 *  
 *  		private String state = "";  //记录的状态
	
			public Memento(String state){
				this.state = state;
			}
			public String getState(){
				return state;
			}
			public void setState(String state){
				this.state = state;
			}
 *  	}
 *  }
 *  ——合情合理的设计
 *  
 *  备忘录模式至少比使用数据库临时表作为缓存的效率高，没有把
 *  压力都放到数据库
 *  
 */
package com.cowthan.pattern1.memento;