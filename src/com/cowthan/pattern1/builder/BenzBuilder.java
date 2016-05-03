package com.cowthan.pattern1.builder;

import java.util.ArrayList;

public class BenzBuilder extends CarBuilder{
	private Benz benz = new Benz();
	@Override
	public void setSequence(ArrayList<String> sequence) {
		this.benz.setSequence(sequence);
	}

	@Override
	public ICar getCar() {
		return this.benz;
	}
	
	//这是我自己加的代码，我觉得这样才对
	public ICar getCar(ArrayList<String> sequence){
		Benz benz = new Benz();
		benz.setSequence(sequence);
		return benz;
	}
}
