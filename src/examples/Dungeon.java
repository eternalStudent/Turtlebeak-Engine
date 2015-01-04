package examples;

import java.awt.event.KeyEvent;

import model.Entity;
import model.EventHandler;
import model.MOB;
import model.Tileset;
import model.World;
import util.Lambda;
import util.Point;
import view.Board;
import controller.Keyboard;

public class Dungeon {
	
	private final Keyboard keyboard = new Keyboard();
	private final Board board = new Board(keyboard, "snake");;
	private World world;
	private MOB player;
	private EventHandler game;
	
	private Dungeon(){
		initialize();
		int key =0;
		while( key != KeyEvent.VK_ESCAPE){
			key = keyboard.get();
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN ){
				Point p = Keyboard.ArrowToPoint(key);
				game.move(player, p);
			}
			board.repaint();
		}
	}
	
	private void initialize(){
		Lambda<Point, Point> toScreen = new Lambda<Point, Point>(){
			public Point produce(Point p){
				return new Point(p.x*32, p.y*32);
			}
		};
		Tileset tileset = new Tileset("tileset2.png", 32, 32, toScreen);
		tileset.setTransparentBackground(-16777216);
		Lambda<Point, Entity> defaultTerrain = new Lambda<Point, Entity>(){
			public Entity produce(Point p){
				Entity e = new Entity("floor");
				e.add(p, 30);
				return e;
			}
		};
		world = new World(tileset, defaultTerrain);
		
		player = new MOB("player");
		player.add(new Point(7, 7), 74);
		world.add(player);
		
		game = new EventHandler(world);
		
		board.setScreen(world, new Point(16, 16));
	}
	
	public static void main(String[] args) {
		new Dungeon();
	}

}
