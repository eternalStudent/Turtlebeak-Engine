package util;

import java.util.HashSet;
import java.util.Set;

public class CellularAutomataMap {
	
	private Set<Point> walls = new HashSet<>();
	
	public CellularAutomataMap(int width, int height, int generations){
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++)
				if (Random.nextInt(20)<9)
					walls.add(new Point(x, y));
		for (int i=0; i<generations; i++){
			Set<Point> temp = new HashSet<>();
			for (int x=0; x<width; x++)
				for (int y=0; y<height; y++){
					Point p = new Point(x, y);
					int count = count(p);
					if (count>=5 || count==0)
						temp.add(p);
				}
			walls = temp;
		}
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++){
				Point p = new Point(x, y);
				if (count(p)==0)
					walls.remove(p);
				if (x*y*(x-width+1)*(y-height+1)==0)
					walls.add(p);
			}	
	}
	
	private int count(Point p){
		int sum=0;
		for (int x=-1; x<2; x++)
			for (int y=-1; y<2; y++){
				if (x==0 && y==0)
					continue;
				if (walls.contains(p.add(x, y)))
					sum++;
			}
		return sum;
	}
	
	public boolean contains(Point p){
		return walls.contains(p);
	}

}
