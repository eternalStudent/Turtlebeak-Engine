package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import controller.Keyboard;
import util.Point;
import util.Tileset;
import model.World;

@SuppressWarnings("serial")
public class Board extends JFrame {
	
	public int width, height;
	
	public Board(Keyboard keyboard, String title){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setVisible(true);
		setResizable(false);		
		addKeyListener(keyboard);
	}
	
	public void setScreen(World world, Point p){
		width = p.x;
		height = p.y;
		getContentPane().add(new GameScreen(world, p), BorderLayout.CENTER);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(new java.awt.Point((screenSize.width-this.getWidth())/3, (screenSize.height-this.getHeight())/3));
	}
	
	private Point calculateScreenBarDim(int size, String layout){
		Point p;
		if (layout.equals(BorderLayout.NORTH) || (layout.equals(BorderLayout.SOUTH))){
			p = new Point(width, size);
			height +=size;
		}	
		else{
			p = new Point(size, height);
			width += size;
		}
		return p;
	}
	
	public void addScreenBar(Tileset tileset, StringBuilder[] text, String layout, int cl, int bg){
		Point dim = calculateScreenBarDim(text.length, layout);
		getContentPane().add(new TextScreenBar(tileset, dim, text, cl, bg), layout);
		pack();
	}
	
	public void addScreenBar(Tileset tileset, int[][] tiles, String layout){
		Point p = calculateScreenBarDim(tiles.length, layout);
		getContentPane().add(new TilesScreenBar(tileset, p, tiles), layout);
		pack();
	}

}