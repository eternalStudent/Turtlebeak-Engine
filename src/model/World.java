package model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import util.Lambda;
import util.Point;

public class World {

	public Tileset tileset;
	public Lambda<Point, Entity> defaultTerrain;
	public Set<Entity> entities = new HashSet<>();
	private Map<Point, SortedSet<Entity>> dict = new HashMap<>();
	
	public World(Tileset tileset, Lambda<Point, Entity> defaultTerrain){
		this.tileset = tileset;
		this.defaultTerrain = defaultTerrain;
	}
	
	public SortedSet<Entity> get(Point p){
		if (dict.get(p) == null){
			SortedSet<Entity> es = new TreeSet<>();
			es.add(defaultTerrain.produce(p));
			dict.put(p, es);
		}	
		return dict.get(p);
	}
	
	public void add(Entity e){
		for (Point p: e.loc){
			SortedSet<Entity> es = get(p);
			if (e.sort == 0.0){
				entities.remove(es.first());
				es.remove(es.first());
			}	
			es.add(e);
		}	
		entities.add(e);
	}
	
	public void remove(Entity e){
		for (Point p: e.loc){
			get(p).remove(e);
		}
		entities.remove(e);
	}
	
	public boolean passable(List<Point> loc){
		for (Point p: loc)
			for (Entity e: get(p))
				if (!e.passable)
					return false;
		return true;
	}
	
	public Point toScreen(Point p){
		return tileset.toScreen.produce(p);
	}
	
}
