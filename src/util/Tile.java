package util;

import java.util.ArrayList;
import java.util.List;

public class Tile {
	
	public int number;
	public List<Filter> filters;
	
	public Tile(int i){
		this(i, new ArrayList<Filter>());
	}
	
	public Tile(int i, List<Filter> filters){
		number = i;
		this.filters = filters;
	}
	
	public void addAll(List<Filter> filters){
		this.filters.addAll(filters);
	}
	
	public Tile filter(List<Filter> filters){
		Tile tile = new Tile(this.number, filters);
		tile.addAll(this.filters);
		return tile;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile)obj;
		return (number == other.number && filters.equals(other.filters));
	}
	
	@Override
	public int hashCode(){
		return number*3+filters.size();
	}
	
	@Override
	public String toString(){
		return number+" "+filters;
	}

}
