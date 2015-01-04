package model;
import java.util.ArrayList;
import java.util.List;

import util.Point;

public class EventHandler {
	
	private World world;
	
	public EventHandler(World world){
		this.world = world;
	}
	
	public boolean move(MOB m, Point p){
		boolean temp = true;
		List<Point> ps = new ArrayList<>();
		ps.addAll(m.loc);
		world.remove(m);
		if (!m.move(p.x, p.y) || !world.passable(m.loc)){
			m.loc = ps;
			temp = false;
		}	
		world.add(m);
		return temp;
	}

}
