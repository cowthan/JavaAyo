package android.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SparseArray<V> extends LinkedHashMap<Integer, V>{

	public List<Integer> keys = new ArrayList<Integer>();
	
	@Override
	public V put(Integer key, V value) {
		keys.add(key);
		return super.put(key, value);
	}
	
	@Override
	public V remove(Object key) {
		keys.remove(key);
		return super.remove(key);
	}
	
	public int indexOfKey(int fdNum) {
		return keys.indexOf(new Integer(fdNum));
	}
	
	
	
}