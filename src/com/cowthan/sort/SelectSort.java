package com.cowthan.sort;


public class SelectSort {

	/**
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 * 选择排序
	 * 思路:每次循环得到最小值的下标，然后交换数据。
	 * 如果交换的位置不等于原来的位置，则不交换。
	 */
	public static void main(String[] args) {
		selectSort(Datas.data);
		Datas.prints("选择排序");
	}
	public static void selectSort(int[] data){
		int index=0;
		for (int i = 0; i < data.length; i++) {
			index = i;
			for (int j = i; j < data.length; j++) {
				if (data[index]>data[j]) {
					index = j;
				}
			}
			if (index != i) {
				swap(data,index,i);
			}
		}
	}
	public static void swap(int[] data,int i,int j){
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
}