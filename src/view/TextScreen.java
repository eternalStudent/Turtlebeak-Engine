package view;

import java.awt.Graphics;

import util.Point;
import util.Text;
import util.Tileset;

@SuppressWarnings("serial")
public class TextScreen extends Screen {
	
	private Text text;
	private int cl;

	protected TextScreen(Tileset tileset, Point dim, Text text, int cl, int bg) {
		super(tileset, dim, bg);
		this.text = text;
		this.cl = cl;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		for (int y=text.currentLine; y<dim.height+text.currentLine; y++)
			write(g, text.getLine(y), 0, y, cl);
	}

}
