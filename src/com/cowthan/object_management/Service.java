package com.cowthan.object_management;

/**
 * 一个对外提供服务的接口，并且不同情况，会产生不同的Service对象，
 * 即通过Service的不同实现，对外提供不同的服务
 *
 */
public interface Service {
	
	void doService();

}
