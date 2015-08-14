package model;

import util.Function;
import util.Parser;

public class Item extends Entity implements Cloneable{
	
	public float weight, size;
	public int amount = 1;
	public Function<Integer, Boolean> usage;
	public Function<Integer, Boolean> instant;

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
		return usage.eval(0);
	}
	
	public boolean instant(){
		if (instant == null)
			return false;
		return instant.eval(0);
	}
	
	@Override
	public Item clone(){
		Item item = (Item) super.clone();
		return item;
	}

}