package com.cowthan.pattern1.builder;

import java.util.ArrayList;

public abstract class CarBuilder {
	public abstract void setSequence(ArrayList<String> sequence);
	public abstract ICar getCar();
	public abstract ICar getCar(ArrayList<String> sequence);//这是我自己加的代码，我觉得这样才对
}
