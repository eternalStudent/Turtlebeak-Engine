package examples;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import controller.Keyboard;
import model.Entity;
import model.EventHandler;
import model.Item;
import model.MOB;
import util.ASCII;
import util.Tileset;
import model.World;
import util.Lambda;
import util.Point;
import util.Random;
import view.Board;

public class SnakeGame {
	
	private final Keyboard keyboard = new Keyboard();
	private final Board board = new Board(keyboard, "snake");;
	private World world;
	private final ASCII snakeTile = new ASCII('O', 6, 10);
	private MOB player;
	private Item mouse;
	private EventHandler game;
	private StringBuilder[] screenBarText = new StringBuilder[1];
	
	private SnakeGame(){
		initialize();
		int key = 0;
		while (key != KeyEvent.VK_N){
			Point[] loc = {new Point(17, 16), new Point(16, 16), new Point(15, 16), new Point(14, 16)};
			ASCII[] asciis = {snakeTile, snakeTile, snakeTile, snakeTile};
			player = new MOB("player");
			player.add(loc,  asciis);
			player.snakelike=true;
			world.add(player);
			while (key != KeyEvent.VK_ESCAPE && !player.isDead()){
				screenBarText[0].delete(0, screenBarText[0].length());
				screenBarText[0].append("score: "+Integer.toString(player.XP));
				Point dir = new Point(0, 0);
				if (keyboard.keypressed())
					key = keyboard.get();
				if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN )
					dir = Keyboard.ArrowToPoint(key);
				if (!game.move(player, dir)){
					player.HP=0;
					screenBarText[0].append(" Game Over!");
				}	
				if (player.loc.get(player.loc.size()-1).equals(mouse.loc.get(0))){
					player.loc.add(0, player.loc.get(0));
					player.ascii.add(snakeTile);
					world.remove(mouse);
					while(player.loc.contains(mouse.loc.get(0)))
						mouse.loc.set(0, Random.nextPoint(1, 30, 1, 30));
					world.add(mouse);
					player.XP++;
				}
				board.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
			if (player.isDead())
				do {
					key = keyboard.get();
				}while (key != KeyEvent.VK_ENTER);
			screenBarText[0].delete(0, screenBarText[0].length());
			screenBarText[0].append("Play Again? (Y/N)");
			board.repaint();
			world.remove(player);
			do{
				key = keyboard.get();
			}while (key!=KeyEvent.VK_N && key != KeyEvent.VK_Y);
		}
		System.exit(0);
	}
	
	private void initialize(){
		Lambda<Point, Point> toScreen = new Lambda<Point, Point>(){
			public Point produce(Point p){
				return new Point(p.x*12, p.y*12);
			}
		};
		Tileset tileset = new Tileset("tileset1.png", 12, 12, toScreen);
		tileset.ASCII = true;
		Lambda<Point, Entity> defaultTerrain = new Lambda<Point, Entity>(){
			public Entity produce(Point p){
				if (p.x*p.y*(p.x-31)*(p.y-31)==0){
					Entity wall = new Entity("wall");
					wall.add(p,  new ASCII(' ', 8, 3));
					wall.passable = false;
					return wall;
				}
				Entity floor = new Entity("floor");
				floor.add(p, new ASCII(' ', 7, 10));
				return floor;
			}
		};
		world = new World(tileset, defaultTerrain);
		
		mouse = new Item("mouse");
		mouse.add(Random.nextPoint(1, 30, 1, 30), new ASCII((int)'*', 0, 10));
		world.add(mouse);
		
		game = new EventHandler(world);
		
		board.setScreen(world, new Point(32, 32));
		screenBarText[0] = new StringBuilder();
		board.addScreenBar(tileset, screenBarText, 1, BorderLayout.NORTH, 7, 0);
	}

	public static void main(String[] args) {
		new SnakeGame();
	}

}