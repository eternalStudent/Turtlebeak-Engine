package model;

import util.Lambda;
import util.Parser;

public class Item extends Entity implements Cloneable{
	
	public float weight, size;
	public int amount = 1;
	public Lambda<Integer, Boolean> usage;
	public Lambda<Integer, Boolean> instant;

	protected Item(){this(null);}
	
	public Item(String name) {
		super(name);
		zLevel = 1.0;
		type = 'i';
	}
	
	public static void fromText(String text, Item item){
		item.weight = Parser.read(text, "weight", 0);
		item.size = Parser.read(text, "size", 0);
		item.amount = Parser.read(text, "amount", 1);
	}
	
	public boolean use(){
		if (usage == null)
			return false;
		return usage.produce(0);
	}
	
	public boolean instant(){
		if (instant == null)
			return false;
		return instant.produce(0);
	}
	
	@Override
	public Item clone(){
		Item item = (Item) super.clone();
		return item;
	}

}