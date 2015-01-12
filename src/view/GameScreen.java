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
	private boolean showAll;

	protected GameScreen(World world, Point dim, boolean showAll) {
		super(world.tileset, dim);
		this.world = world;
		this.showAll = showAll;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for (int x=world.loc.x; x<world.loc.x+dim.width; x++)
			for (int y=world.loc.y; y<world.loc.y+dim.height; y++){ 
				Point p = new Point(x, y);
				world.get(p);
			}
		List<Entity> list = new ArrayList<>(world.entities);
		for (Entity e: list)
			for (Point p: e.loc){
				if (world.isVisible(p) || showAll){
					if (world.tileset.ASCII)
						draw(g, e.getAscii(p), p.subtract(world.loc));
					else
						draw(g, e.getTile(p).filter(world.getFilters(p)), p.subtract(world.loc));
				}			
			}	
	}

}
