package view;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import model.Entity;
import model.World;
import util.Point;

@SuppressWarnings("serial")
public class GameScreen extends Screen {
	
	private World world;

	protected GameScreen(World world, Point p) {
		super(world.tileset, p);
		this.world = world;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for (int x=0; x<dim.width; x++)
			for (int y=0; y<dim.height; y++){ 
				Point p = new Point(x, y);
				world.get(p);
			}
		List<Entity> list = new ArrayList<>(world.entities);
		for (Entity e: list)
			for (Point p: e.loc){
				if (world.tileset.ASCII)
					draw(g, e.getAscii(p), p);
				else
					draw(g, e.getTile(p), p);
						
			}	
	}

}
