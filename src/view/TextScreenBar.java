package view;
import java.awt.Font;
import java.awt.Graphics;
import util.Point;
import util.Tileset;

@SuppressWarnings("serial")
public class TextScreenBar extends Screen {
	
	String[] text;
	int color;

	public TextScreenBar(Tileset tileset, Point dim, String[] text, int cl, int bg) {
		super(tileset, dim);
		this.text = text;
		setBackground(tileset.color(bg));
		color = cl;
	} 
	
	protected TextScreenBar(Tileset tileset, Point dim, String[] text, int bg, int cl, Font font) {
	super(tileset, dim);
	this.text = text;
	this.font = font;
} 
	
	public void paint(Graphics g){
		super.paint(g);
		for (int y=0; y<text.length; y++)
			write(g, text[y].toString(), 0, y, color);
	}

}
