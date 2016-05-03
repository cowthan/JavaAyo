package com.cowthan.pattern1.decorator;

public class SpeakWoCaoDecorator extends Decorator{

	public SpeakWoCaoDecorator(IPerson person) {
		super(person);
	}

	private void speakWoCao(){
		System.out.println("装饰：80年代的人说话以卧槽结尾和开头");
	}
	
	@Override
	public void speak() {
		person.speak();
		speakWoCao();
	}
	
}
