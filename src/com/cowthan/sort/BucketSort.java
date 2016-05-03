package com.cowthan.sort;

import java.util.ArrayList;
import java.util.Iterator;


public class BucketSort {
	 
    /** 
     * 桶排序算法，对arr进行桶排序，排序结果仍放在arr中 
     * @param arr 
     * 我的博客地址：http://blog.csdn.net/u010156024/article/details/48932219
     */  
	public static void main(String[] args){
		bucketSort(Datas.datad);
		for (int i = 0; i < Datas.datad.length; i++) {
			System.out.println(Datas.datad[i]+",");
		}
	}
    public static void bucketSort(double arr[]){  
         
        int n = arr.length;
         
        ArrayList<Double> arrList[] = new ArrayList[n];
        //把arr中的数均匀的的分布到[0,1)上，每个桶是一个list，存放落在此桶上的元素   
        for(int i =0;i<n;i++){
            int temp = (int) Math.floor(n*arr[i]);
            if(null==arrList[temp])
                arrList[temp] = new ArrayList<>();
            arrList[temp].add(arr[i]);
        }  
         
        //对每个桶中的数进行插入排序   
        for(int i = 0;i<n;i++){  
            if(null!=arrList[i])  
                insert(arrList[i]);  
        }  
         
        //把各个桶的排序结果合并   
        int count = 0; 
         
        for(int i = 0;i<n;i++){  
            if(null!=arrList[i]){  
                Iterator<Double> iter = arrList[i].iterator();  
                while(iter.hasNext()){  
                    Double d = (Double)iter.next();  
                    arr[count] = d;
                    count++;  
                }  
            }  
        }  
    }  
     
    /** 
     * 用插入排序对每个桶进行排序 
     * @param list 
     */  
    public static void insert(ArrayList<Double> list){  
        if(list.size()>1){  
            for(int i =1;i<list.size();i++){  
                if((Double)list.get(i)<(Double)list.get(i-1)){  
                    double temp = (Double) list.get(i);  
                    int j = i-1;  
                    for(;j>=0&&((Double)list.get(j)>(Double)list.get(j+1));j--)  
                        list.set(j+1, list.get(j));  
                    list.set(j+1, temp);  
                }
            }
        }
    }
}
