package com.cowthan.sort;


public class ShellSort {

	/**
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 * 希尔排序（缩减增量排序）
	 * 想想也不难。
	 * 思路：三层循环
	 * 第一层循环：控制增量-增量随着程序的进行依次递减一半
	 * 第二层循环：遍历数组
	 * 第三层循环：比较元素，交换元素。
	 * 这里需要注意的是：比较的两个元素和交换的两个元素是不同的。
	 */
	public static void main(String[] args) {
		int[] data = Datas.data;
		int k;
		for (int div = data.length/2; div>0; div/=2) {
			for (int j = div; j < data.length; j++) {
				int temp = data[j];
				for (k=j; k>=div && temp<data[k-div] ; k-=div) {
					data[k] = data[k-div];
				}
				data[k] = temp;
			}
		}
		Datas.prints("希尔排序");
	}
}
