package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {
	
	private Map<String, EquipableItem> dict = new HashMap<>();
	public Inventory inv;
	public List<Item> onFloor = new ArrayList<>();
	
	public boolean add(Item item){
		return inv.add(item);
	}
	
	public EquipableItem get(String slot){
		return dict.get(slot);
	}
	
	public EquipableItem equip(EquipableItem item){
		return dict.put(item.slot, item);
	}
	
	public boolean isEmpty(String slot){
		return get(slot)==null;
	}
	
	public boolean unequip(EquipableItem item){
		return onFloor.add(dict.remove(item.slot));
	}

}
