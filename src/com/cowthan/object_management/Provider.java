package com.cowthan.object_management;

/**
 * 用来生成Service对象，注意，如果不使用Provider，则注册到Services的就得是Service实现类的Class对象，
 * newInstance也只能通过反射来了
 *
 */
public interface Provider {
	Service newService();
}
