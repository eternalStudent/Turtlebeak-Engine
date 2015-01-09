package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Lambda;
import util.Point;
import util.SortedList;
import util.Tileset;

public class World {

	public Tileset tileset;
	public Lambda<Point, Entity> defaultTerrain;
	public List<Entity> entities = new SortedList<>();
	public Point loc = new Point(0,0);
	private Map<Point, List<Entity>> dict = new HashMap<>();
	
	public World(Tileset tileset, Lambda<Point, Entity> defaultTerrain){
		this.tileset = tileset;
		this.defaultTerrain = defaultTerrain;
	}
	
	public synchronized List<Entity> get(Point p){
		if (dict.get(p) == null){
			List<Entity> es = new ArrayList<>();
			Entity e = defaultTerrain.produce(p);
			es.add(e);
			dict.put(p, es);
			entities.add(e);
		}	
		return dict.get(p);
	}
	
	public synchronized void add(Entity e){
		for (Point p: e.loc){
			List<Entity> es = get(p);
			if (e.zLevel == 0.0){
				entities.remove(es.get(0));
				es.remove(es.get(0));
			}	
			es.add(e);
		}	
		entities.add(e);
	}
	
	public synchronized void remove(Entity e){
		for (Point p: e.loc){
			get(p).remove(e);
		}
		entities.remove(e);
	}
	
	public boolean passable(List<Point> loc){
		for (Point p: loc){
			List<Entity> list = get(p);
			if (!list.get(list.size()-1).passable)
					return false;
		}	
		return true;
	}
	
	public Point toScreen(Point p){
		return tileset.toScreen.produce(p);
	}
	
}
