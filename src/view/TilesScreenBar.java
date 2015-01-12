package view;

import java.awt.Graphics;

import util.Point;
import util.Tile;
import util.Tileset;

@SuppressWarnings("serial")
public class TilesScreenBar extends Screen {

	int[][] tiles;
	
	protected TilesScreenBar(Tileset tileset, Point dim, int[][] tiles) {
		super(tileset, dim);
		this.tiles = tiles;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for (int y=0; y<tiles.length; y++)
			for (int x=0; x<tiles[y].length; x++)
				draw(g, new Tile(tiles[y][x]), new Point(x, y));
	}

}
