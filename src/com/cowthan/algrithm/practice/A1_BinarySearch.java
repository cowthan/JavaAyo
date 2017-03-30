package com.cowthan.algrithm.practice;

import java.util.Arrays;

public class A1_BinarySearch {
	
	public static int rank(int key, int[] a){
		int lo = 0;
		int hi = a.length - 1;
		while(lo <= hi){
			int mid = lo + (hi - lo) / 2;
			if(key < a[mid]) hi = mid - 1;
			else if(key > a[mid]) lo = mid + 1;
			else return mid;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] whitelist = new int[]{5, 4, 23, 43, 2, 12, 343, 2222, 1, 2, 3, 4, 5, 6};
		Arrays.sort(whitelist);
		int key = 1212;
		
		int index = rank(key, whitelist);
		if(index != -1){
			System.out.println("find " + key + " in: " + index);
		}else{
			System.out.println(key + " not found");
		}
	}
	
}
