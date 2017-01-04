package com.cowthan.type_parameter;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		List<Animal> animals;
		List<Dog> dogs = new ArrayList<Dog>();
		System.out.println(new TypeToken<List<Animal>>(){}.getType());
	}
	
}
