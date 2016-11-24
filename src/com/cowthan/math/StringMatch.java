package com.cowthan.math;

import org.ayo.lang.Lang;

/**
 * 子串查找，模式匹配
 *
 */
public class StringMatch {
	
	/**
	 * 朴素匹配，遍历source，遇到miss match，就回退到上一轮的下一个字符
	 * 也可以理解为：拿着pattern，往source上挨个贴，贴一下，就挨个字符判断match，miss match了，就贴source的下一个
	 * KMP算法和朴素匹配的区别就在于，遇到miss match了，不需要贴source的下一个，可以跳过几个
	 * 主要问题就在于跳过几个怎么能知道
	 * @param source
	 * @param pattern
	 * @param pos
	 * @return
	 */
	public static int index(String source, String pattern, int pos){
		
		int i = pos;
		int j = 0;
		while(i < source.length() && j < pattern.length()){
			if(source.charAt(i) == pattern.charAt(j)){
				i++;
				j++;
			}else{
				i = i - j + 1;
				j = 0;
			}
		}
		if(j == pattern.length()){
			return i - j;
		}
		return -1;
		
	}
	
	/*
	 KMP
--------------------------------------------------------------------------------------------------------	 
1	 2	 3	 4	 5	 6	 7	 8	 9	10	11	12	13	14	15	16	17	18	19	20	21	22	23	24	25	26
b	a	b	c	b	a	b	c	a	b	c	a	a	b	c	a	b	c	a	b	c	a	c	a	b	c
a	b	c	a	b	c	a	c	a	b	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 
 	a	b	c	a	b	c	a	c	a	b	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 
 	 	 	 	 	a	b	c	a	b	c	a	c	a	b	 	 	 	 	 	 	 	 	 	 	 
 	 	 	 	 	 	 	 	a	b	c	a	b	c	a	c	a	b	 	 	 	 	 	 	 	 
 	 	 	 	 	 	 	 	 	 	 	 	a	b	c	a	b	c	a	c	a	b	 	 	 	 
 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	a	b	c	a	b	c	a	c	a	b	 
--------------------------------------------------------------------------------------------------------

http://www.cnblogs.com/c-cloud/p/3224788.html
http://www.cnblogs.com/yjiyjige/p/3263858.html
http://www.cnblogs.com/tangzhengyue/p/4315393.html

KMP要点：
pattern串必须匹配到几个相等的字符了，KMP才有意义，如果pattern的第一个字符和source的所有字符都不匹配，
那每轮都往前跳一步，KMP就没有意义了
假设此时source的i是1，已经匹配到相等的前4个字符（1,2,3,4），到第5个字符开始不相等了
所以我们就知道了，如果pattern跳一步（跳到i是2），那souce串的前3个字符（2,3,4），就是pattern串的第（2,3,4）个字符
既然都知道了，那怎么利用起来呢， 这就是KMP要解决的问题
此时的状态：
1 2 3 4 5 a b c d e f g h i j k l m n
          a b c d m n p

你知道source里这有个abcd的子串了，根据这个已知条件，
你去pattern里看看，对于abcd来说
前缀： a ab abc
后缀： d cd bcd
都不相等，所以next是0
已匹配的字符数是4，所以可以移动4-0=4
------为什么，这里可以用通俗的语言讲一下吗？？------
1 2 3 4 5 a b c d e f g h i j k l m n
                  a b c d m n p
                  
再来个反例：
此时的状态：
1 2 3 4 5 b b b b e f g h i j k l m n
          b b b b m n p
此时已知bbbb子串匹配相等
前缀：b bb bbb
后缀：b bb bbb
3个相等，next是3
已匹配的字符数是4，所以可以移动4-3=1
1 2 3 4 5 b b b b e f g h i j k l m n
            b b b b m n p

再来个例子，上面两个例子是两个极端，来个中间的例子
1 2 3 4 5 b a b a b a g h i j k l m n
          b a b a m n p
此时已知baba子串匹配相等
前缀：b ba bab
后缀：a ba aba
1个相等，next是1
已匹配的字符数是4，所以可以移动4-1=3
1 2 3 4 5 b a b a b a g h i j k l m n
                b a b a m n p
好像不太对啊，不是2吗

再来个例子，上面两个例子是两个极端，来个中间的例子
1 2 3 4 5 o u u o e f g h i j k l m n
          o u u o m n p
此时已知ouuo子串匹配相等
前缀：o ou ouu
后缀：uuo uo o
1个相等，next是1
已匹配的字符数是4，所以可以移动4-1=3
1 2 3 4 5 o u u o e f g h i j k l m n
                o u u o m n p


再来个例子，上面两个例子是两个极端，来个中间的例子
1 2 3 4 5 a a c a a e f g h i j k l m n
          a a c a a m n p
此时已知aacaa子串匹配相等
前缀：a aa aac aaca
后缀：a aa caa acaa
2个相等，next是2
已匹配的字符数是5，所以可以移动5-2=3
1 2 3 4 5 a a c a a e f g h i j k l m n
                a a c a a m n p


所以，对于已经匹配到的子串 t:
1 最多可以跳t.length步
2 最少就是跳1步
  
aacaa匹配到，再跳
 acaa
  caa
   aa
    a
-----这不就是后缀吗，你需要找到跟后缀一样的子串，有一样的，会阻止你跳5步，跳5步是最好结果
 aaca
 aac
 aa
 a
-----这不就是前缀吗，前缀和后缀就aa和a相等，后面就不理解了
-----可以这么理解，前缀和后缀中，相等的串的个数是next的值，你把这个理解为，相等的串中，长度的最大值是next值，也一样
-----因为，如果长度为3的前后缀相等，则长度为1和2的肯定也相等


都什么能减低匹配效率：
1 pattern和source总是能找到匹配的子串，能匹配到，就可以多跳几步，一个都匹配不到，那就是挨个走了
2 匹配到的子串中， 前缀和后缀相等的越多，next就越大，跳的步数就越少，例如，如果匹配到子串aaaa相等，那你就只能跳一步，反而浪费了时间

	 */
	public static int index2(String source, String pattern, int pos){
		char[] t = source.toCharArray();

	    char[] p = pattern.toCharArray();

	    int i = pos; // 主串的位置

	    int j = 0; // 模式串的位置

	    int[] next = getNext(pattern);

	    while (i < t.length && j < p.length) {

	       if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0

	           i++;

	           j++;

	       } else {

	           // i不需要回溯了

	           // i = i - j + 1;

	           j = next[j]; // j回到指定位置

	       }

	    }

