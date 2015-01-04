package model;
import java.util.ArrayList;
import java.util.List;

import util.Point;

@SuppressWarnings("rawtypes")
public class Entity implements Comparable{
	
	public String name;
	public Double sort = 0.0;
	
	public boolean passable = true;
	public boolean solid = false;
	public boolean transparent = true;
	
	public List<ASCII> ascii = new ArrayList<>();
	public List<Point> loc = new ArrayList<>();
	public List<Integer> tiles = new ArrayList<>();
	public Tileset spritesheet;
	
	public Entity(String name){
		this.name = name;
	}

	@Override
	public int compareTo(Object o) {
		Entity other = (Entity)o;
		return sort.compareTo(other.sort);
	}
	
	public ASCII getAscii(Point p){
		return ascii.get(loc.indexOf(p));
	}
	
	public int getTile(Point p){
		return tiles.get(loc.indexOf(p));
	}
	
	public void add(Point[] loc, ASCII[] tiles){
		for (int i=0; i<loc.length; i++){
			this.loc.add(loc[i]);
			this.ascii.add(tiles[i]);
		}
	}
	
	public void add(Point loc, ASCII tile){
		this.loc.add(loc);
		this.ascii.add(tile);
	}
	
	public void add(Point loc, int tile){
		this.loc.add(loc);
		this.tiles.add(tile);
	}
	
	public String toString(){
		return name;
	}

}
