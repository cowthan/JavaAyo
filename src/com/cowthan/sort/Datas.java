package com.cowthan.sort;

public class Datas {
	/**
	 * ���ݹ�����
	 */
	public static int[] data = new int[]{
		30,1,45,5,89,9,0,54,34,2,25,56,8,
		76,200,90,33,25,25,25,23,333,22,12,80
	};
	public static double[] datad = new double[]{
		0.30,0.1,0.45,0.5,0.89,0.9,0.0,0.54,0.34,0.2,0.25,0.56,0.8,
		0.76,0.200,0.90,0.33,0.25,0.25,0.25,0.23,0.333,0.22,0.12,0.80
	};
	static String result = "0,1,2,5,8,9,12,22,23,25,25,25,25,30,33,34,45,54,56,76,80,89,90,200,333";
	public static void prints(String str){
		System.out.println(str);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]+",");
			sb.append(data[i]+",");
		}
		System.out.println();
		sb.deleteCharAt(sb.length()-1);
		System.out.println("���=\r\n"+sb);
		if (sb.toString().equals(result)) {
			System.out.println(true);
		}
	}
}