	    if (j == p.length) {

	       return i - j;

	    } else {

	       return -1;

	    }
	}
	
	public static int[] makeNext(String p){
		
		int[] res = new int[p.length()];
		
		int i = 0;
		while(i < p.length()){
			int len = 1;
			while(len <= p.length()){
				///aabbcc
				String s = p.substring(i, i+len); //0, 1，结果是a
				///求出s的前缀集合，和后缀集合
				
				///算出前缀后缀集合中，相等的串的个数，或者相等的串的最大长度
				
			}
		}
		
		return res;
	}
	
	public static int[] getNext(String ps) {

	    char[] p = ps.toCharArray();

	    int[] next = new int[p.length];

	    next[0] = -1;

	    int j = 0;

	    int k = -1;

	    while (j < p.length - 1) {

	       if (k == -1 || p[j] == p[k]) {

	           next[++j] = ++k;

	       } else {

	           k = next[k];

	       }

	    }

	    return next;

	}
	
	public static void main(String[] args) {
		
		String source = "abacadttddkkledcs";
		String pattern = "abaca";
		int index = StringMatch.index(source, pattern, 0);
		System.out.println("source--" + source);
		System.out.println("pattern-" + pattern);
		System.out.println(index);
		
		source = "abacadttddkkledcs";
		pattern = "11233";
		index = StringMatch.index(source, pattern, 0);
		System.out.println("source--" + source);
		System.out.println("pattern-" + pattern);
		System.out.println(index);
		
		source = "abacadttddkkledcs";
		pattern = "dcs";
		System.out.println("source--" + source);
		System.out.println("pattern-" + pattern);
		index = StringMatch.index2(source, pattern, 0);
		System.out.println(index);
		
	}
}
