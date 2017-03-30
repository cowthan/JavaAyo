package com.cowthan.algrithm.lib;

public class lib {
/*

要研究算法，要用到的是java的一个很小的子集，这也是所有语言基本都有的一些特性

类型：
int及其算术运算符 32   + - *  / %
double及其算术运算符 64   + - *  /
bool及其逻辑操作   && || ! ^(异或，相同为false，不同为true)
char   16

其他：
long  64
short 16
char 16
byte 8
float 32

表达式：
普通表达式，就是中缀表达式
!优先级最高，&&次之，||优先级最低
比较运算符

类型转换：
(int)3.7

语句：
for
while
if
break
continue

数组：


二维数组

静态方法

方法

递归
- 总是要有一个最简单的情况，方法的开头总是包含return
- 问题规模一直缩小
- 递归的父问题和子问题之间，不应该有交集，否则会有效率问题（同一个规模的问题被多次计算）
- 递归便于评估程序性能

系统库：
Math
Integer
Double
String
StringBuilder
System
Arrays

Math库：
abs(double)
max(a, b)
min(a, b)

sin(theta)
cos(theta)
tan(theta)

Math.E  常数e
Math.PI 常数派

exp(double a)  e的n次方  指数函数
log(double a) lna，也就是log底为e 自然对数函数
pow(a, b)  a的b次方
random()  [0, 1)
sqrt(a)  a的平方根




标准库：
StdIn
StdOut
StdDraw
StdRandom
StdStats
In
Out

StdRandom：
各种分布，挺有意思

StdOut
关于printf的占位符
%d  int
%f  double
%e  double 按科学计数法输出
%s  字符串

%x.yd
x表示输出后的字符宽度
y对于double，表示保留小数位数，对于字符串，表示截断留几个字符

%5d  对于512，会输出"==512"
%-5d 对于512，会输出"512=="

%.2f 对于23.12345，会输出23.12


 */
	public static class array{
		
		/**
		 * 找出数组最大值
		 * @param arr
		 * @return
		 */
		public static double max(double[] arr){
			double max = arr[0];
			for (int i = 1; i < arr.length; i++) {
				if(arr[i] > max) max = arr[i];
			}
			return max;
		}
		
		public static double sum(double[] arr){
			double sum = 0;
			for (int i = 0; i < arr.length; i++) {
				sum += arr[i];
			}
			return sum;
		}
		
		public static double avg(double[] arr){
			return sum(arr) / arr.length;
		}
		
		/** 颠倒数组元素的顺序 */
		public static void reverse(double[] arr){
			int N = arr.length;
			for (int i = 0; i < arr.length; i++) {
				double temp = arr[i];
				arr[i] = arr[N-1-i];
				arr[N-1-i] = temp;
			}
		}
		
		/** 复制数组 */
		public static double[] copy(double[] src){
			int N = src.length;
			double[] dest = new double[N];
			for (int i = 0; i < N; i++) {
				dest[i] = src[i];
			}
			
			//System.arraycopy(src, 0, dest, 0, arr.length);
			
			return dest;
		}
		
		public static void remove(double[] src, int position){
			System.arraycopy(src, position + 1, src, position, src.length - position);
			//出来的数组，元素个数没变，这个需要注意
		}
		
		
		
		
		
	}
	
}

















