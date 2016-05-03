package com.cowthan.pattern1.builder;

public class Director_Client {
	
	public static void main(String[] args) {
		CarDirector director = new CarDirector();
		
		for(int i = 0; i < 2; i++){
			director.getBenz_a().run();
		}
		for(int i = 0; i < 2; i++){
			director.getBenz_c().run();
		}
		for(int i = 0; i < 2; i++){
			director.getBMW_a().run();
		}
		
	}
	
}
