package com.cowthan.collection;

import java.util.LinkedList;

public class Stack<T> {
	
	private LinkedList<T> storage = new LinkedList<>();
	
	public void push(T v){
		storage.addFirst(v);
	}
	
	public T peek(){
		return storage.getFirst();
	}
	
	public T pop(){
		return storage.removeFirst();
	}
	
	public boolean empty(){
		return storage.isEmpty();
	}
	
	public String toString(){
		java.util.Stack<T> s = new java.util.Stack<>();
		return storage.toString();
	}

	
	public static void main(String[] args) {
		
		Stack<String> stack = new Stack<String>();
		for(String s: "My dog has fileas".split(" ")){
			stack.push(s);
		}
		
		while(!stack.empty()){
			System.out.println(stack.pop() + " ");
		}
		
		String str = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+'+r+u--+l+e+s--";
		for(int i = 0; i < str.length(); i++){
			//+表示入栈，-表示弹出栈顶
			char c = str.charAt(i);
			if(c == '+') continue;
			else if(c == '-'){
				System.out.print(stack.pop());
			}else{
				stack.push(c+"");
			}
		}
	}
}
