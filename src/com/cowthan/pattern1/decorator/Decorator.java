package com.cowthan.pattern1.decorator;

/**
 * 用来装饰IPerson的实现类，Father，son，grandson这三个类都有人类的固定特性，并且
 * 有继承关系，现在想在继承来的能力之外，给这些能力添加点个性
 *
 */
public abstract class Decorator implements IPerson{
	 protected IPerson person;
	 
	 public Decorator(IPerson person){
		 this.person = person;
	 }
	 
	 @Override
	 public void speak() {
		
	 }

	 @Override
	 public void walk() {
		
	 }
}
