package view;
import java.awt.Graphics;

import util.Point;
import model.Tileset;

@SuppressWarnings("serial")
public class ScreenBar extends Screen {
	
	StringBuilder text;

	protected ScreenBar(Tileset tileset, Point p, StringBuilder text) {
		super(tileset, p);
		this.text = text;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.write(g, text.toString(), 0, 0, 7);
	}

}
