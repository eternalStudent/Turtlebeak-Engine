package examples;

import java.awt.event.KeyEvent;

import model.*;
import util.*;
import view.Board;
import view.GameScreen;
import controller.Keyboard;

public class Dungeon {
	
	private final Keyboard keyboard = new Keyboard();
	private final Board board = new Board(keyboard, "dungeon");;
	private World world;
	private MOB player;
	private EventHandler game;
	
	private Dungeon(){
		initialize();
		int key =0;
		game.updateVisual(player);
		world.addAllUnfiltered(player.visual);
		while( key != KeyEvent.VK_ESCAPE){
			key = keyboard.get();
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN 
					|| key == KeyEvent.VK_END || key == KeyEvent.VK_PAGE_DOWN || key == KeyEvent.VK_PAGE_UP || 
					key == KeyEvent.VK_HOME){
				Point p = Keyboard.ArrowToPoint(key);
				game.move(player, p);
				setWorldLoc();
			}
			game.updateVisual(player);
			world.unfilterAll();
			world.filterAll(new FogOfWar());
			world.addAllUnfiltered(player.visual);
			board.repaint();
		}
	}
	
	private void initialize(){
		Tileset tileset = new Tileset("tileset2.png", 32, 32);
		tileset.setTransparentBackground(-16777216);
		Lambda<Point, Entity> defaultTerrain = new Lambda<Point, Entity>(){
			public Entity produce(Point p){
				Entity e = new Entity("floor");
				e.add(p, 30);	
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
					e.add(p, 9);
					e.passable = false;
					e.transparent = false;
					world.add(e);
				}	
		}	
		
		player = new MOB("player");
		player.add(new Point(1, 1), 74);
		while(!world.passable(player.loc)){
			player.loc.set(0, Random.nextPoint(61, 61));
		}
		world.add(player);
		setWorldLoc();
		
		game = new EventHandler(world);
		
		board.addToPane(new GameScreen(world, new Point(15, 15), false));
	}
	
	public void setWorldLoc(){
		world.loc = player.loc().subtract(7, 7);
		if (world.loc.x<0)
			world.loc = new Point(0, world.loc.y);
		if (world.loc.y<0)
			world.loc = new Point(world.loc.x, 0);
		if (world.loc.x>46)
			world.loc = new Point(46, world.loc.y);
		if (world.loc.y>46)
			world.loc = new Point(world.loc.x, 46);
	}
	
	public static void main(String[] args) {
		new Dungeon();
	}

}
