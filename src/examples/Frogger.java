package examples;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Entity;
import model.EventHandler;
import model.MOB;
import model.World;
import util.AI;
import util.Lambda;
import util.Point;
import util.Tileset;
import view.Board;
import controller.Keyboard;

public class Frogger {
	
	private final Keyboard keyboard = new Keyboard();
	private final Board board = new Board(keyboard, "frogger");;
	private World world;
	private MOB player;
	private EventHandler game;
	private final List<MOB> mobs = new ArrayList<>();
	private final int[][] lifes = {{17, 17, 18}};
	private final StringBuilder[] text = new StringBuilder[1];
	private final List<MOB> frogs = new ArrayList<>();
	
	public Frogger(){
		initialize();
		int key = 0;
		while (key != KeyEvent.VK_N){
			lifes[0][0] = 17; lifes[0][1] = 17; lifes[0][2] = 18;
			player.loc.set(0, new Point(9, 12));
			player.XP = 0;
			int minY = 12;
			world.add(player);
			world.remove(player);
			for (MOB frog: frogs)
				world.remove(frog);
			frogs.clear();
			while (key != KeyEvent.VK_ESCAPE && !player.isDead()){
				
				//player reached the end
				if (player.loc.get(0).y==0){
					world.remove(player);
					if (frogs.size() == 4){
						for (MOB frog: frogs)
							world.remove(frog);
						frogs.clear();
					}
					else{
						MOB frog = new MOB("frog");
						frog.add(player.loc.get(0), 4);
						world.add(frog);
						frogs.add(frog);
					}
					player.XP+=5;
					player.loc.set(0, new Point(9, 12));
					minY = 12;
					world.add(player);
				}
				else{
					if (player.loc.get(0).y<minY){
						minY--;
						player.XP++;
					}
				}
			
				text[0].delete(0, text[0].length());
				text[0].append("score: "+Integer.toString(player.XP));
			
				//player plays
				if (keyboard.keypressed()){
					key = keyboard.get();
					if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN ){
						Point dir = Keyboard.ArrowToPoint(key);
						if (!game.move(player, dir))
							loseLife();
						for (Entity e: world.get(player.loc.get(0)))
							if (e!=player && e.getClass().getName().equals("model.MOB")){
								((MOB)e).ties.add(player);
							}	
					}
				}
			
				//move stuff
				for (MOB m: mobs){
					if (!m.act())
						loseLife();
					if (m.ties.size()>0 && m.ties.contains(player) && !m.loc.contains(player.loc.get(0))){
						m.ties.remove(player);
					}	
				}
			
				//paint and wait
				board.repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (player.isDead()){
				text[0].append(" Game Over!");
				board.repaint();
				do {
					key = keyboard.get();
				}while(key != KeyEvent.VK_ENTER);
			}	
			text[0].delete(0, text[0].length());
			text[0].append("Play Again? (Y/N)");
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
				return new Point(p.x*32, p.y*32);
			}
		};
		Tileset tileset = new Tileset("frogger.png", 32, 32, toScreen);
		tileset.setTransparentBackground(-16777216);
		Lambda<Point, Entity> defaultTerrain = new Lambda<Point, Entity>(){
			public Entity produce(Point p){
				Entity e = new Entity(null);
				if (p.y<6){
					if (p.x % 4 == 1 && p.y == 0){
						e.name = "lilypad";
						e.add(p, 2);
					}
					else{
						e.name = "water";
						e.add(p, 1);
						e.passable = false;
					}	
				}
				else if (p.y == 6 || p.y == 12){
					e.name = "pavement";
					e.add(p,  3);
				}
				else{
					e.name = "road";
					e.add(p,  0);
				}
				if (p.x<0 || p.x>19 || p.y<0 || p.y>12)
					e.passable = false;
				return e;
			}
		};
		world = new World(tileset, defaultTerrain);
		
		player = new MOB("player");
		player.add(new Point(9, 12), 4);
		player.zLevel = 3.0;
		player.HP = 5;
		world.add(player);
				
		try {
			Entity.fromResource(getClass().getResource("frogger.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((MOB) Entity.dict.get("left race car")).ai = newAI(-1, 15);
		((MOB) Entity.dict.get("bulldozer")).ai = newAI(1, 45);
		((MOB) Entity.dict.get("private car")).ai = newAI(-1, 25);
		((MOB) Entity.dict.get("right race car")).ai = newAI(1, 15);
		((MOB) Entity.dict.get("truck")).ai = newAI(-1, 45);
		((MOB) Entity.dict.get("left turtles")).ai = newAI(-1, 30);
		((MOB) Entity.dict.get("right turtles")).ai = newAI(-1, 30);
		((MOB) Entity.dict.get("log2")).ai = newAI(1, 40);
		((MOB) Entity.dict.get("log3")).ai = newAI(1, 40);
		((MOB) Entity.dict.get("log5")).ai = newAI(1, 30);

		place("left race car", 2, 10, 11);
		place("bulldozer", 4, 5, 10);
		place("private car", 3, 6, 9);
		place("right race car", 1, 10, 8);
		place("truck", 3, 6, 7);
		place("left turtles", 4, 5, 5);
		place("log2", 3, 6, 4);
		place("log5", 1, 10, 3);
		place("right turtles", 4, 5, 2);
		place("log3", 3, 5, 1);
		
		game = new EventHandler(world);
		board.setScreen(world, new Point(20, 13));
		board.addScreenBar(tileset, lifes, BorderLayout.SOUTH);
		text[0] = new StringBuilder();
		board.addScreenBar(tileset, text, BorderLayout.NORTH, 12, 0);
	}
	
	private AI newAI(int dir, int wait){
		int x1 = dir==1? 19:  0;
		int x2 = dir==1? -1: 20;
		return new AI(world){
			public int waitingPeriod;
			public boolean act(){
				if (waitingPeriod == 0){
					for (int i=0; i<mob.loc.size(); i++)
						if (mob.loc.get(i).equals(x1, mob.loc.get(i).y)){
							world.remove(mob);
							if (mob.loc.get(i).equals(player.loc.get(0))){
								mob.ties.remove(player);
								loseLife();
							}	
							mob.loc.set(i, new Point(x2, mob.loc.get(i).y));
							world.add(mob);
						}
					waitingPeriod = wait;
					return game.move(mob, new Point(dir, 0));
				}
				else
					waitingPeriod--;
				return true;
			}
		};
	}
	
	private void loseLife(){
		world.remove(player);
		player.loc.set(0,  new Point(9, 12));
		world.add(player);
		player.HP--;
		if (player.HP%2==0)
			lifes[0][player.HP/2] = 19;
		else
			lifes[0][player.HP/2] = 18;
	}
	
	private void place(String name, int amount, int space, int y){
		for (int i=0; i<amount; i++){
			Entity m = Entity.fromDict(name);
			for (int j=0; j<m.tiles.size(); j++)
				m.loc.add(new Point(i*space+j, y));
			world.add(m);
			mobs.add((MOB)m);
		}
	}

	public static void main(String[] args) {
		new Frogger();
	}

}