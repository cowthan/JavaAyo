package com.cowthan.type_parameter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class TypeToken<T> {

    private final Type type;

    protected TypeToken(){
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    public final static Type LIST_STRING = new TypeToken<List<String>>() {}.getType();



}
