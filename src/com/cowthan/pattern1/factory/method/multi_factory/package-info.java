/**
 * 	工厂方法-多工厂模式
 * 
 *  定义一个接口，用于创建对象，让子类决定实例化哪一个类，工厂
 *  方法使一个类的实例化延迟到其子类
 *  
 *  Define an interface for creating an object, but let subclasses
 *  decide which class to instantiate, Factory Method lets a class
 *  defer instantiation to subclasses.
 * 
 *  多工厂模式：
 *  好处就是之所以使用这个模式的原因，不多说，但是坏处在于每增加一种产品，
 *  就需要同时增加一个工厂类，维护时需要考虑两个对象之间的关系
 *  
 *  改善：
 *  在复杂应用中一般还是使用本模式，但是增加一个协调类，作用就是避免调用者与各个子工厂
 *  交流，封装子工厂，高层模块使用的还是统一的访问接口
 *  ——这个怎么实现？
 *  ——目标就是在NvWa类中，不应该出现：AbstractHumanFactory factory = new WhiteHumanFactory();
 *  这种直接与具体工厂子类交互的代码
 *  ——还是不知道怎么实现！！！！！！！！
 *  
 *  学问：
 *  业务的变数每增加一个，在代码中就需要多一层来封装
 * 
 */
package com.cowthan.pattern1.factory.method.multi_factory;