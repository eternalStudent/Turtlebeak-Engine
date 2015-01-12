package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Maze {
	
	private Set<Point> walls = new HashSet<>();
	private DisjointSets<Point> sets = new DisjointSets<>();
	int space;
	
	public Maze(int width, int height, int space){
		this.space = space;
		for (int x=0; x<width*space+1; x++)
			for (int y=0; y<height*space+1; y++){
				if (x%space==0 || y%space==0)
					walls.add(new Point (x, y));
			}
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++)
				sets.add(new Point(x, y));
		
		while (sets.size()>1){
			Point p = Random.nextPoint(width, height);
			destroyWall(p, randomDir(p));
		}
	}
	
	private Point randomDir(Point p){
		Point root = sets.find(p);
		List<Point> list = new ArrayList<>();
		Point[] roots = {sets.find(p.add(0, -1)), sets.find(p.add(-1, 0)), sets.find(p.add(1, 0)), sets.find(p.add(0, 1))};
		Point[] dirs = {new Point(0, -1), new Point(-1, 0), new Point(1, 0), new Point(0, 1)};
		for (int i=0; i<4; i++)
			if (roots[i] != null && roots[i] != root)
				list.add(dirs[i]);
		if (list.size()> 0)
			return Random.nextElement(list);
		return new Point(0, 0);
	}
	
	private void destroyWall(Point p1, Point dir){
		if (dir.equals(0, 0))
			return;
		Point p2 = p1.add(dir);
		Point root1 = sets.find(p1);
		Point root2 = sets.find(p2);
		sets.union(root1, root2);
		p1 = new Point(p1.x*space, p1.y*space);
		if (dir.equals(0, -1))
			for (int x=1; x<space; x++)
				walls.remove(p1.add(x, 0));	
		if (dir.equals(-1, 0))
			for (int y=1; y<space; y++)
				walls.remove(p1.add(0, y));
		if (dir.equals(1, 0))
			for (int y=1; y<space; y++)
				walls.remove(p1.add(space, y));
		if (dir.equals(0, 1))
			for (int x=1; x<space; x++)
				walls.remove(p1.add(x, space));
	}
	
	public boolean contains(Point p){
		return walls.contains(p);
	}
	
}