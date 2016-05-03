package com.cowthan.sort;


public class InsertSort {
	/**
	 * 插入排序
	 * 思路：将数据插入到已排序的数组中。
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 */
	public static void main(String[] args) {
		int[] data = Datas.data;
		int temp;
		for (int i = 1; i < data.length; i++) {
			temp = data[i];//保存待插入的数值
			int j = i;
			for (; j>0 && temp<data[j-1]; j--) {
				data[j] = data[j-1];
				//如果带插入的数值前面的元素比该值大，就向后移动一位
			}
			//内部循环结束，找到插入的位置赋值即可。
			data[j]=temp;
		}
		Datas.prints("插入排序");
	}
}
