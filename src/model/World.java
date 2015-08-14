package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Filter;
import util.Function;
import util.Point;
import util.SortedList;
import util.Tileset;

public class World {

	public Tileset tileset;
	public Function<Point, Entity> defaultTerrain;
	public List<Entity> entities = new SortedList<>();
	public Point loc = new Point(0,0);
	private Map<Point, List<Entity>> dict = new HashMap<>();
	private Map<Point, List<Filter>> visual = new HashMap<>();
	
	public World(Tileset tileset, Function<Point, Entity> defaultTerrain){
		this.tileset = tileset;
		this.defaultTerrain = defaultTerrain;
	}
	
	public synchronized List<Entity> get(Point p){
		if (dict.get(p) == null){
			List<Entity> es = new ArrayList<>();
			Entity e = defaultTerrain.eval(p);
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
	
	public List<MOB> getMOBs(List<Point> loc){
		List<MOB> list = new ArrayList<>();
		for (Point p: loc){
			for (Entity e: get(p)){
				if (e instanceof MOB)
					list.add((MOB)e);
			}
		}
		return list;
	}
	
	public List<Item> getItems(Point p){
		List<Item> list = new ArrayList<>();
		for (Entity e: get(p))
			if (e instanceof Item)
				list.add((Item)e);
		return list;
	}
	
	public List<Item> getItems(List<Point> loc){
		List<Item> list = new ArrayList<>();
		for (Point p: loc){
			list.addAll(getItems(p));
		}
		return list;
	}
	
	public boolean passable(List<Point> loc){
		for (Point p: loc){
			List<Entity> list = get(p);
			if (!list.get(list.size()-1).passable)
					return false;
		}	
		return true;
	}
	
	public boolean transparent(Point p){
		List<Entity> list = get(p);
		for (Entity e: list)
			if (!e.transparent)
				return false;
		return true;
	}
	
	public boolean isVisible(Point p){
		return visual.containsKey(p);
	}
	
	public List<Filter> getFilters(Point p){
		if (visual.containsKey(p))
			return visual.get(p);
		return new ArrayList<Filter>();
	}
	
	public void filterAll(Filter filter){
		for (Point p: visual.keySet())
			visual.get(p).add(filter);
	}
	
	public void unfilterAll(){
		for (Point p: visual.keySet())
			visual.put(p, new ArrayList<Filter>());
	}
	
	public void addAllUnfiltered(Collection<Point> c){
		for (Point p: c)
			visual.put(p, new ArrayList<Filter>());
	}
	
	public Point toScreen(Point p){
		return tileset.toScreen.eval(p);
	}
	
}
