package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tile {
	
	public int number;
	public List<Filter> filters = new ArrayList<>();
	public Tileset spritesheet;
	
	public Tile(int i){
		this(i, null, new ArrayList<Filter>());
	}
	
	public Tile(int i, Tileset spritesheet){
		this(i, spritesheet, new ArrayList<Filter>());
		
	}
	
	public Tile(int i, Tileset spritesheet,List<Filter> filters){
		number = i;
		this.spritesheet = spritesheet;
		addAll(filters);
	}
	
	public void addAll(List<Filter> filters){
		this.filters.addAll(filters);
	}
	
	public Tile filter(List<Filter> filters){
		Tile tile = new Tile(this.number, spritesheet, filters);
		tile.addAll(this.filters);
		return tile;
	}
	
	public BufferedImage getTileImage(){
		return spritesheet.getTileImage(this);
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
