package com.cowthan.pattern1.factory.method;

public abstract class AbstractHumanFactory {
	public abstract <T extends IHuman> T createHuman(Class<T> clazz);
}
