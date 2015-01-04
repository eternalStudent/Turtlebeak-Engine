package model;
import util.Point;

public class MOB extends Entity{
	
	public boolean snakelike = false;
	public int HP = 3;
	public int XP;

	public MOB(String name) {
		super(name);
		sort = 2.0;
		solid = true;
		passable = false;
	}
	
	public MOB(String name, Point[] loc){
		this(name);
		for (int i=0; i<loc.length; i++)
			this.loc.add(loc[i]);
	}
	
	public boolean move(int x, int y){
		if (snakelike){
			Point p = loc.get(loc.size()-1).add(x, y);
			for (int i=1; i<loc.size(); i++)
				if (loc.get(i).equals(p))
					return false;
			loc.add(p);
			loc.remove(0);
		}
		else{
			for (int i=0; i<loc.size(); i++)
				loc.set(i,loc.get(i).add(x, y));
		}
		return true;
	}
	
	public boolean isDead(){
		return HP<=0;
	}

}
