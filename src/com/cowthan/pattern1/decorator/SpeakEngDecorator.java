package com.cowthan.pattern1.decorator;

public class SpeakEngDecorator extends Decorator{

	public SpeakEngDecorator(IPerson person) {
		super(person);
	}

	private void speakEng(){
		System.out.println("装饰：80年代的人学英语");
	}
	
	@Override
	public void speak() {
		speakEng();
		person.speak();
	}
	
}
