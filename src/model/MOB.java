package model;
import java.util.HashSet;
import java.util.Set;

import util.AI;
import util.Parser;
import util.Point;

public class MOB extends Entity implements Cloneable{
	
	public boolean snakelike = false;
	public int maxHP = 3;
	public int HP = 3;
	public int XP;
	public AI ai;
	public Set<MOB> ties = new HashSet<>();
	public Set<Point> visual = new HashSet<>();
	public int vision = 5;
	public Equipment eqp = new Equipment();
	public Weapon naturalWeapon;

	protected MOB(){this(null);}
	
	public MOB(String name) {
		super(name);
		zLevel = 2.0;
		solid = true;
		passable = false;
		type = 'm';
	}
	
	public MOB(String name, Point[] loc){
		this(name);
		for (int i=0; i<loc.length; i++)
			this.loc.add(loc[i]);
	}
	
	public static void fromText(String text, MOB m){
		m.maxHP = Parser.read(text, "HP", 3);
		m.HP = m.maxHP;
		m.snakelike = Parser.read(text, "snake", false);
		m.vision = Parser.read(text, "vision", 5);
	}
	
	public Point loc(){
		return loc.get(0);
	}
	
	public Point loc(int i){
		return loc.get(i);
	}
	
	public boolean act(){
		ai.mob = this;
		return ai.act();
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
				loc.set(i, loc.get(i).add(x, y));
		}
		return true;
	}
	
	public boolean isDead(){
		return HP<=0;
	}
	
	@Override
	public MOB clone(){
		MOB m = (MOB) super.clone();
		m.ties = new HashSet<>();
		m.visual = new HashSet<>();
		m.eqp = new Equipment();
		if (ai != null)
			m.ai = ai.clone();
		if (naturalWeapon != null)
			m.naturalWeapon = naturalWeapon.clone();
		return m;
	}
}