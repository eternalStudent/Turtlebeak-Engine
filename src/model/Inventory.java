package model;

import java.util.ArrayList;
import java.util.List;

import util.Function;

public class Inventory {
	
	public float size, weight, space;
	private List<Item> list = new ArrayList<>();
	public Function<Item, Boolean> isAllowed;
	
	public Inventory(float space){
		this.space = space;
	}
	
	public boolean add(Item item){
		if (isAllowed.eval(item) && enoughFreeSpace(item)){
			list.add(item);
			size += item.size;
			weight += item.weight;
			return true;
		}
		return false;
	}
	
	public boolean remove(Item item){
		if (list.remove(item)){
			size -= item.size;
			weight -= item.weight;
			return true;
		}
		return false;
	}
	
	private boolean enoughFreeSpace(Item item){
		return (space==-1 || space<=size+item.size);
	}

}