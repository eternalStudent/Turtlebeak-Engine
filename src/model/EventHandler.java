package model;
import java.util.ArrayList;
import java.util.List;

import model.MOB;
import util.Line;
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
	
	public boolean inSight(Point loc, int x, int y){
		Line line = new Line(loc.x, loc.y, x, y);
		int d = line.distance();
		if (d == 0)
			return true;
		for (int i=0; i<d; i++){
			loc = line.get(i);
			if (!world.transparent(loc))
				break;
		}
		return loc.equals(x, y);
	}
	
	
	public void updateVisual(MOB m){
		m.visual.clear();
		for (Point p: Point.sphere(m.loc.get(0).x,  m.loc.get(0).y,  m.vision)){
			if (m.snakelike){
				if (inSight(m.loc.get(m.loc.size()-1), p.x, p.y))
						m.visual.add(p);
			}
			else{
				for (Point loc: m.loc)
					if (inSight(loc, p.x, p.y))
						m.visual.add(p);
			}
		}	
	}

}
