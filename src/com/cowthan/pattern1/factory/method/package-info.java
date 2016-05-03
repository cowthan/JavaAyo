/**
 * 	工厂方法模式--标准模式
 * 
 *  定义一个接口，用于创建对象，让子类决定实例化哪一个类，工厂
 *  方法使一个类的实例化延迟到其子类
 *  
 *  Define an interface for creating an object, but let subclasses
 *  decide which class to instantiate, Factory Method lets a class
 *  defer instantiation to subclasses.
 * 
 */
/**
 * @author qiaoliang
 *
 * 对于多种产品，如黄人，白人，黑人，三个人继承自统一的基类IHuman
 * 1、三种人对外提供的接口都是一样的，没有个性化的方法，所以对外都是IHuman
 * 2、new的工作就可以交给一个八卦炉，就是生产人类的工厂，这个工厂的产品是IHuman
 * 3、至于具体是什么类型，由参数传入类型，用反射生产出来，所以工厂不需要知道具体类型
 *
 *
 * 优点：
 * 1、良好的封装性，代码结构清晰，高层即调用者只要知道产品的类名，或
 * 约束字符串，不需要知道创建对象的艰辛，降低模块间的耦合
 * 2、优秀的扩展性
 * 3、屏蔽产品类，你只能拿到一个产品对象，具体是哪一个产品，工厂类才知道
 *
 * 扩展：
 * 1、本例是一个比较通用的工厂方法框架，可以照搬
 * 2、如果产品种类少，只需要一个工厂类的对象，则连AbstractHumanFactory也不用了，
 * 也不用new工厂对象了，直接使用静态就行，具体看factory.simple，简单工厂模式
 * 3、可能需要多个工厂类的子类实现，用在业务比较复杂的地方，当每一种具体产品
 * 的创建不仅仅是new，还需要设置一系列初始值时，本例中的工厂方法就会很庞大，
 * 同时也要知道具体的产品的类型进行判断了
 */
package com.cowthan.pattern1.factory.method;