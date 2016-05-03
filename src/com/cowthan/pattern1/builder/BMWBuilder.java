package com.cowthan.pattern1.builder;

import java.util.ArrayList;

public class BMWBuilder extends CarBuilder{
	private BMW bmw = new BMW();
	@Override
	public void setSequence(ArrayList<String> sequence) {
		this.bmw.setSequence(sequence);
	}

	@Override
	public ICar getCar() {
		return this.bmw;
	}
	
	public ICar getCar(ArrayList<String> sequence){
		BMW bmw = new BMW();
		bmw.setSequence(sequence);
		return bmw;
	}
}
