package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import controller.Keyboard;
import util.Point;
import model.Tileset;
import model.World;

@SuppressWarnings("serial")
public class Board extends JFrame {
	
	//private BorderLayout layout = new BorderLayout();
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
	
	public void addScreenBar(Tileset tileset, int size, StringBuilder text, String layout){
		Point p;
		if (layout.equals(BorderLayout.NORTH) || (layout.equals(BorderLayout.SOUTH))){
			p = new Point(width, size);
			height +=size;
		}	
		else{
			p = new Point(size, height);
			width += size;
		}
		getContentPane().add(new ScreenBar(tileset, p, text), layout);
		pack();
	}

}
