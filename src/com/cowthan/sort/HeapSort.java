package com.cowthan.sort;


public class HeapSort {

	/**
	 * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
	 * 堆排序(就是优先队列)
	 * 也就是完全二叉树
	 * 第一步：建堆.其实就是讲数组中的元素进行下虑操作，
	 * 		使得数组中的元素满足堆的特性。
	 * 第二步：通过将最大的元素转移至堆的末尾,
	 * 		然后将剩下的元素在构建堆。
	 * 		完成排序。
	 * 最重要的过程就是构建堆的过程。
	 * 里面的比较思路和希尔排序中的比较思路一致。
	 * 将大的元素上浮，小的元素下浮。始终和temp比较。
	 * temp除了第一次比较可能改变外，其他次数的比较不改变该值。
	 * 这样的处理就是让较大的元素趋于上浮，较小的元素下浮。
	 */
	public static void main(String[] args) {
		int[] data = Datas.data;
		for (int i = data.length/2; i >=0; i--) {
			buildHeap(data,i,data.length);
		}
		Datas.prints("堆排序-构建树");
		System.out.println("============================");
		for (int i = data.length-1; i>0; i--) {
			swap(data, 0, i);
			buildHeap(data, 0, i);
		}
		Datas.prints("堆排序-排序后");
	}
	static void swap(int[] data,int i,int j){
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	static void buildHeap(int[] data,int i,int len){
		int leftChild = leftChild(i);
		int temp = data[i];
		for (; leftChild<len;) {
			if (leftChild != len-1 && data[leftChild]<data[leftChild+1]) {
				leftChild++;
			}
			if (temp<data[leftChild]) {
				data[i] = data[leftChild];
			}else {
				/**braek说明两个儿子都比父节点小，
				* 父节点大于两个儿子
				* 所以直接停止比较，减小比较的次数。
				*/
				break;
			}
			i = leftChild;
			leftChild = leftChild(i);
		}
		data[i] = temp;
	}
	//返回节点i的左儿子的index
	static int leftChild(int i){
		return 2*i+1;
	}
}