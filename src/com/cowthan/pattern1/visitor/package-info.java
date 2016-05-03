/**
 * 	访问者模式
 * 
 * 	这个模式没有深入研究，基本原理就是一个Employee，Manager等对象要做
 *  报表，形式各不相同，为了不对Employee，Manager进行侵入性的破坏，就
 *  定义一个IVisitor接口，其接口方法就是visit(各种对象)，这些被访问对象应该
 *  是出自同一个接口，这里都是雇员
 *  
 *  伪实现：
 *  public class visitor implents IVisitor{
 *      public void visit(Emp emp){
 *      	打印普通员工报表
 *      }
 *      
 *      public void visit(Mgmr mgmr){
 *      	打印经理报表
 *      }
 *  }
 *  
 *  调用：这个有点深度
 *  ——不是让你直接：visitor.visit(new emp())
 *  ——而是： emp.access(visitor)
 *  
 *  public class Emp{
 *  
 *     ....属性，getter， setter
 *     
 *     //===访问者相关：
 *     public void access(IVisitor visitor){
 *     		visitor.visit(this);
 *     }
 *  }
 *  
 *  ——如你所见，不同的报表形式，就再多定义几个 IVisitor的实现类
 *  
 *  
 *  分析：
 *  1、Emp这种类负责数据的加载，而visitor这种类负责数据的展示，
 *  各自职责不同，各自演绎各自的变化
 *  2、业务规则要求遍历多个不同的对象，这是Visitor模式的出发点
 *  3、访问者模式是为了弥补迭代器模式的功能，这俩我都没深入研究
 *  4、visitor可以在被accept的同时，进行一些统计工作，此时需要
 *  IVisitor提供关于统计的接口，以供外部调用，但注意：
 *  只有在被accpet之后，visitor中才有统计数据，如获取总工资
 */
package com.cowthan.pattern1.visitor;