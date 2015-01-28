package examples;

import model.Entity;
import model.World;
import util.*;
import view.Board;
import view.GameScreen;

public class ShowMap {
	
	private final Board board = new Board(null, "map");;
	private World world;
	
	public ShowMap(){
		initialize();
		board.repaint();
	}
	
	private void initialize(){
		Tileset tileset = new Tileset("tileset1.png", 12, 12);
		tileset.ASCII = true;
		Lambda<Point, Entity> defaultTerrain = new Lambda<Point, Entity>(){
			public Entity produce(Point p){
				Entity e = new Entity("floor");
				e.add(p, new ASCII(' ', 7, 15));	
				return e;
			}
		};
		world = new World(tileset, defaultTerrain);
		//Maze dungeon = new Maze(20, 20, 3);
		BSPDungeon dungeon = new BSPDungeon(61, 61);
		//CellularAutomataMap dungeon = new CellularAutomataMap(61, 61);
		for (int x=0; x<61; x++)
			for (int y=0; y<61; y++){
				Point p = new Point(x, y);
				if (dungeon.contains(p)){
					Entity e = new Entity("wall");
					e.add(p, new ASCII(' ', 0, 0));
					world.add(e);
				}	
		}
		
		board.addToPane(new GameScreen(world, new Point(61, 61)));
	}
	
	public static void main(String[] args){
		new ShowMap();
	}

}
