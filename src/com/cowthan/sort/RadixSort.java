package com.cowthan.sort;

public class RadixSort {
	/**
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 * 基数排序
	 * 二维数组构成桶
	 * 一维数组记录每个位存放的个数。
	 * 每次构建桶完成，拷贝数据到原来的数组中去。
	 * 继续下一轮桶的构建。
	 * 分别个位，十位，百位。。。
	 * 程序必须知道最大值的位数。
	 */
	public static void main(String[] args) {
		radixSort(Datas.data,3);
		Datas.prints("基数排序");
	}
	public static void radixSort(int[] data,int maxLen){
		//maxLen表示最大值的长度
		//LSD最低位优先排序	l从0开始 循环三次
		int k = 0;
		int n = 1;
		int[][] bucket = new int[10][data.length];//桶
		/**
		 * 表示桶的每一行也就是每一位存放的个数
		 */
		int[] orders = new int[10];
		int temp = 0;
		for (int l = 0; l < maxLen; l++) {
			for (int i = 0; i < data.length; i++) {
				temp = (data[i]/n)%10;
				bucket[temp][orders[temp]] = data[i];
				orders[temp]++;
			}
			//将桶中的数值保存会原来的数组中
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < orders[i]; j++) {
					if (orders[i]>0) {
						data[k]=bucket[i][j];
						k++;
					}
				}
				//拷贝完成清除记录的个数，设为0
				orders[i]=0;
			}
			//n乘以10 取十位  百位的数值
			n*=10;
			k=0;
			//k值记录拷贝数据到原有数组中的位置，拷贝完成恢复0
		}
	}
}