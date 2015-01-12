package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DisjointSets<T> {
	
	private Map<T, Set<T>> dict = new HashMap<>();
	private Set<T> all = new HashSet<>();
	 
	public void add(T t){
		if (all.contains(t))
			return;
		Set<T> set = new HashSet<>();
		set.add(t);
		dict.put(t, set);
		all.add(t);
	}
	 
	public void union(T t1, T t2){
		Set<T> set1 = dict.remove(t1);
		Set<T> set2 = dict.remove(t2);
		set1.addAll(set2);
		dict.put(t1, set1);
	}
	 
	public T find(T elem){
		for (T t: dict.keySet())
			if (dict.get(t).contains(elem))
				return t;
	    return null;
	}
	 
	public int size(){
	    return dict.size();
	}

}
