package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class SortedList<T extends Comparable<? super T>> extends ArrayList<T> implements List<T>{
	
	public boolean add(T t) {
        int index = Collections.binarySearch(this, t);
        if (index < 0) index = ~index;
        super.add(index, t);
        return true;
    }

}
