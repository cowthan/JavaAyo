package com.cowthan.pattern1.builder;

import java.util.ArrayList;

public class CarDirector {
	private ArrayList<String> sequence = new ArrayList<String>();
	private BenzBuilder benzBuilder = new BenzBuilder();
	private BMWBuilder bmwBuilder = new BMWBuilder();
	
	
	public Benz getBenz_a(){
		this.sequence.clear();
		this.sequence.add("start");
		this.sequence.add("stop");
		//this.benzBuilder.setSequence(this.sequence);
		return (Benz) this.benzBuilder.getCar(this.sequence);
	}
	
	public Benz getBenz_b(){
		this.sequence.clear();
		this.sequence.add("start");
		this.sequence.add("alarm");
		//this.benzBuilder.setSequence(this.sequence);
		return (Benz) this.benzBuilder.getCar(this.sequence);
	}
	
	public Benz getBenz_c(){
		this.sequence.clear();
		this.sequence.add("start");
		this.sequence.add("engine boom");
		//this.benzBuilder.setSequence(this.sequence);
		return (Benz) this.benzBuilder.getCar(this.sequence);
	}
	
	public BMW getBMW_a(){
		this.sequence.clear();
		this.sequence.add("start");
		this.sequence.add("stop");
		//this.bmwBuilder.setSequence(this.sequence);
		return (BMW) this.bmwBuilder.getCar(this.sequence);
	}
	
	///......
}
