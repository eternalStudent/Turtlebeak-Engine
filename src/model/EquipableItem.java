package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.Parser;

public class EquipableItem extends Item implements Cloneable{
	
	public Set<Set<String>> slots;
	public Set<String> currentSlots;
	public Weapon weapon;
	public Inventory inv;
	public Map<String,Integer> bonuses = new HashMap<>();

	public EquipableItem(String name, String slot) {
		super(name);
		//this.slot = slot;
	}
	
	public static void fromText(String text, EquipableItem item){
		Item.fromText(text, item);
		//item.slot = Parser.read(text, "slot", "");
		if (Parser.read(text, "weapon", false))
			item.weapon = Weapon.fromDict(item.name);
		item.bonuses = Parser.read(text);
	}
	
	@Override
	public EquipableItem clone(){
		EquipableItem item = (EquipableItem) super.clone();
		if (inv != null){
			item.inv = new Inventory(inv.space); 
			item.inv.isAllowed = inv.isAllowed; //a bit problematic
		}
		if (weapon != null){
			item.weapon = weapon.clone();
			item.weapon.item = item;
		}	
		item.bonuses = new HashMap<>(bonuses);
		return item;
	}

}