package model;

import java.util.HashMap;
import java.util.Map;

import util.Parser;

public class EquipableItem extends Item {
	
	public String slot;
	public Weapon weapon;
	public Inventory inv;
	public Map<String,Integer> bonuses = new HashMap<>();

	public EquipableItem(String name, String slot) {
		super(name);
		this.slot = slot;
	}
	
	public static void copy(Entity entity, Entity otherEntity){
		Item.copy(entity, otherEntity);
		EquipableItem item = (EquipableItem)entity;
		EquipableItem other =(EquipableItem)otherEntity;
		item.slot = other.slot;
		item.weapon = other.weapon;
		if (other.inv != null){
			item.inv = new Inventory(other.inv.space); 
			item.inv.isAllowed = other.inv.isAllowed;
		}	
	}
	
	public static void fromText(String text, EquipableItem item){
		Item.fromText(text, item);
		item.slot = Parser.read(text, "slot", "");
		if (Parser.read(text, "weapon", false))
			item.weapon = Weapon.fromDict(item.name);
		item.bonuses = Parser.read(text);
	}
	
	public static EquipableItem clone(EquipableItem other){
		EquipableItem item = new EquipableItem(other.name, other.slot);
		copy(item, other);
		return item;
	}

}