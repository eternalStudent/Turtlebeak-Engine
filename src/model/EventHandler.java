package model;
import java.util.ArrayList;
import java.util.List;

import util.Point;

public class EventHandler {
	
	private World world;
	
	public EventHandler(World world){
		this.world = world;
	}
	
	public boolean move(MOB mob, Point dir){
		if (dir.equals(0, 0))
			return true;
		boolean temp = true;
		List<Point> prevLoc = new ArrayList<>();
		prevLoc.addAll(mob.loc);
		world.remove(mob);
		if ( !mob.move(dir.x, dir.y) || (!world.passable(mob.loc) && (!mob.passable)) ){
			mob.loc = prevLoc;
			temp = false;
		}	
		world.add(mob);
		for (MOB m: mob.ties)
			move(m, dir);
		return temp;
	}

}
