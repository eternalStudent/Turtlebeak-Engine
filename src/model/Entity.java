package model;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.Resources;

import util.Parser;
import util.Point;
import util.Tileset;
import util.ASCII;

public class Entity implements Comparable<Entity>{
	
	public static Map<String, Entity> dict = new HashMap<>();
	
	public String name;
	protected char type = 'e';
	public Double zLevel = 0.0;
	
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

	public static void copy(Entity e, Entity other){
		e.name = other.name;
		e.ascii = other.ascii;
		e.passable = other.passable;
		e.solid = other.solid;
		e.zLevel = other.zLevel;
		e.spritesheet = other.spritesheet;
		e.tiles = other.tiles;
		e.transparent = other.transparent;
	}
	
	public static Entity factory(char ch){
		switch (ch){
		case 'm': return new MOB(null);
		case 'i': return new Item(null);
		default: return new Entity(null);
		}
	}
	
	public static Entity clone(Entity other){
		Entity e = factory(other.type);
		copy(e, other);
		if (e.type == 'm'){
			MOB.copy((MOB)e, other);
		} 
		return e;
	}
	
	public static Entity fromDict(String name){
		return clone(dict.get(name));
	}
	
	public static void fromText(String text){
		Pattern pattern = Pattern.compile("\\{([^//}])*\\}");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()){
			String elem = matcher.group();
			char type = Parser.read(elem, "type", 'e');
			Entity e = factory(type);
			e.name = Parser.read(elem, "name", "");
			e.passable = Parser.read(elem, "pass", e.passable);
			e.solid = Parser.read(elem, "solid", e.solid);
			e.transparent = Parser.read(elem, "trans", e.transparent);
			e.zLevel = Parser.read(elem, "sort", e.zLevel);
			e.tiles = Parser.readIntList(elem, "tiles", new ArrayList<Integer>());
			e.ascii = Parser.readASCIIList(elem, "ASCII", new ArrayList<ASCII>());
			if (type == 'm')
				MOB.fromText(elem, (MOB)e);
			dict.put(e.name, e);
		}
	}
	
	public static void fromResource(URL url) throws IOException{
		fromText(Resources.toString(url, StandardCharsets.UTF_8));
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
		
	@Override
	public int compareTo(Entity other) {
		return zLevel.compareTo(other.zLevel);
	}

	@Override
	public String toString(){
		return name;
	}

}
