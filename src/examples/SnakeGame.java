package examples;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import controller.Keyboard;
import model.Entity;
import model.EventHandler;
import model.Item;
import model.MOB;
import model.ASCII;
import model.Tileset;
import model.World;
import util.Lambda;
import util.Point;
import util.Random;
import view.Board;

public class SnakeGame {
	
	private final Keyboard keyboard = new Keyboard();
	private final Board board = new Board(keyboard, "snake");;
	private World world;
	private final ASCII snakeTile = new ASCII('O', 7, 0);
	private MOB player;
	private Item mouse;
	private EventHandler game;
	private StringBuilder screenBarText;
	
	private SnakeGame(){
		initialize();
		int key = 0;
		int prev;
		while (key != KeyEvent.VK_N){
			Point[] loc = {new Point(17, 16), new Point(16, 16), new Point(15, 16), new Point(14, 16)};
			ASCII[] asciis = {snakeTile, snakeTile, snakeTile, snakeTile};
			player = new MOB("player");
			player.add(loc,  asciis);
			player.snakelike=true;
			world.add(player);
			while (key != KeyEvent.VK_ESCAPE && !player.isDead()){
				screenBarText.delete(0, screenBarText.length());
				screenBarText.append("score: "+Integer.toString(player.XP));
			
				prev = key;
				if (keyboard.keypressed())
					key = keyboard.get();
				if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN ){
					Point p = Keyboard.ArrowToPoint(key);
					if (!game.move(player, p)){
						player.HP=0;
						screenBarText.append(" Game Over!");
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
				}	
				else
					key = prev;
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
			world.remove(player);
			screenBarText.delete(0, screenBarText.length());
			screenBarText.append("Play Again? (Y/N)");
			board.repaint();
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
					Entity e = new Entity("wall");
					e.add(p,  new ASCII('#', 8, 0));
					e.passable = false;
					return e;
				}
				Entity e = new Entity("floor");
				e.add(p, new ASCII(0, 0, 0));
				return e;
			}
		};
		world = new World(tileset, defaultTerrain);
		
		mouse = new Item("mouse");
		mouse.add(Random.nextPoint(1, 30, 1, 30), new ASCII((int)'*', 15, 0));
		world.add(mouse);
		
		game = new EventHandler(world);
		
		board.setScreen(world, new Point(32, 32));
		screenBarText = new StringBuilder();
		board.addScreenBar(tileset, 1, screenBarText, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		new SnakeGame();
	}

}
