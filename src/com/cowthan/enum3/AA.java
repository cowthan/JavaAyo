package com.cowthan.enum3;

public class AA {
/*

1 
enum的静态方法：
Week[] values()：返回所有常量的数组
Week valueOf(String)：根据常量名，获得枚举常量

2 
enum的实例方法：
name()：获取常量名
ordinal()：获取枚举常量在类型中的数字位置，从0开始


3 
特别注意：
ordinal()是内置的，这个是序数，是为EnumSet和EnumMap设计的，程序员不应该依赖这个方法做有关下标的事

你应该用实例域代替序数，例如MONDAY(1)


4 EnumSet代替位域： 枚举的集合

什么是位域呢，就是常见的flag模式，或者标志位模式

public class Text{

	public static final int STYLE_BOLD          = 1 << 0; //1   0001
	public static final int STYLE_ITALIC        = 1 << 1; //2   0010
	public static final int STYLE_UNDERLINE     = 1 << 2; //4   0100
	public static final int STYLE_STRIKETHROUGH = 1 << 3; //8   1000
	
	public void applyStyles(int styles){
	}

}

text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
OR运算符可以将几个常量合并到一个集合中，这就叫位域（bit field），可以认为位域是标志位的集合


EnumSet可以用单个long来实现，性能比得上位域


-------------怎么改编：
public class Text{
	public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
	
	//任何Set都可以传进来，但EnumSet是最佳的
	public void applyStyles(Set<Style> styles){
	}
}

怎么调用：text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));




4 EnumMap代替序数索引

避免使用enum.ordinal()作为数组下标，如果想根据enum的序数快速定位枚举，应该用EnumMap

EnumMap：键是枚举的Map


Map<Week, Set<Course>> courses = new EnumMap<Week, Set<Course>>(Week.class);

for(Week w: Week.values()){
	courses.put(w, new HashSet<Course>>);
}

指定了EnumMap的key类型之后，由于enum的常量个数是固定的，所以Enum最多有几个键也是固定的，
所以可以实现完美哈希


 */
}
