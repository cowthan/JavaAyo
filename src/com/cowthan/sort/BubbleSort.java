package com.cowthan.sort;


public class BubbleSort {

	/**
	 * 冒泡排序
	 * 思路：内部循环每走一趟排好一位，依次向后排序
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 */
	public static void main(String[] args) {
		bubbleSort(Datas.data);
	}

	private static void bubbleSort(int[] data) {
		int temp;
		for (int i = 0; i < data.length; i++) {
			for (int j = i+1; j < data.length; j++) {
				if (data[i]>data[j]) {
					temp =data[i];
					data[i]=data[j];
					data[j] = temp;
				}
			}
		}
		Datas.prints("冒泡排序");
	}
}
