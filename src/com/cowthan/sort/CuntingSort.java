package com.cowthan.sort;


public class CuntingSort {

	/**
	 * 计数排序
	 * 思路：构建一个与待排序中最大值相同大小的数组，
	 * 该数组存放待排序数组中每个数字出现的个数。
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 */
	public static void main(String[] args) {
		cunting(Datas.data, 333);
		Datas.prints("计数排序");
	}

	public static void cunting(int[] data,int max){
		int[] temp = new int[max+1];
		int[] result = new int[data.length];
		/**
		 * 该循环设置初始值为0
		 */
		for (int i = 0; i < temp.length; i++) {
			temp[i]=0;
		}
		/**
		 * 该for语句循环遍历原数组，将数组中元素出现的个数存放在
		 * temp数组中相对应的位置上。
		 * temp数组长度与最大值的长度一致。保证每个元素都有一个对应的位置。
		 */
		for (int i = 0; i < data.length; i++) {
			temp[data[i]]+=1;
		}
		/**
		 * 累计每个元素出现的个数。
		 * 通过该循环，temp中存放原数组中数据小于等于它的个数。
		 * 也就是说此时temp中存放的就是对应的元素排序后，在数组中存放的位置+1。
		 */
		for (int i = 1; i < temp.length; i++) {
			temp[i]=temp[i]+temp[i-1];
		}
		/**
		 * 这里从小到大遍历也可以输出正确的结果，但是不是稳定的。
		 * 只有从大到小输出，结果才是稳定的。
		 * result中存放排序会的结果。
		 */
		for (int i = data.length-1; i>=0; i--) {
			int index = temp[data[i]];
			result[index-1]= data[i];
			temp[data[i]]--;
		}
		Datas.data = result;
	}
}
