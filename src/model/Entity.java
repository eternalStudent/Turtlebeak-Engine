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
import util.Tile;
import util.Tileset;
import util.ASCII;

public class Entity implements Comparable<Entity>, Cloneable{
	
	public static Map<String, Entity> dict = new HashMap<>();
	
	public String name;
	public Double zLevel = 0.0;
	protected char type = 'e';
	
	public boolean passable = true;
	public boolean solid = false;
	public boolean transparent = true;
	
	public List<ASCII> ascii = new ArrayList<>();
	public List<Point> loc = new ArrayList<>();
	public List<Tile> tiles = new ArrayList<>();
	public Tileset spritesheet;
	
	protected Entity(){}
	
	public Entity(String name){
		this.name = name;
	}
	
	public static Entity factory(char ch){
		switch (ch){
		case 'm': return new MOB();
		case 'i': return new Item();
		case 'q': return new EquipableItem();
		default: return new Entity();
		}
	}
	
	
	public static Entity fromDict(String name){
		return dict.get(name).clone();
	}
	
	private static List<Tile> toTiles(List<Integer> list){
		List<Tile> tiles = new ArrayList<>();
		for (int i=0; i<list.size(); i++)
			tiles.add(new Tile(list.get(i)));
		return tiles;
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
			e.tiles = toTiles(Parser.readIntList(elem, "tiles", new ArrayList<Integer>()));
			e.ascii = Parser.readASCIIList(elem, "ASCII", new ArrayList<ASCII>());
			if (e instanceof MOB)
				MOB.fromText(elem, (MOB)e);
			if (e instanceof Item)
				Item.fromText(text, (Item)e);
			if (e instanceof EquipableItem)
				EquipableItem.fromText(text, (EquipableItem)e);
			dict.put(e.name, e);
		}
	}
	
	public static void fromResource(URL url) throws IOException{
		fromText(Resources.toString(url, StandardCharsets.UTF_8));
	}	
	
	public ASCII getAscii(Point p){
		return ascii.get(loc.indexOf(p));
	}
	
	public Tile getTile(Point p){
	
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
		this.tiles.add(new Tile (tile));
	}
	
	@Override
	public Entity clone(){
		Entity e = new Entity(name);
		try {
			e = (Entity) super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		e.ascii = new ArrayList<>(ascii);
		e.loc = new ArrayList<>();
		e.tiles = new ArrayList<>(tiles);
		return e;
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