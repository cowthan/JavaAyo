package com.cowthan.sort;


public class QuickSort {

	/**
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 * 快速排序
	 * 首先找到三数中值，然后分别移动左右两边的数据，
	 * 以中值数分割成两组，一组比中值数大，一组比中值数小。
	 * 然后递归快排两组数组。
	 * 当待排序的数组小于CUTOFF时，使用插入排序。
	 */
	public static void main(String[] args) {
		quickSort(Datas.data,0,Datas.data.length-1);
		Datas.prints("快速排序");
	}
	public static void quickSort(int[] data,int left,int right){
		int CUTOFF = 1;
		if (left+CUTOFF<right) {
			//找到中值数
			int media = media3(data, left, right);
			//保存左右界，left,right值不变
			int i =left;
			int j = right-1;
			//循环移动左右两边的元素
			while (true) {
				while(data[++i]<media);
				while (data[--j]>media);
				if (i>j) {
					break;
				}
				swap(data, i, j);
			}
			//将中值数移动到i处。中值数即排在i处。
			swap(data, i, right-1);
			//递归排序中值数两边的数据
			quickSort(data, left, i-1);
			quickSort(data, i+1, right);
		}else {
			/**
			 * 插入排序
			 * 当待排序的元素少于20个时候，
			 * 快速排序性能不如直接插入排序好。
			 * 所以else语句里面，在待排序基本有序的情况下
			 * 可以使用直接插入排序更好。
			 */
			InsertSort.main(null);
		}
	}
	//找到中值数
	public static int media3(int[] data,int left,int right){
		int center = (left+right)/2;
		/**
		 * 前两个if语句的比较，
		 * 使得最小值放在最左边。
		 */
		if (data[center]<data[left]) {
			swap(data, center, left);
		}
		if (data[right]<data[left]) {
			swap(data, right, left);
		}
		/**
		 * 第三个if语句使得最大值放在最右边。
		 * 中间值，放在中间位置。
		 */
		if (data[right]<data[center]) {
			swap(data, right, center);
		}
		//把中间的位置放在right-1的位置。
		swap(data, center, right-1);
		return data[right-1];
	}
	//交换数据
	public static void swap(int[] data,int i,int j){
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

}
