package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	public boolean equip(EquipableItem item, Set<String> slots){
		if (!item.slots.contains(slots))
			return false;
		for (String slot: slots){
			if (dict.containsKey(slot))
				return false;
		}
		item.currentSlots = slots;
		for (String slot: slots){
			dict.put(slot, item);
		}
		return true;
	}
	
	public boolean isEmpty(String slot){
		return get(slot)==null;
	}
	
	public void unequip(EquipableItem item){
		Set<String> slots = item.currentSlots;
		item.currentSlots = null;
		for (String slot: slots){
			dict.remove(slot);
		}
		onFloor.add(item);
	}

}
