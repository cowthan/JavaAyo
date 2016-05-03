/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.os;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public final class Bundle implements Serializable{
	
	
	public static final Bundle EMPTY = new Bundle();

    private Map<String, Object> data = new HashMap<String, Object>();

    public Bundle(Bundle data2) {
    	this.data = data2.data;
	}
    
    public Bundle(){
    	
    }

	public boolean hasExtra(String key){
        return data.containsKey(key);
    }

    public Bundle getExtras(){
        return this;
    }

    public Bundle putExtra(String name, int value){
        data.put(name, new Integer(value));
        return this;
    }
    public Bundle putExtra(String name, float value){
        data.put(name, new Float(value));
        return this;
    }
    public Bundle putExtra(String name, double value){
        data.put(name, new Double(value));
        return this;
    }
    public Bundle putExtra(String name, boolean value){
        data.put(name, new Boolean(value));
        return this;
    }
    public Bundle putExtra(String name, char value){
        data.put(name, new Character(value));
        return this;
    }
    public Bundle putExtra(String name, String value){
        data.put(name, value);
        return this;
    }

    public <T> Bundle putExtra(String name, T t){
        data.put(name, t);
        return this;
    }

    public int getIntExtra(String name){
        Object o = data.get(name);
        return (Integer) o;
    }

    public float getFloatExtra(String name){
        Object o = data.get(name);
        return (Float) o;
    }

    public double getDoubleExtra(String name){
        Object o = data.get(name);
        return (Double) o;
    }

    public boolean getBooleanExtra(String name){
        Object o = data.get(name);
        return (Boolean) o;
    }

    public <T> T getExtra(String name){
        Object o = data.get(name);
        return (T) o;
    }

}
