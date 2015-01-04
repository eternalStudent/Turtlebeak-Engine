package view;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

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
				SortedSet<Entity> es = world.get(p);
				List<Entity> list = new ArrayList<>(es);
				if (world.tileset.ASCII)
					draw(g, es.last().getAscii(p), p);
				else
					for (int i=0; i<es.size(); i++){
						draw(g, list.get(i).getTile(p), p);
					}	
			}	
	}

}
