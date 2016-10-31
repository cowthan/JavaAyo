package com.cowthan.annotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class AnnotationProcessorTest {
	
	public static class AnnotationProcessor1 extends AbstractProcessor{

		@Override
		public boolean process(Set<? extends TypeElement> annotations,
				RoundEnvironment roundEnv) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	public static void main(String[] args) {
		
	}

}
