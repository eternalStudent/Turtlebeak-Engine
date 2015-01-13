package util;

import java.util.HashSet;
import java.util.Set;

public class CellularAutomataMap {
	
	private Set<Point> walls = new HashSet<>();
	
	public CellularAutomataMap(int width, int height, int generations, int initial, Range born, Range stayAlive){
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++)
				if (Random.nextInt(120)<initial)
					walls.add(new Point(x, y));
		for (int i=0; i<generations; i++){
			Set<Point> temp = new HashSet<>();
			for (int x=0; x<width; x++)
				for (int y=0; y<height; y++){
					Point p = new Point(x, y);
					int count = count(p);
					if ((!walls.contains(p) && born.inRange(count)) || (walls.contains(p) && stayAlive.inRange(count)))
						temp.add(p);
				}
			walls = temp;
		}
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++){
				Point p = new Point(x, y);
				if (x*y*(x-width+1)*(y-height+1)==0)
					walls.add(p);
			}
	}
	
	public CellularAutomataMap(int width, int height){
		this(width, height, 2, 54, new Range(5, 9), new Range(3, 9));
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
