package view;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.Point;
import util.ASCII;
import util.Tile;
import util.Tileset;

@SuppressWarnings("serial")
public abstract class Screen extends JPanel{
	
	protected Tileset tileset;
	protected Dimension dim;
	protected Font font;
	protected int bg;
	
	protected Screen(Tileset tileset, Point dim, int bg){
		super();
		this.tileset = tileset;
		font = new Font(Font.SANS_SERIF, Font.PLAIN, tileset.th);
		setBackground(tileset.color(bg));
		this.dim = new Dimension(dim.x, dim.y);
		dim = tileset.toScreen.produce(dim);
		setPreferredSize(new Dimension(dim.x, dim.y));
	}
	
	protected Screen(Tileset tileset, Point dim){
		this(tileset, dim, 0);
	}
	
	protected void writeTF(Graphics g, String st, int x, int y, int color){
		writeTF(g, st, new Point(x, y), color);
	}
	
	protected void writeTF(Graphics g, String st, Point p, int color){
		p = tileset.toScreen.produce(p.add(new Point(0, 1)));
		g.setColor(tileset.color(color));
		g.drawString(st, p.x, p.y-g.getFontMetrics().getMaxDescent());
	}
	
	protected void draw(Graphics g, ASCII tile, int x, int y){
		draw(g, tile, new Point(x, y));
	}
	
	protected void draw(Graphics g, ASCII tile, Point p){
		p = tileset.toScreen.produce(p);
		g.drawImage(tileset.getTileImage(tile), p.x, p.y, null);
	}
	
	protected void draw(Graphics g, Tile tile, Point p){
		p = tileset.toScreen.produce(p);
		g.drawImage(tileset.getTileImage(tile), p.x, p.y, null);
	}
	
	protected void writeChar(Graphics g, char ch, int x, int y, int cl){
		draw(g, new ASCII((int)ch, cl, bg), x, y);
	}
	
	protected void write(Graphics g, String st, int x0, int y0, int cl){
		if (!tileset.ASCII)
			writeTF(g, st, x0, y0, cl);
		else{
			char[] charrArr = st.toCharArray();
			for (int x=0; x<charrArr.length; x++)
				writeChar(g, charrArr[x], x0+x, y0, cl);
		}	
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setFont(font);
	}

}
